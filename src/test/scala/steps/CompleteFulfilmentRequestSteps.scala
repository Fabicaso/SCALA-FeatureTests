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

  override implicit val patienceConfig = PatienceConfig(4.seconds, 100.milliseconds)

  When("""^I complete the fulfilment request for '(.*)' with '(.*)' selecting '(.*)'$""") {
    (productionId: String, requiredDate: String, expectedAssetsToSelect: String) =>
      logger.info(scenarioMarker, s"Complete the fulfilment request")
      PageLoadedOverview.whenIsEnabled
      eventually(click on CreateNewRequestButton.whenIsDisplayed)

      PageLoadedRequest.whenIsEnabled
      click on ProductionIdButton(productionId).whenIsEnabled

      val assetsToSelect = AssetsToSelect(expectedAssetsToSelect, productionId)
      if (assetsToSelect.isEmpty)
        fail(s"Unsupported assets to select type: $expectedAssetsToSelect")
      else {
        eventually {
          if (assetsToSelect.size > 1)
            click on SelectMultipleAssets.whenIsDisplayed
          assetsToSelect.foreach { nextAssetToSelect =>
            click on nextAssetToSelect.whenIsDisplayed
          }
        }
      }
      click on RequestNextButton.whenIsDisplayed

      RequestConfirmLoaded.whenIsEnabled

      val expectedAsset = AssetRequested.requestedAssets(productionId)

      val date: Option[Query] = RequiredByDateQuery(requiredDate)

      expectedAsset.assetJobType match {
        case "Transcode"      => fillTrancodeRequest(date)
        case "PullAndDeliver" => fillPullAndDeliverRequest(date)
        case _                => fail(s"Unsupported job type in $expectedAsset")
      }

      eventually(click on SendRequestButton.whenIsEnabled)

      SentRequestConfirmation.whenIsDisplayed(PatienceConfig(5.seconds, 100.milliseconds), scenarioMarker)

  }

  def fillPullAndDeliverRequest(date: Option[Query]) =
    fillCommonRequestFor(PullAndDeliverJob, date)

  def fillTrancodeRequest(date: Option[Query]) =
    fillCommonRequestFor(TranscodeJob, date)

  private def fillCommonRequestFor(job: Query, date: Option[Query]) = {
    textField(ClientField).value = "Client"
    click on DeliveryMediumField.whenIsDisplayed
    click on OnlineDeliveryMedium.whenIsDisplayed
    eventually(click on JobField.whenIsDisplayed)
    eventually(click on job.whenIsDisplayed)
    textArea(DeliveryMethod).value = "Delivery Method"
    date.foreach { dateQuery =>
      logger.debug(scenarioMarker, s"Setting date $dateQuery for $job")
      eventually(click on RequiredByDateField.whenIsDisplayed)
      eventually(click on dateQuery.whenIsDisplayed)
    }
  }
}
