package steps

import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._

class NewRequestSteps extends BaseSteps with MenuPageObject with OverviewPageObject with NewRequestPageObject {

  override implicit val patienceConfig = PatienceConfig(4.seconds, 200.milliseconds)

  Then("""^No assets found msg is displayed for '(.*)' and production id - '(.*)'$""") { (series: String, productionId: String) =>
    logger.info(scenarioMarker, s"Warning message to be displayed is: No assets found for $productionId")
    newRequestSeriesRow(series).clickWhenIsDisplayed
    NoAssetsFound(productionId).whenIsDisplayed.text should ===("No assets found")
  }

  And("""^I am on the 'New Request' page using the following licence number '(.*)'$""") { (licenceId: String) =>
    logger.info(scenarioMarker, s"Go to licence number: $licenceId")
    EnterLicenceSection.clickWhenIsDisplayed
    eventually(numberField(LicenceInput)).value = licenceId.toString
    submit()
    logger.info(scenarioMarker, s"Overview Page loaded")
    CreateNewRequestButton.clickWhenIsDisplayed
    PageLoadedRequest.whenIsEnabled
    logger.info(scenarioMarker, s"New Request Page loaded")
  }

  And("""^Production ID '(.*)' is checked as previously Requested for '(.*)' on the New request page$""") {
    (productionId: String, series: String) =>
      logger.info(scenarioMarker, s"isRequestedOrFulfilledCheck displayed")
      newRequestSeriesRow(series).clickWhenIsDisplayed
      isRequestedOrFulfilledCheck(productionId).whenIsDisplayed
  }

}
