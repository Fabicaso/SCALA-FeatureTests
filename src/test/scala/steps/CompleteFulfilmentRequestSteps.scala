package steps

import itv.fulfilmentplanning.AssetRequested
import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._

class CompleteFulfilmentRequestSteps
    extends BaseSteps
    with CurrentRequestsPageObject
    with MenuPageObject
    with OverviewPageObject
    with NewRequestPageObject
    with ConfirmRequestPageObject {

  override implicit val patienceConfig = PatienceConfig(10.seconds, 200.milliseconds)

  When("""^I complete the fulfilment request for '(.*)' with '(.*)' selecting '(.*)'$""") {
    (productionId: String, requiredDate: String, expectedAssetsToSelect: String) =>
      logger.info(scenarioMarker, s"Completing the fulfilment request")
      PageLoadedRequest.whenIsEnabled

      selectAssets(productionId, expectedAssetsToSelect)

      RequestConfirmLoaded.whenIsEnabled

      sendRequest(AssetRequested.requestedAssets(productionId), RequiredByDateQuery(requiredDate))

      SentRequestConfirmation.whenIsDisplayed(PatienceConfig(5.seconds, 100.milliseconds), scenarioMarker)
      logger.info(scenarioMarker, s"Request has been sent!")

  }

  private def sendRequest(expectedAsset: AssetRequested, date: Option[Query]) = {
    expectedAsset.assetJobType match {
      case "Transcode"      => fillTrancodeRequest(date, expectedAsset.client)
      case "PullAndDeliver" => fillPullAndDeliverRequest(date, expectedAsset.client)
      case _                => fail(s"Unsupported job type in $expectedAsset")
    }

    SendRequestButton.clickWhenIsEnabled
  }

  private def selectAssets(productionId: String, expectedAssetsToSelect: String) = {
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
    RequestNextButton.clickWhenIsDisplayed
  }

  def fillPullAndDeliverRequest(date: Option[Query], client: String) =
    fillCommonRequestFor(PullAndDeliverJob, date, client)

  def fillTrancodeRequest(date: Option[Query], client: String) =
    fillCommonRequestFor(TranscodeJob, date, client)

  private def fillCommonRequestFor(job: Query, date: Option[Query], client: String) = {
    textField(ClientField).value = client
    DeliveryMediumField.clickWhenIsDisplayed
    OnlineDeliveryMedium.clickWhenIsDisplayed
    JobField.clickWhenIsDisplayed
    job.clickWhenIsDisplayed
    textArea(DeliveryMethod).value = "Delivery Method"
    date.foreach { dateQuery =>
      logger.debug(scenarioMarker, s"Setting date $dateQuery for $job")
      eventually(click on RequiredByDateField.whenIsDisplayed)
      eventually(click on dateQuery.whenIsDisplayed)
    }
  }
}
