package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait OverviewPageObject extends WebBrowser {

  def PageLoadedOverview = id("page-loaded-overview")

  def CreateNewRequestButton = id("create-new-request-button")

}
