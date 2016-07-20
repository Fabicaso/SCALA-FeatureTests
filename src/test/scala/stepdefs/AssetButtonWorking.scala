package stepdefs
import java.util.concurrent.TimeUnit
import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.{By, WebElement}
import org.scalatest.Matchers

class AssetButtonWorking extends ScalaDsl with EN with Matchers {
  //val driver = new RemoteWebDriver(new java.net.URL("http", "10.206.45.185", 8080, "/wd/hub"), DesiredCapabilities.chrome())
  val driver = new ChromeDriver()
  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
  println("Scenario 4: Test that the Asset button is working on the New Fulfilment Request page")


  Given("""^I go to the the new fulfilment request page$""") { () =>
    driver.navigate().to("http://frontend.stg.fp.itv.com/new-fulfilment-request")
  }

  And("""^I have enter the licence id "(.+)"$""") { (licenceId: String) =>
    val licenceInput: WebElement = driver.findElement(By.id("licence-input"))
    println(s" /licence input: $licenceId")
    licenceInput.sendKeys(licenceId)
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)


    val SelectAssetButton: WebElement = driver.findElementByClassName("Button")
    SelectAssetButton.click()
    println("Select Asset Button clicked")
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)


  }

  When("""^I click the Asset button$""") { () =>
    val selectAssetsButton: WebElement = driver.findElementByXPath("html/body/div[1]/div/main/article/div/div/div[2]/div/div/section[1]/div/ul/li/div[2]/div/button")
    println("select assets elem button clicked and Format pop-up window open'")
    selectAssetsButton.click()
    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)
  }

  Then("""^I can select the asset format$""") { () =>
    val selectFormatButton: WebElement = driver.findElementByXPath( "/html/body/div[2]/div/div/div/div[1]/div/table/tbody/tr[4]/td[1]")
        selectFormatButton.click()
    println("select Format elem clicked and Format pop-up window closed'")



  }


}

