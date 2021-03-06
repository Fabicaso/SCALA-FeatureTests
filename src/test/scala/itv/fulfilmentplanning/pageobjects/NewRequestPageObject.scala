package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait NewRequestPageObject extends WebBrowser {

  def CloseOnAssetPickerBox(productionId: String)       = id(s"prod-$productionId-picker-button-close")
  def newRequestSeriesRow(series: String)               = id(s"request-series-$series-selection-name")
  def isRequestedOrFulfilledCheck(productionId: String) = id(s"prod-$productionId-selection-isRequestedOrFulfilled")
  def VerifyAssetHasBeenSelected(productionId: String)  = id(s"prod-$productionId-selection-button-selected")
  def RequestNextButton                                 = id("request-confirm-button")
  def ProductionIdButton(productionId: String)          = id(s"prod-$productionId-selection-button")
  def NoAssetsFound(productionId: String)               = id(s"prod-$productionId-selection-noassets}")
  def SelectMultipleAssets                              = id("request-multiselect-checkbox")
  def ProductionIdMultiple(productionId: String)        = id(s"prod-$productionId-selection-multiple}")
  def ProductionIdSelected(productionId: String)        = id(s"prod-$productionId-selection-button-selected")
  def PageLoadedRequest                                 = id("page-loaded-request")
  def PageLoadedAssetRequestDates                       = id("page-loaded-request-dates")
  def CloseAssetBoxButton(productionId: String)         = id(s"prod-$productionId-picker-button-close")

  def NewRequestAssetButton(productionId: String) = xpath(s"//span[contains(text(),'$productionId')]")

  def AssetsToSelect(assetToSelect: String, productionId: String): List[Query] = assetToSelect match {
    case "first asset"     => List(PickFirstAsset(productionId))
    case "multiple assets" => List(PickFirstAsset(productionId), PickSecondAsset(productionId))
    case _                 => List()

  }

  def PickFirstAsset(productionId: String) = xpath(s"//div[@id='prod-$productionId-picker-assets']//tr[1]/td[1]")

  def PickSecondAsset(productionId: String) = xpath(s"//div[@id='prod-$productionId-picker-assets']//tr[2]/td[1]")

}
