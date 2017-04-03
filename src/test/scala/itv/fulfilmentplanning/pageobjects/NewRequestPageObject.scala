package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait NewRequestPageObject extends WebBrowser {
  def VerifyAssetHasBeenSelected(productionId: String) = id(s"prod-$productionId-selection-button-selected")
  def SelectMultipleAssets                             = id("request-multiselect-checkbox")
  def RequestNextButton                                = id("request-confirm-button")
  def ProductionIdButton(productionId: String)         = id(s"prod-$productionId-selection-button")
  def ProductionIdSelected(productionId: String)       = id(s"prod-$productionId-selection-button-selected")
  def ProductionIdMultple(productionId: String)        = id(s"prod-$productionId-selection-multiple")
  def PageLoadedRequest                                = id("page-loaded-request")

  def PickFirstAsset(productionId: String) = xpath(s"//div[@id='prod-$productionId-picker-assets']//tr[1]/td[1]")

  def PickSecondAsset(productionId: String) = xpath(s"//div[@id='prod-$productionId-picker-assets']//tr[2]/td[1]")

  def AssetsToSelect(assetToSelect: String, productionId: String): List[Query] = assetToSelect match {
    case "first asset"     => List(PickFirstAsset(productionId))
    case "multiple assets" => List(PickFirstAsset(productionId), PickSecondAsset(productionId))
    case _                 => List()

  }

}
