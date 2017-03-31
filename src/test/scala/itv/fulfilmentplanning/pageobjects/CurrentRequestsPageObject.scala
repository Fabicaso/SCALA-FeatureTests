package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait CurrentRequestsPageObject extends WebBrowser {

  def RequestList: ClassNameQuery = className("page-requestList")

  def CurrentRequestsPageLoaded: IdQuery = id("page-loaded-request-list")

  def RequestedAssetsForDate(date: Option[String]) =
    id(s"request-list-section-header-${date.getOrElse("noDate")}-date")

  def AssetWithProductionId(productionId: String) = xpath(s"//span[contains(text(),'$productionId')] ")

}
