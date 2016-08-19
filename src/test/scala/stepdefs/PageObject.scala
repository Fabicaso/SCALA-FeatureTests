package stepdefs

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.{By, WebElement}

import scala.collection.JavaConversions._

class PageObject {

  val driver = new ChromeDriver()
  driver.manage().window().maximize()

  //*** Page Title ***
  def PageTitle: String = driver.getTitle()

  //*** Quit WebDriver ***
  def Quit() = driver.quit()


  //*** Opens Homepages ***
  def OpenHomePage(link: String) = driver.navigate().to(link)

  //*** Licence Number Field ***
  def LicenceNumberField: WebElement = driver.findElement(By.id("licence-input"))

  //*** find the "Select Asset" button ***
  def SelectAssetButton: WebElement = driver.findElementByCssSelector("[data-hook='licence-id-submit']")


  //----------Asset

  //*** Select the Asset button on the Pick Asset page ***
  def AssetButton: WebElement = driver.findElementByCssSelector("[data-hook='picker-button-show']")


  //loop count number of Asset buttons


  def AssetButtonList: Vector[WebElement] = driver.findElementsByCssSelector("div.u-flex.u-flexAlignItemsCenter.u-flexJustifyEnd").toVector


  def ProductionIDList: Vector[WebElement] = driver.findElementsByCssSelector("div>div>div:nth-child(1)>label").toVector


  //def NoAssetFoundList: util.List[WebElement] = driver.findElementsByCssSelector("#div.u-flex.u-flexAlignItemsCenter.u-flexJustifyEnd>div>span>")

  def NoAssetFoundText: String = driver.findElementByClassName("class=u-fcWarn").getText()

  //--------SKIP


  //*** Select Skip All button ***
  def SkipAllButton: WebElement = driver.findElementByCssSelector("[data-hook='pick-skip-group']")


  //*** Select a Skipped Asset ***
  def SelectSkippedAsset: WebElement = driver.findElementByCssSelector("[data-hook='picker-button-skipped']")


  //--------Asset Format

  //*** Format Popup Window ***
  def SelectFormat: Option[WebElement] = driver.findElementsByCssSelector("[data-hook='production-asset-row']").toVector.headOption


  //*** Create Button ***
  def CreateButton: WebElement = driver.findElementByCssSelector("[data-hook='pick-assets-submit']")

  //*** Select firstProduction ID
  def ProductionID: String = driver.findElementByCssSelector("div.Grid-cell.u-md-size2of3.u-lg-size3of6>div>div>section>div>ul>li:nth-child(1)>div>div>div:nth-child(1)>label").getText()

  def clientProfileInput = driver.findElementByName("clientProfile")
}
