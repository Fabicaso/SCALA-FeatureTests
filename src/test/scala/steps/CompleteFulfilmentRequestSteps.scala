package steps

import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._

class CompleteFulfilmentRequestSteps
    extends BaseSteps
    with CurrentRequestsPageObject
    with MenuPageObject
    with OverviewPageObject
    with NewRequestPageObject
    with ConfirmRequestPageObject {

  override implicit val patienceConfig = PatienceConfig(2.seconds, 100.milliseconds)

  When("""^I complete the fulfilment request for '(.*)' of '(.*)' with '(.*)'$""") {
    (_: String, _: String, productionId: String) =>
      logger.info(scenarioMarker, s"Complete the fulfilment request")
      PageLoadedOverview.whenIsEnabled
      eventually(click on CreateNewRequestButton.whenIsDisplayed)

      PageLoadedRequest.whenIsEnabled
      click on ProductionIdButton(productionId).whenIsEnabled

      eventually {
        click on PickFirstAsset(productionId).whenIsDisplayed
        VerifyAssetHasBeenSelected(productionId).whenIsDisplayed
      }
      click on RequestConfirmButton.whenIsDisplayed

      RequestConfirmLoaded.whenIsEnabled
      fillPullAndDeliverRequest()
      eventually(click on SendRequestButton.whenIsEnabled)

      SentRequestConfirmation.whenIsDisplayed(PatienceConfig(5.seconds, 100.milliseconds), scenarioMarker)
  }

  def fillPullAndDeliverRequest() = {
    textField(ClientField).value = "Client"
    click on DeliveryMediumField.whenIsDisplayed
    click on OnlineDeliveryMedium.whenIsDisplayed
    eventually(click on JobField.whenIsDisplayed)
    eventually(click on PullAndDeliverJob.whenIsDisplayed)
    textArea(DeliveryMethod).value = "Delivery Method"
  }
}
