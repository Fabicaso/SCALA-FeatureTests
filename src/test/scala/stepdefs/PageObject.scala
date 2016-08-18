package stepdefs

import java.util

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.{By, WebElement}


class PageObject {

  val driver = new ChromeDriver()
  driver.manage().window().maximize()

  //*** Page Title ***
  def PageTitle: String = driver.getTitle()

  //*** Close WebDriver ***
  def Close() = driver.close()


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


  def AssetButtonList: util.List[WebElement] = driver.findElementsByCssSelector("div.u-flex.u-flexAlignItemsCenter.u-flexJustifyEnd")


  def ProductionIDList: util.List[WebElement] = driver.findElementsByCssSelector("div>div>div:nth-child(1)>label")


  //def NoAssetFoundList: util.List[WebElement] = driver.findElementsByCssSelector("#div.u-flex.u-flexAlignItemsCenter.u-flexJustifyEnd>div>span>")

  def NoAssetFoundText: String = driver.findElementByClassName("class=u-fcWarn").getText()

  //--------SKIP


  //*** Select Skip All button ***
  def SkipAllButton: WebElement = driver.findElementByCssSelector("[data-hook='pick-skip-group']")


  //*** Select a Skipped Asset ***
  def SelectSkippedAsset: WebElement = driver.findElementByCssSelector("[data-hook='picker-button-skipped']")


  //--------Asset Format

  //*** Format Popup Window ***
  def SelectFormat: WebElement = driver.findElementByCssSelector("[data-hook='production-asset-row']")


  //*** Create Button ***
  def CreateButton: WebElement = driver.findElementByCssSelector("[data-hook='pick-assets-submit']")

  //*** Select firstProduction ID
  def ProductionID: String = driver.findElementByCssSelector("div.Grid-cell.u-md-size2of3.u-lg-size3of6>div>div>section>div>ul>li:nth-child(1)>div>div>div:nth-child(1)>label").getText()


}
