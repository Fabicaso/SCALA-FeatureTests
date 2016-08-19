package stepdefs

import org.scalatest.selenium.{Driver, WebBrowser}

trait NewFulfilmentOrderPageObject extends WebBrowser with Driver {

  //*** Licence Number Field ***
  def LicenceNumberField: IdQuery = id("licence-input")

  //*** find the "Select Asset" button ***
  def SelectAssetButton: CssSelectorQuery = cssSelector("[data-hook='licence-id-submit']")


  //----------Asset

  //*** Select the Asset button on the Pick Asset page ***
  def AssetButton: CssSelectorQuery = cssSelector("[data-hook='picker-button-show']")


  //loop count number of Asset buttons


  def AssetButtonList: CssSelectorQuery = cssSelector("div.u-flex.u-flexAlignItemsCenter.u-flexJustifyEnd")


  def ProductionIDList: CssSelectorQuery = cssSelector("div>div>div:nth-child(1)>label")


  //def NoAssetFoundList: util.List[WebElement] = cssSelector("#div.u-flex.u-flexAlignItemsCenter.u-flexJustifyEnd>div>span>")

  //--------SKIP


  //*** Select Skip All button ***
  def SkipAllButton: CssSelectorQuery = cssSelector("[data-hook='pick-skip-group']")


  //*** Select a Skipped Asset ***
  def SelectSkippedAsset: CssSelectorQuery = cssSelector("[data-hook='picker-button-skipped']")


  //--------Asset Format

  //*** Format Popup Window ***
  def SelectFormat: CssSelectorQuery = cssSelector("[data-hook='production-asset-row']")


  //*** Create Button ***
  def CreateButton: CssSelectorQuery = cssSelector("[data-hook='pick-assets-submit']")

  //*** Select firstProduction ID
  def ProductionID: CssSelectorQuery = cssSelector("div.Grid-cell.u-md-size2of3.u-lg-size3of6>div>div>section>div>ul>li:nth-child(1)>div>div>div:nth-child(1)>label")

  def clientProfileInput: NameQuery = name("clientProfile")
}
