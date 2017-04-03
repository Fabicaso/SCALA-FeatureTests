package steps

import itv.fulfilmentplanning.{AssetRequested, TestData}
import itv.fulfilmentplanning.pageobjects.{CurrentRequestsPageObject, MenuPageObject}

import scala.concurrent.duration._
import org.scalatest.Matchers._

class CurrentRequestsSteps extends BaseSteps with CurrentRequestsPageObject with MenuPageObject {

  override implicit val patienceConfig = PatienceConfig(4.seconds, 100.milliseconds)

  And("""^I am on the 'New Request' page using the following licence number (\d+)$""") { (licenceId: Int) =>
    logger.info(scenarioMarker, s"Go to licence number: $licenceId")
    click on EnterLicenceSection.whenIsDisplayed
    eventually(numberField(LicenceInput)).value = licenceId.toString
    submit()
  }

  Then("""^the 'Current Requests' page is displayed$""") { () =>
    logger.info(scenarioMarker, "Go to current request")
    waitUntilPageIsLoaded()
    logger.info(scenarioMarker, "Success!")
  }

  Then(
    """^'(.*)' with production id '(.*)' and licence number (\d+) (?:is|are) displayed on the 'Current Requests' page under '(.*)' section$""") {
    (assetsToSelect: String, productionId: String, licenceId: Int, requiredBy: String) =>
      logger.info(scenarioMarker,
                  s"Verify asset with production id $productionId has been requested for licence number: $licenceId")
      click on CurrentRequestSection.whenIsDisplayed
      waitUntilPageIsLoaded()

      val expectedDate: Option[String] = TestData.dateFrom(requiredBy)

      click on RequestedAssetsForDate(expectedDate)

      val result = RequestedAssetRowBy(productionId).whenIsDisplayed

      val domIdForProduction = result.attribute("id").getOrElse(fail)

      val list            = domIdForProduction.split("-").init
      val assetId         = list.last
      val actualLicenceId = list.init.last

      licenceId should ===(actualLicenceId.toInt)
      val expectedAsset = AssetRequested.requestedAssets(productionId)

      JobTypeAssetRow(productionId, actualLicenceId, assetId, expectedDate, expectedAsset.assetJobType).whenIsDisplayed

      expectedAsset should ===(
        AssetRequested(
          productionId = productionId,
          programmeTitle =
            ProgrammeTitleAssetRow(productionId, actualLicenceId, assetId, expectedDate).whenIsDisplayed.text,
          duration = DurationAssetRow(productionId, actualLicenceId, assetId, expectedDate).whenIsDisplayed.text,
          source = SourceAssetRow(productionId, actualLicenceId, assetId, expectedDate).whenIsDisplayed.text,
          assetJobType = expectedAsset.assetJobType
        ))

      if (assetsToSelect == "multiple asssets") {
        RequestedAssetRowBy(productionId).elements shouldBe >(1)
      }

      logger.info(scenarioMarker, "Success!")
  }

  private def waitUntilPageIsLoaded() = {
    CurrentRequestsPageLoaded
      .whenIsEnabled(patienceConfig = PatienceConfig(10.seconds, 1.second), scenarioMarker)
      .isEnabled shouldBe true
  }

}
