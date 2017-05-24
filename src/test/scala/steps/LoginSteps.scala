package steps

import itv.fulfilmentplanning.Credentials
import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._
import scala.util.Try

class LoginSteps extends BaseSteps with SignInPageObject with GoogleAuthPageObject with OfflineAccess {

  override implicit val patienceConfig = PatienceConfig(7.seconds, 200.milliseconds)

  Given("""^I am on the 'Login' page$""") { () =>
    logger.info(scenarioMarker, "Open home page")
    go to config.homePageUrl
  }

  When("""^I login with the following valid credentials$""") { () =>
    if (requiresToLogin()) {
      logger.info(scenarioMarker, "Click on sign in")
      SignInButton.clickWhenIsDisplayed

      val newWay            = Try(OldEmail.whenIsDisplayed).isFailure
      val (email, password) = if (newWay) Email -> Password else OldEmail -> OldPassword

      eventually(emailField(email)).value = Credentials.testCredentials.email
      logger.info(scenarioMarker, "Set email")
      if (newWay)
        IdentifierNext.clickWhenIsDisplayed
      else
        submit()

      password.whenIsDisplayed.isDisplayed shouldBe true
      eventually(pwdField(password)).value = Credentials.testCredentials.password
      logger.info(scenarioMarker, "Set password")
      if (newWay)
        PasswordNext.clickWhenIsDisplayed
      else {
        submit()
        AllowOfflineAccess.clickWhenIsEnabled
      }
    } else {
      logger.info(scenarioMarker, "I am already logged in!")
    }
  }

  def requiresToLogin(): Boolean = !isItemPresentInLocalStorage("usersession")

}
