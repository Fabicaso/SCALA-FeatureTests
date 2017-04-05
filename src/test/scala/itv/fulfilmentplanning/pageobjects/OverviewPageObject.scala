package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait OverviewPageObject extends WebBrowser {

  def PageLoadedOverview = id("page-loaded-overview")

  def CreateNewRequestButton = id("create-new-request-button")

  def NewRequestAssetButton(productionId: String) = xpath(s"//span[contains(text(),'$productionId')] ")

  def LicenceStartDate = id("overview-header-startDate")

}
