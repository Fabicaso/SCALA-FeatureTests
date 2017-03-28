package steps

import com.typesafe.config.ConfigFactory
import itv.fulfilmentplanning.{Config, Credentials}
import itv.fulfilmentplanning.pageobjects.{
  CurrentRequestsPageObject,
  GoogleAuthPageObject,
  OfflineAccess,
  SignInPageObject
}

import scala.concurrent.duration._

class LoginSteps
    extends BaseSteps
    with SignInPageObject
    with CurrentRequestsPageObject
    with GoogleAuthPageObject
    with OfflineAccess {

  val config = Config.load(ConfigFactory.load())

  override implicit val patienceConfig = PatienceConfig(2.seconds, 100.milliseconds)

  Given("""^I am on the 'Login' page$""") { () =>
    logger.info(scenarioMarker, "Open login page")
    go to config.homePageUrl
  }

  When("""^I login with the following valid credentials$""") { () =>
    logger.info(scenarioMarker, "Click on sign in")
    click on SignInButton.whenIsDisplayed

    eventually(emailField(Email)).value = Credentials.testCredentials.email
    logger.info(scenarioMarker, "Set email")
    submit()
    Password.whenIsDisplayed.isDisplayed shouldBe true
    eventually(pwdField(Password)).value = Credentials.testCredentials.password
    logger.info(scenarioMarker, "Set password")
    submit()
    logger.info(scenarioMarker, "finding submit_approve_access")

    click on AllowOfflineAccess.whenIsEnabled
    logger.info(scenarioMarker, "submit_approve_access is been clicked")

  }

  Then("""^the 'Current Requests' page is displayed$""") { () =>
    logger.info(scenarioMarker, "Go to current request")
    val requestListElement =
      RequestList.whenIsDisplayed(patienceConfig = PatienceConfig(10.seconds, 1.second), scenarioMarker)
    requestListElement.isDisplayed shouldBe true
    logger.info(scenarioMarker, "Success!")
  }

}
