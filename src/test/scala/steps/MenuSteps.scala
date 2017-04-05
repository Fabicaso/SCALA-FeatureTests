package steps

import itv.fulfilmentplanning.pageobjects.{
  CurrentRequestsPageObject,
  MenuPageObject,
  NewRequestPageObject,
  OverviewPageObject
}
import itv.fulfilmentplanning.{AssetRequested, TestData}

import scala.concurrent.duration._

class MenuSteps extends BaseSteps with MenuPageObject {

  override implicit val patienceConfig = PatienceConfig(4.seconds, 100.milliseconds)

  And("""^I enter the following Licence Number (\d+)$""") { (licenceId: Int) =>
    logger.info(scenarioMarker, s"Go to licence number: $licenceId")
    eventually(click on EnterLicenceSection.whenIsDisplayed)
    eventually(numberField(LicenceInput)).value = licenceId.toString
    submit()
  }

  Then("""^the '(.*)' is displayed$""") { (textDisplayed: String) =>
    logger.info(scenarioMarker, s"Warning message to be displayed is: $textDisplayed")
    ExactText(textDisplayed).whenIsDisplayed
    logger.info(scenarioMarker, "Success!")
  }

  Then("""^the 'Invalid Licence Error Msg' is displayed '(.*)'$""") { (licenceInvalidWarningMessage: String) =>
    logger.info(scenarioMarker, s"Warning message to be displayed is: $licenceInvalidWarningMessage")
    ExactText(licenceInvalidWarningMessage).whenIsDisplayed
    logger.info(scenarioMarker, "Success!")
  }

}
