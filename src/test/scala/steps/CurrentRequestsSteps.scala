package steps

import itv.fulfilmentplanning.{AssetRequested, TestData}
import itv.fulfilmentplanning.pageobjects.{
  CurrentRequestsPageObject,
  MenuPageObject,
  NewRequestPageObject,
  OverviewPageObject
}

import scala.concurrent.duration._
import org.scalatest.Matchers._

class CurrentRequestsSteps
    extends BaseSteps
    with CurrentRequestsPageObject
    with MenuPageObject
    with OverviewPageObject
    with NewRequestPageObject {

  override implicit val patienceConfig = PatienceConfig(4.seconds, 200.milliseconds)

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
      val expectedDate = TestData.dateFrom(requiredBy)
      CurrentRequestSection.clickWhenIsDisplayed
      waitUntilPageIsLoaded()

      RequestedAssetsForDate(expectedDate).clickWhenIsDisplayed

      val result = RequestedAssetRowBy(productionId).whenIsDisplayed

      val domIdForProduction = result.attribute("id").getOrElse(fail)

      val list            = domIdForProduction.split("-").init
      val assetId         = list.last
      val actualLicenceId = list.init.last

      licenceId should ===(actualLicenceId.toInt)
      val expectedAsset = AssetRequested.requestedAssets(productionId)

      ExactText(expectedAsset.client).whenIsDisplayed
      JobTypeAssetRow(productionId,
                      actualLicenceId,
                      assetId,
                      expectedAsset.client,
                      expectedDate,
                      expectedAsset.assetJobType).whenIsDisplayed

      expectedAsset should ===(
        AssetRequested(
          productionId = productionId,
          programmeTitle = ProgrammeTitleAssetRow(productionId,
                                                  actualLicenceId,
                                                  assetId,
                                                  expectedAsset.client,
                                                  expectedDate).whenIsDisplayed.text,
          duration =
            DurationAssetRow(productionId, actualLicenceId, assetId, expectedAsset.client, expectedDate).whenIsDisplayed.text,
          source =
            SourceAssetRow(productionId, actualLicenceId, assetId, expectedAsset.client, expectedDate).whenIsDisplayed.text,
          assetJobType = expectedAsset.assetJobType,
          client = expectedAsset.client
        ))

      if (assetsToSelect == "multiple asssets") {
        RequestedAssetRowBy(productionId).elements shouldBe >(1)
      }

      logger.info(scenarioMarker, "Success!")
  }

  private def waitUntilPageIsLoaded() = {
    CurrentRequestsPageLoaded
      .whenIsEnabled(patienceConfig = PatienceConfig(20.seconds, 1.second), scenarioMarker)
      .isEnabled shouldBe true
  }

}