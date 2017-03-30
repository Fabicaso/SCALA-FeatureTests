package steps

import itv.fulfilmentplanning.pageobjects.{CurrentRequestsPageObject, MenuPageObject}

import scala.concurrent.duration._

class CurrentRequestsSteps extends BaseSteps with CurrentRequestsPageObject with MenuPageObject {

  override implicit val patienceConfig = PatienceConfig(2.seconds, 100.milliseconds)

  And("""^I am on the 'New Request' page using the following licence number (\d+)$""") { (licenceId: Int) =>
    logger.info(scenarioMarker, s"Go to licence number: $licenceId")
    click on EnterLicenceSection.whenIsDisplayed
    eventually(numberField(LicenceInput)).value = licenceId.toString
    submit()
  }

  And(
    """^the current request for licence number (\d+) is displayed on the 'Current Requests' page under 'No Required By Date' section$""") {
    (licenceId: Int) =>
      logger.info(scenarioMarker, s"Verify asset is on the current request page with licence number: $licenceId")
  }

  Then("""^the 'Current Requests' page is displayed$""") { () =>
    logger.info(scenarioMarker, "Go to current request")
    val requestListElement =
      CurrentRequestsPageLoaded.whenIsEnabled(patienceConfig = PatienceConfig(10.seconds, 1.second), scenarioMarker)
    requestListElement.isEnabled shouldBe true
    logger.info(scenarioMarker, "Success!")
  }

}
