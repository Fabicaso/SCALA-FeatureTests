package steps

import itv.fulfilmentplanning.Credentials
import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._

class LoginSteps extends BaseSteps with SignInPageObject with GoogleAuthPageObject with OfflineAccess {

  override implicit val patienceConfig = PatienceConfig(5.seconds, 200.milliseconds)

  Given("""^I am on the 'Login' page$""") { () =>
    logger.info(scenarioMarker, "Open home page")
    go to config.homePageUrl
  }

  When("""^I login with the following valid credentials$""") { () =>
    if (requiresToLogin()) {
      logger.info(scenarioMarker, "Click on sign in")
      SignInButton.clickWhenIsDisplayed
      val (email, password) = Email -> Password
      eventually(emailField(email)).value = Credentials.testCredentials.email
      logger.info(scenarioMarker, "Set email")
      IdentifierNext.clickWhenIsDisplayed
      password.whenIsDisplayed.isDisplayed shouldBe true
      eventually(pwdField(password)).value = Credentials.testCredentials.password
      logger.info(scenarioMarker, "Set password")
      PasswordNext.clickWhenIsDisplayed
    } else {
      logger.info(scenarioMarker, "I am already logged in!")
    }
  }
  def requiresToLogin(): Boolean = !isItemPresentInLocalStorage("usersession")

}
