package steps

import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._

class MenuSteps
    extends BaseSteps
    with MenuPageObject
    with OverviewPageObject
    with CurrentRequestsPageObject
    with NewRequestPageObject {

  override implicit val patienceConfig = PatienceConfig(15.seconds, 300.milliseconds)

  And("""^I enter the following Licence Number '(.*)'$""") { (licenceId: String) =>
    logger.info(scenarioMarker, s"Go to licence number: $licenceId")
    eventually {
      EnterLicenceSection.clickWhenIsDisplayed
      numberField(LicenceInput).value = licenceId
      submit()
    }
  }

  Then("""^the '(.*)' is displayed$""") { (textDisplayed: String) =>
    logger.info(scenarioMarker, s"Warning message to be displayed is: $textDisplayed")
    ExactText(textDisplayed).whenIsDisplayed
  }

  Then("""^the 'Invalid Licence Error Msg' is displayed '(.*)'$""") { (licenceInvalidWarningMessage: String) =>
    logger.info(scenarioMarker, s"Warning message to be displayed is: $licenceInvalidWarningMessage")
    ExactText(licenceInvalidWarningMessage).whenIsDisplayed
  }

  Then("""^the 'ITV' logo is displayed$""") { () =>
    logger.info(scenarioMarker, s"ITV logo is correctly displayed")
    ITVLogo.whenIsDisplayed
  }

  And("""^the 'Craft' logo is displayed$""") { () =>
    logger.info(scenarioMarker, s"Craft logo is correctly displayed")
    CraftLogo.whenIsDisplayed
  }

  When("""^I click on the breadcrumb for the '(.*)' page$""") { (page: String) =>
    logger.info(scenarioMarker, s"Checking that $page breadcrumb link is working")
    page match {
      case "Current Requests" => PageBreadcrumbLink("current-requests").clickWhenIsDisplayed
      case "New Request"      => PageBreadcrumbLink("request").clickWhenIsDisplayed
      case "Send Request"     => PageBreadcrumbLink("send").clickWhenIsDisplayed
      case "Overview"         => PageBreadcrumbLink("overview").clickWhenIsDisplayed
    }
  }

  Then("""^the '(.*)' page is displayed$""") { (page: String) =>
    logger.info(scenarioMarker, s"Checking that $page is opened after clicking the $page breadcrumb")
    eventually {
      waitPageToBeLoaded(page)
    }
  }

  private def waitPageToBeLoaded(page: String) =
    page match {
      case "Overview" =>
        PageLoadedOverview
          .whenIsEnabled(PatienceConfig(10.seconds, 500.milliseconds), scenarioMarker)
          .isEnabled shouldBe true
      case "Current Requests" =>
        CurrentRequestsPageLoaded
          .whenIsEnabled(patienceConfig = PatienceConfig(10.seconds, 500.milliseconds), scenarioMarker)
          .isEnabled shouldBe true
      case "New Request" =>
        PageLoadedRequest
          .whenIsEnabled(patienceConfig = PatienceConfig(10.seconds, 500.milliseconds), scenarioMarker)
          .isEnabled shouldBe true
      case "Required By" =>
        PageLoadedAssetRequestDates
          .whenIsEnabled(patienceConfig = PatienceConfig(10.seconds, 500.milliseconds), scenarioMarker)
          .isEnabled shouldBe true
    }
}
