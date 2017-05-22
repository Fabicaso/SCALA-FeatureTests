package steps

import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._

class MenuSteps extends BaseSteps with MenuPageObject {

  override implicit val patienceConfig = PatienceConfig(4.seconds, 200.milliseconds)

  And("""^I enter the following Licence Number '(.*)'$""") { (licenceId: String) =>
    logger.info(scenarioMarker, s"Go to licence number: $licenceId")
    EnterLicenceSection.clickWhenIsDisplayed
    eventually(numberField(LicenceInput)).value = licenceId
    submit()
  }

  Then("""^the '(.*)' is displayed$""") { (textDisplayed: String) =>
    logger.info(scenarioMarker, s"Warning message to be displayed is: $textDisplayed")
    ExactText(textDisplayed).whenIsDisplayed
  }

  Then("""^the 'Invalid Licence Error Msg' is displayed '(.*)'$""") { (licenceInvalidWarningMessage: String) =>
    logger.info(scenarioMarker, s"Warning message to be displayed is: $licenceInvalidWarningMessage")
    ExactText(licenceInvalidWarningMessage).whenIsDisplayed
  }

  Then("""^the 'ITV' logo is displayed$"""){ () =>
    logger.info(scenarioMarker,s"ITV logo is correctly displayed")
    ITVLogo.whenIsDisplayed
  }

  And ("""^the 'Craft' logo is displayed$"""){ () =>
    logger.info(scenarioMarker,s"Craft logo is correctly displayed")
    CraftLogo.whenIsDisplayed
  }


}
