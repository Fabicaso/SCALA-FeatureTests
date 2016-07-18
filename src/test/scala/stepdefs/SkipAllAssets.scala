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

      println("I'm in the fulfilment order page ")
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
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
  }

When ("""^I click on skip all button$""")
  {
    () => val SkipButton : WebElement =driver.findElementByXPath("html/body/div[1]/div/main/article/div/div/div[2]/div/div/section[1]/header/div/button")
      SkipButton.click()
      println("SkipAll Button 1 clicked")
      driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)

      val SkipButton2 : WebElement =driver.findElementByXPath("html/body/div[1]/div/main/article/div/div/div[2]/div/div/section[2]/header/div/button")
        SkipButton2.click()
        println("SkipAll Button 2 clicked")
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)

     val SkipButton3 : WebElement =driver.findElementByXPath("html/body/div[1]/div/main/article/div/div/div[2]/div/div/section[3]/header/div/button")
      SkipButton3.click()
      println("SkipAll Button 3 clicked")
      driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)

  }


  Then ("""^All Assets should be Skipped$""")
  {

    () => val AssetStatusWebElem : WebElement = driver.findElementsByCssSelector("[data-hook='open-picker']").get(0)
          val AssetStatus = AssetStatusWebElem.getText
          assert(AssetStatus =="Skipped")


     val Episode : WebElement= driver.findElementsByCssSelector("[id='itemTitle-1/5634/0032/33#004']").get(0)
     val EpisodeText =  Episode.getText



     println(s"Episode : $EpisodeText has Asset status: $AssetStatus")







  }


  //After(_ => driver.close())
}