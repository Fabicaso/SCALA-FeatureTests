package steps

import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser
import itv.fulfilmentplanning.AssetRequested
import itv.fulfilmentplanning.pageobjects._
import scala.concurrent.duration._

class CompleteFulfilmentRequestSteps extends BaseSteps with NewRequestPageObject with ConfirmRequestPageObject {

  override implicit val patienceConfig = PatienceConfig(10.seconds, 200.milliseconds)

  When("""^I complete the fulfilment request for '(.*)' and ProdID '(.*)' with '(.*)' selecting '(.*)'$""") {
    (series: String, productionIds: String, requiredDate: String, expectedAssetsToSelect: String) =>
      logger.info(scenarioMarker, s"Completing the fulfilment request")
      reloadPage()
      waitUntilPageIsLoaded()

      val productionIdsToSelect = productionIds.split(",")
      productionIdsToSelect.foreach { productionId =>
        selectAssets(series, productionId, expectedAssetsToSelect)
      }
      RequestNextButton.clickWhenIsDisplayed
      RequestConfirmLoaded.whenIsEnabled
      fillRequestDetails(AssetRequested.requestedAssets(productionIdsToSelect.last))
      setRequiredByToAsset(AssetRequested.requestedAssets(productionIdsToSelect.last).licenceId,
                           productionIds,
                           RequiredByDateQuery(requiredDate))

      SendRequestButton.clickWhenIsDisplayed
      SentRequestConfirmation.whenIsDisplayed(PatienceConfig(5.seconds, 100.milliseconds), scenarioMarker)
      logger.info(scenarioMarker, s"Request has been sent!")
  }

  private def fillRequestDetails(expectedAsset: AssetRequested) = {
    expectedAsset.assetJobType match {
      case "Transcode"      => fillCommonRequestFor(OnlineDeliveryMedium, TranscodeJob, expectedAsset.client)
      case "PullAndDeliver" => fillCommonRequestFor(HardDriveDeliveryMedium, PullAndDeliverJob, expectedAsset.client)
      case "TapeAsSource"   => fillCommonRequestFor(TapeDeliveryMedium, TapeAsSourceJob, expectedAsset.client)
      case _                => fail(s"Unsupported job type in $expectedAsset")
    }
    NextButtonOnSendRequestPage.clickWhenIsDisplayed
  }

  private def waitUntilPageIsLoaded() = {
    PageLoadedRequest
      .whenIsEnabled(patienceConfig = PatienceConfig(20.seconds, 1.second), scenarioMarker)
      .isEnabled shouldBe true
  }

  private def selectAssets(series: String, productionId: String, expectedAssetsToSelect: String) = {
    eventually {
      click on newRequestSeriesRow(series).whenIsDisplayed
      ProductionIdButton(productionId).whenIsDisplayed.isDisplayed should ===(true)
    }
    ProductionIdButton(productionId).clickWhenIsEnabled
    val assetsToSelect = AssetsToSelect(expectedAssetsToSelect, productionId)
    if (assetsToSelect.isEmpty)
      fail(s"Unsupported assets to select type: $expectedAssetsToSelect")
    else {
      if (assetsToSelect.size > 1)
        SelectMultipleAssets.clickWhenIsDisplayed
      eventually {
        assetsToSelect.foreach { nextAssetToSelect =>
          nextAssetToSelect.clickWhenIsDisplayed
        }
      }
      if (assetsToSelect.size > 1) {
        CloseAssetBoxButton(productionId).clickWhenIsDisplayed
        ProductionIdMultiple(productionId).whenIsDisplayed
      } else
        ProductionIdSelected(productionId).whenIsDisplayed
    }
  }

  private def fillCommonRequestFor(deliveryMedium: Query, job: Query, client: String) = {
    textField(ClientField).value = client
    DeliveryMediumField.clickWhenIsDisplayed
    deliveryMedium.clickWhenIsDisplayed
    JobField.clickWhenIsDisplayed
    job.clickWhenIsDisplayed
    textArea(DeliveryMethod).value = "Delivery Method"
  }

  private def setRequiredByToAsset(licenceId: String, productionIds: String, date: Option[Query]) = {
    logger.debug(scenarioMarker, s"Selecting the assets in order to add the Required By date")
    PageLoadedAssetRequestDates.whenIsEnabled

    val productionIdsToSelect = productionIds.split(",")
    productionIdsToSelect.foreach { productionId =>
      selectAssetsOnRequiredByPage(licenceId, productionId).clickWhenIsDisplayed
      date.foreach { dateQuery =>
        logger.debug(scenarioMarker, s"Setting date $dateQuery")
        RequiredByDateField.clickWhenIsDisplayed
        EditRequiredByDateDropdown.clickWhenIsDisplayed
        eventually(click on dateQuery.whenIsDisplayed)
      }
    }
  }

}
