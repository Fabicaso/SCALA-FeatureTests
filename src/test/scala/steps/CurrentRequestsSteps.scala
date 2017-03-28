package steps

import itv.fulfilmentplanning.pageobjects.MenuPageObject
import scala.concurrent.duration._

class CurrentRequestsSteps extends BaseSteps with MenuPageObject {

  override implicit val patienceConfig = PatienceConfig(2.seconds, 100.milliseconds)

  Given("""^I am on the 'New Request' page using the following licence number (\d+)$""") { (licenceId: Int) =>
    logger.info(scenarioMarker, s"Go to licence number: $licenceId")
    click on EnterLicenceSection.whenIsEnabled

  }
  When("""^I complete the fulfilment request for '1: Desert Seas' of 'Desert Seas'$""") { () =>
    logger.info(scenarioMarker, s"Complete the fulfilment request")
  }
  Then(
    """^the current request for licence number (\d+) is displayed on the 'Current Requests' page under 'No Required By Date' section$""") {
    (licenceId: Int) =>
      logger.info(scenarioMarker, s"Verify asset is on the current request page with licence number: $licenceId")
  }

}
