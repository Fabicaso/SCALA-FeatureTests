package stepdefs

import java.util.concurrent.TimeUnit

import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.{By, WebElement}
import org.scalatest.Matchers




class SkipAllAssets extends ScalaDsl with EN with Matchers {
    val driver = new ChromeDriver()
  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)



  println("Scenario: Navigate to new fulfilment order page from a licence and hit the Skip All asset button")



  Given ("""^I open the new fulfilment request page$""") {

    ()=> driver.navigate().to("http://frontend.stg.fp.itv.com/new-fulfilment-request")

      print("I'm in the fulfilment order page ")
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
  }

  And ("""^I have typed in the licence id "(.+)"$""") { (licenceID : String)
    => val licenceip : WebElement = driver.findElement(By.id("licence-input"))
    licenceip.sendKeys(licenceID)
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

    println("The Licence ID typed in is:" +licenceID)

  val SelectAssetButton: WebElement = driver.findElementByClassName("Button")
  SelectAssetButton.click()
    println("Select Asset Button clicked")
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
  }

When ("""^I click on skip all button$""")
  {
    () => val SkipButton : WebElement =driver.findElementByXPath("html/body/div[1]/div/main/article/div/div/div[2]/div/div/section[1]/header/div/button")
      SkipButton.click()
      println("SkipAllButton 1 clicked")
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)

      val SkipButton2 : WebElement =driver.findElementByXPath("html/body/div[1]/div/main/article/div/div/div[2]/div/div/section[2]/header/div/button")
        SkipButton2.click()
        println("SkipAllButton 2 clicked")
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)

      val SkipButton3 : WebElement =driver.findElementByXPath("html/body/div[1]/div/main/article/div/div/div[2]/div/div/section[3]/header/div/button")
      SkipButton3.click()
      println("SkipAllButton 3 clicked")
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)

  }


  Then ("""^All Assets should be Skipped$""")
  {

    () => val AssetStatusWebElem : WebElement = driver.findElementsByCssSelector("[data-hook='open-picker']").get(0)

      println(s"AssetStatusWebElem: $AssetStatusWebElem ${AssetStatusWebElem.getText}")

      AssetStatusWebElem.getText
     val AssetStatus = AssetStatusWebElem.getText // AssetStatusWebElem.getCssValue("title='Change selection'").toString

    println("This is the Asset Status: " + AssetStatus)

      assert(AssetStatus =="Skipped")

      println("Prod ID Status is SKIPPED!")

  }


  After(_ => driver.quit())
}