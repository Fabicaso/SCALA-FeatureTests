package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait OverviewPageObject extends WebBrowser {

  def CreateNewRequestButton = id("create-new-request-button")

}
