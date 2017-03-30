package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait NewRequestPageObject extends WebBrowser {
  def PageLoadedRequest = id("page-loaded-request")

  def ProductionIdButton(productionId: String) = id(s"prod-$productionId-selection-button")

  def PickFirstAsset(productionId: String) = xpath(s"//div[@id='prod-$productionId-picker-assets']//tr[1]/td[1]")

  def VerifyAssetHasBeenSelected(productionId: String) = id(s"prod-$productionId-selection-button-selected")

  def RequestConfirmButton = id("request-confirm-button")

}
