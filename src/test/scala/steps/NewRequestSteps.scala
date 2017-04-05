package steps

import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._

class NewRequestSteps
    extends BaseSteps
    with CurrentRequestsPageObject
    with MenuPageObject
    with OverviewPageObject
    with NewRequestPageObject {

  override implicit val patienceConfig = PatienceConfig(4.seconds, 100.milliseconds)

  Then("""^No assets found msg is displayed for the production id - '(.*)'$""") { (productionId: String) =>
    logger.info(scenarioMarker, s"Warning message to be displayed is: No assets found for $productionId")
    NoAssetsFound(productionId).whenIsDisplayed.text should ===("No assets found")
    logger.info(scenarioMarker, "Success!")
  }

  And("""^I am on the 'New Request' page using the following licence number '(.*)'$""") { (licenceId: String) =>
    logger.info(scenarioMarker, s"Go to licence number: $licenceId")
    eventually(click on EnterLicenceSection.whenIsDisplayed)
    eventually(numberField(LicenceInput)).value = licenceId.toString
    submit()
    logger.info(scenarioMarker, s"Overview Page loaded")
    eventually(click on CreateNewRequestButton.whenIsDisplayed)
    PageLoadedRequest.whenIsEnabled
    logger.info(scenarioMarker, s"New Request Page loaded")
  }

}
