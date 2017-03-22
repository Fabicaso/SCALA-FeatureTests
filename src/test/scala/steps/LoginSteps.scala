package steps

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.StrictLogging
import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.{Config, Credentials}
import itv.fulfilmentplanning.pageobjects.{CurrentRequestsPageObject, GoogleAuthPageObject, SignInPageObject}
import itv.fulfilmentplanning.utils.{WebBrowserUtils, WebDriverOps}
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.WebBrowser
import org.scalatest.{Assertions, Inspectors, Matchers}

import scala.concurrent.duration._

class LoginSteps
   extends BaseSteps
    with SignInPageObject
    with CurrentRequestsPageObject
    with GoogleAuthPageObject {

  val config = Config.load(ConfigFactory.load())

  override implicit val patienceConfig = PatienceConfig(25.seconds, 500.milliseconds)

  Given("""^I am on the 'Login' page$""") { () =>
    logger.info("Open login page")
    go to config.homePageUrl
  }

  When("""^I login with the following valid credentials$""") { (arg0: DataTable) =>
    logger.info("Click on sign in")
    click on eventually(SignInButton.findElementOrFail)
    eventually(emailField(Email)).value = Credentials.testCredentials.email
    logger.info("Set email")
    submit()
    eventually(Password.findElementOrFail.isDisplayed shouldBe true)
    eventually(pwdField(Password)).value = Credentials.testCredentials.password
    logger.info("Set password")
    submit()
  }

  Then("""^the 'Current Requests' page is displayed$""") { () =>
    eventually {
      logger.info("Waiting for current request to be displayed")
      RequestList.findElementOrFail.isDisplayed shouldBe true
    }
  }

}
