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

    val SelectAssetButton: WebElement = driver.findElementByClassName("Button")
    SelectAssetButton.click()
    println("Select Asset Button clicked")

    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
  }

  When("""^I click to create the new work fulfilment request$""") { () =>

    val selectAssetsButton1: WebElement = driver.findElementByXPath("html/body/div[1]/div/main/article/div/div/div[2]/div/div/section[1]/div/ul/li/div[2]/div/button")
    println("select assets elem button clicked and Format pop-up window open'")
    selectAssetsButton1.click()
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)

    val selectSkipButton: WebElement = driver.findElementByXPath("//*[@id='app']/div/main/article/div/div/div[2]/div/div/section[3]/header/div/button")
    selectSkipButton.click()
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)

    val selectFormatButton: WebElement = driver.findElementByXPath( "/html/body/div[2]/div/div/div/div[1]/div/table/tbody/tr[4]/td[1]")
    selectFormatButton.click()
    println("select Format elem clicked and Format pop-up window closed'")
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)

    val selectCreateButton: WebElement = driver.findElementByXPath("//*[@id='app']/div/main/article/div/div/div[3]/aside/div[2]/button[1]")
    selectCreateButton.click()

  }

  Then("""^I have Created a Fulfilment Order: COMPLETE$""") { () =>
    driver.findElementsByTagName("h1") shouldNot be(empty)
val Complete: WebElement =driver.findElementByXPath("//*[@id='app']/div/main/article/div/div/div[2]/div/h1")

val ComplString : String = Complete.getText()
    println(s"Fulfillment Order Creation Status:$ComplString")

    assert(ComplString == "Complete")


    driver.close()

  }



}