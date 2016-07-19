package stepdefs

import java.util.concurrent.TimeUnit

import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.{By, WebElement}
import org.scalatest.Matchers

class CreateNewFulfilmentOrderSteps extends ScalaDsl with EN with Matchers {
  //val driver = new RemoteWebDriver(new java.net.URL("http", "10.206.45.185", 8080, "/wd/hub"), DesiredCapabilities.chrome())
  val driver = new ChromeDriver()
  driver.manage().timeouts().implicitlyWait(30 , TimeUnit.SECONDS)

  Given("""^I have navigated to the new fulfilment request page$""") { () =>
    driver.navigate().to("http://frontend.stg.fp.itv.com/new-fulfilment-request")
  }

  And("""^I have entered licence id "(.+)"$""") { (licenceId: String) =>
    val licenceInput: WebElement = driver.findElement(By.id("licence-input"))
    println(s"licence input: $licenceInput")
    licenceInput.sendKeys(licenceId)
  }

  When("""^I click to create the new work fulfilment request$""") { () =>
    val selectAssetsElem: WebElement = driver.findElementByClassName("FormGroup-input") //findElement(By.partialLinkText("Select Assets"))

    println(s"select assets elem: $selectAssetsElem with text '${selectAssetsElem.getText}'")
    selectAssetsElem.click()
  }

  Then("""^I am taken to the new fulfilment page$""") { () =>
    driver.findElementsByTagName("h1") shouldNot be(empty)
  }

  //After(_ => driver.quit())
}