package steps

import itv.fulfilmentplanning.{Credentials, ExpectedAsset, ExpectedAsset$, TestData}
import itv.fulfilmentplanning.pageobjects._
import org.scalactic.StringNormalizations._

import scala.concurrent.duration._

class CurrentRequestsSteps
    extends BaseSteps
    with CurrentRequestsPageObject
    with CurrentRequestSideBar
    with MenuPageObject {

  override implicit val patienceConfig = PatienceConfig(4.seconds, 200.milliseconds)

  Then("""^the 'Current Requests' page is displayed$""") { () =>
    logger.info(scenarioMarker, "Go to current request")
    waitUntilPageIsLoaded()
  }

  Then(
    """^asset details with production id '(.*)' and licence number '(\d+)' (?:is|are) displayed on the 'Current Requests' page under '(.*)' section$""") {
    (productionId: String, licenceId: Int, requiredBy: String) =>
      logger.info(scenarioMarker,
                  s"Verify asset with production id $productionId has been requested for licence number: $licenceId")

      CurrentRequestSection.clickWhenIsDisplayed
      waitUntilPageIsLoaded()

      RequestedAssetsForDate(TestData.dateFrom(requiredBy)).clickWhenIsDisplayed

      RequestedAssetRowBy(productionId).clickWhenIsDisplayed
      val expectedAsset = ExpectedAsset(productionId)

      (SideBarRequestedBy.whenIsDisplayed.text should ===(Credentials.testCredentials.email.split("@").head))(
        after being lowerCased)
      SideBarRequestId.whenIsDisplayed.text should include(licenceId.toString)
      (SideBarJob.whenIsDisplayed.text.replaceAll(" ", "") should ===(expectedAsset.assetJobType))(
        after being lowerCased)
      SideBarOrderId.whenIsDisplayed.text should ===(expectedAsset.licenceId)

      (SideBarDeliveryMedium.whenIsDisplayed.text.replaceAll(" ", "") should ===(expectedAsset.job.deliveryMedium))(
        after being lowerCased)
//      expectedAsset.programmeTitle should ===(SideBarTitle.whenIsDisplayed.text)
  }

  Then(
    """^'(.*)' with production id '(.*)' and licence number (\d+) (?:is|are) displayed on the 'Current Requests' page under '(.*)' section$""") {
    (assetsToSelect: String, productionIds: String, licenceId: Int, requiredBy: String) =>
      logger.info(scenarioMarker,
                  s"Verify asset with production id $productionIds has been requested for licence number: $licenceId")

      CurrentRequestSection.clickWhenIsDisplayed
      waitUntilPageIsLoaded()
      val expectedDate = TestData.dateFrom(requiredBy)

      RequestedAssetsForDate(expectedDate).clickWhenIsDisplayed

      val productionIdsToSelect = productionIds.split(",")
      productionIdsToSelect.foreach { productionId =>
        val result             = RequestedAssetRowBy(productionId).whenIsDisplayed
        val domIdForProduction = result.attribute("id").getOrElse(fail)
        val list               = domIdForProduction.split("-").init
        val assetId            = list.last
        val actualLicenceId    = list.init.last

        val expectedAsset = ExpectedAsset(productionId)

        ExactText(expectedAsset.job.client).whenIsDisplayed
        JobTypeAssetRow(productionId,
                        actualLicenceId,
                        assetId,
                        expectedAsset.job.client,
                        expectedDate,
                        expectedAsset.assetJobType).whenIsDisplayed

        expectedAsset should ===(
          ExpectedAsset(
            licenceId = actualLicenceId,
            productionId = productionId,
            programmeTitle = ProgrammeTitleAssetRow(productionId,
                                                    actualLicenceId,
                                                    assetId,
                                                    expectedAsset.job.client,
                                                    expectedDate).whenIsDisplayed.text,
            duration =
              DurationAssetRow(productionId, actualLicenceId, assetId, expectedAsset.job.client, expectedDate).whenIsDisplayed.text,
            source =
              SourceAssetRow(productionId, actualLicenceId, assetId, expectedAsset.job.client, expectedDate).whenIsDisplayed.text,
            assetJobType = expectedAsset.assetJobType,
            job = expectedAsset.job
          ))

        if (assetsToSelect == "multiple assets") {
          val ProdNumberOnCurrentRequest = RequestedAssetRowBy(productionId).findAllElements.size
          ProdNumberOnCurrentRequest shouldBe >(1)
          logger.info(
            scenarioMarker,
            s"Current Request Page is showing all the: $ProdNumberOnCurrentRequest Assets selected for the required Productions ")
        }
      }

  }

  private def waitUntilPageIsLoaded() = {
    CurrentRequestsPageLoaded
      .whenIsEnabled(patienceConfig = PatienceConfig(20.seconds, 1.second), scenarioMarker)
      .isEnabled shouldBe true
  }

}
