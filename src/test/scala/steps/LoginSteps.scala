package steps

import itv.fulfilmentplanning.Credentials
import itv.fulfilmentplanning.pageobjects._
import org.openqa.selenium.JavascriptExecutor

import scala.concurrent.duration._

class LoginSteps extends BaseSteps with SignInPageObject with GoogleAuthPageObject with OfflineAccess {

  override implicit val patienceConfig = PatienceConfig(4.seconds, 200.milliseconds)

  Given("""^I am on the 'Login' page$""") { () =>
    logger.info(scenarioMarker, "Open home page")
    go to config.homePageUrl
  }

  When("""^I login with the following valid credentials$""") { () =>
    if (requiresToLogin()) {
      logger.info(scenarioMarker, "Click on sign in")
      SignInButton.clickWhenIsDisplayed

      eventually(emailField(Email)).value = Credentials.testCredentials.email
      logger.info(scenarioMarker, "Set email")
      submit()
      Password.whenIsDisplayed.isDisplayed shouldBe true
      eventually(pwdField(Password)).value = Credentials.testCredentials.password
      logger.info(scenarioMarker, "Set password")
      submit()
      logger.info(scenarioMarker, "finding submit_approve_access")

      AllowOfflineAccess.clickWhenIsEnabled
      logger.info(scenarioMarker, "submit_approve_access is been clicked")
    } else {
      logger.info(scenarioMarker, "I am already logged in!")
    }
  }

  def requiresToLogin(): Boolean = !isItemPresentInLocalStorage("usersession")

  private def isItemPresentInLocalStorage(item: String) = webDriver match {
    case executor: JavascriptExecutor =>
      Option(executor.executeScript(String.format("return window.localStorage.getItem('%s');", item))).nonEmpty
    case _ => false
  }

}
