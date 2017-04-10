package steps

import itv.fulfilmentplanning.{AssetRequested, TestData}
import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._

class CurrentRequestsSteps extends BaseSteps with CurrentRequestsPageObject with MenuPageObject {

  override implicit val patienceConfig = PatienceConfig(4.seconds, 200.milliseconds)

  Then("""^the 'Current Requests' page is displayed$""") { () =>
    logger.info(scenarioMarker, "Go to current request")
    waitUntilPageIsLoaded()
  }

  Then(
    """^'(.*)' with production id '(.*)' and licence number (\d+) (?:is|are) displayed on the 'Current Requests' page under '(.*)' section$""") {
    (assetsToSelect: String, productionIds: String, licenceId: Int, requiredBy: String) =>
      logger.info(scenarioMarker,
                  s"Verify asset with production id $productionIds has been requested for licence number: $licenceId")
      val expectedDate = TestData.dateFrom(requiredBy)
      CurrentRequestSection.clickWhenIsDisplayed
      waitUntilPageIsLoaded()

      RequestedAssetsForDate(expectedDate).clickWhenIsDisplayed

      val productionIdsToSelect = productionIds.split(",")
      productionIdsToSelect.foreach { productionId =>
        val result = RequestedAssetRowBy(productionId).whenIsDisplayed

        val domIdForProduction = result.attribute("id").getOrElse(fail)

        val list            = domIdForProduction.split("-").init
        val assetId         = list.last
        val actualLicenceId = list.init.last

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
            licenceId = actualLicenceId,
            productionId = productionId,
            deliveryMedium = expectedAsset.deliveryMedium,
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

        if (assetsToSelect == "multiple assets") {
          RequestedAssetRowBy(productionId).elements shouldBe >(1)
        }
      }

  }

  private def waitUntilPageIsLoaded() = {
    CurrentRequestsPageLoaded
      .whenIsEnabled(patienceConfig = PatienceConfig(20.seconds, 1.second), scenarioMarker)
      .isEnabled shouldBe true
  }

}
