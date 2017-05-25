package steps

import itv.fulfilmentplanning.{Credentials, ExpectedAsset, ExpectedData, TestData}
import itv.fulfilmentplanning.pageobjects._
import org.scalactic.StringNormalizations._

import scala.concurrent.duration._

class CurrentRequestsSteps
    extends BaseSteps
    with CurrentRequestsPageObject
    with CurrentRequestSideBar
    with MenuPageObject {

  override implicit val patienceConfig = PatienceConfig(10.seconds, 200.milliseconds)

  Then(
    """^asset details with production id '(.*)' and licence number '(\d+)' (?:is|are) displayed on the 'Current Requests' page under '(.*)' section$""") {
    (productionId: String, licenceId: Int, requiredBy: String) =>
      logger.info(scenarioMarker,
                  s"Verify asset with production id $productionId has been requested for licence number: $licenceId")

      CurrentRequestSection.clickWhenIsDisplayed
      waitUntilPageIsLoaded()

      RequestedAssetsForDate(TestData.dateFrom(requiredBy)).clickWhenIsDisplayed

      RequestedAssetRowBy(productionId).clickWhenIsDisplayed

      val expectedAsset = ExpectedData.assetFor(productionId)

      SideBarAssetId.whenIsDisplayed.text should not be 'empty

      SideBarTitle.whenIsDisplayed.text should not be 'empty

      (SideBarRequestedBy.whenIsDisplayed.text should ===(Credentials.testCredentials.email.split("@").head))(
        after being lowerCased)
      SideBarRequestId.whenIsDisplayed.text should include(licenceId.toString)
      SideBarFormat.whenIsDisplayed.text should ===(expectedAsset.format)

      SideBarOrderId.whenIsDisplayed.text should ===(expectedAsset.licenceId)

      (SideBarJob.whenIsDisplayed.text should ===(expectedAsset.job.jobType))(after being lowerCased)
      SideBarClient.whenIsDisplayed.text should ===(expectedAsset.job.client)
      SideBarDeliveryMethod.whenIsDisplayed.text should ===(expectedAsset.job.deliveryMethod)
      (SideBarDeliveryMedium.whenIsDisplayed.text.replaceAll(" ", "") should ===(expectedAsset.job.deliveryMedium))(
        after being lowerCased)
      (SideBarPreferredOutputFrameRate.whenIsDisplayed.text should ===(
        expectedAsset.job.preferredOutputFrameRate.getOrElse("")))(after being lowerCased)
      (SideBarResolutionOutput.whenIsDisplayed.text should ===(expectedAsset.job.resolutionOutput.getOrElse("")))(
        after being lowerCased)
      SideBarSpec.whenIsDisplayed
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

        val expectedAsset = ExpectedData.assetFor(productionId)

        ExactText(expectedAsset.job.client).whenIsDisplayed
        JobTypeAssetRow(productionId,
                        actualLicenceId,
                        assetId,
                        expectedAsset.job.client,
                        expectedDate,
                        expectedAsset.job.jobType).whenIsDisplayed

        expectedAsset should ===(
          ExpectedAsset(
            productionId = productionId,
            licenceId = actualLicenceId,
            programmeTitle = ProgrammeTitleAssetRow(productionId,
                                                    actualLicenceId,
                                                    assetId,
                                                    expectedAsset.job.client,
                                                    expectedDate).whenIsDisplayed.text,
            duration =
              DurationAssetRow(productionId, actualLicenceId, assetId, expectedAsset.job.client, expectedDate).whenIsDisplayed.text,
            format =
              FormatAssetRow(productionId, actualLicenceId, assetId, expectedAsset.job.client, expectedDate).whenIsDisplayed.text,
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

  Then("""^I can change the status to '(.*)' on the Current Request page$"""){
    (toAssetStatus: String) =>
      logger.info(scenarioMarker, s"Change the Asset Status to Cancelled from the Current Request page")
      CurrentRequestNavBarMenu.clickWhenIsDisplayed
      CurrentRequestEditStatus.clickWhenIsDisplayed
      CurrentRequestAssetStatus(toAssetStatus.toLowerCase()).clickWhenIsDisplayed
  }

  private def waitUntilPageIsLoaded() = {
    CurrentRequestsPageLoaded
      .whenIsEnabled(patienceConfig = PatienceConfig(20.seconds, 1.second), scenarioMarker)
      .isEnabled shouldBe true
  }

}
