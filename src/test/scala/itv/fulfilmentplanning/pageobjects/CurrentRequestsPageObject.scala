package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait CurrentRequestsPageObject extends WebBrowser {

  def RequestList: ClassNameQuery = className("page-requestList")

  def CurrentRequestsPageLoaded: IdQuery = id("page-loaded-request-list")

}
