package stepdefs

import java.util.concurrent.TimeUnit

import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.{By, WebElement}
import org.scalatest.Matchers

class CreateNewFulfilmentOrderSteps extends ScalaDsl with EN with Matchers {

  val driver = new ChromeDriver()
  driver.manage().window().maximize()
  driver.manage().timeouts().implicitlyWait(10 , TimeUnit.SECONDS)

  Given("""^I have navigated to the new fulfilment request page$""") { () =>
    driver.navigate().to("http://craft.stg.fp.itv.com")
  }

  And("""^I have entered licence id "(.+)"$""") { (licenceId: String) =>
    val licenceInput: WebElement = driver.findElement(By.id("licence-input"))
    println(s"licence input: $licenceId")
    licenceInput.sendKeys(licenceId)
  }

  When("""^I click to create the new work fulfilment request$""") { () =>
    val selectAssetsElem: WebElement = driver.findElementByXPath("//*[@id='app']/div/main/article/div/div/div[2]/div/form/div[2]/div/button")

    println(s"select assets elem: $selectAssetsElem with text '${selectAssetsElem.getText}'")
    selectAssetsElem.click()
  }

  Then("""^I am taken to the new fulfilment page$""") { () =>
    driver.findElementsByTagName("h1") shouldNot be(empty)

    driver.close()

  }



}