package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait MenuPageObject extends WebBrowser {

  def EnterLicenceSection = id("globalNav-enter-licence")

  def CurrentRequestSection = id("globalNav-current-requests")

  def Breadcrumb = id("breadcrumb")

  def LicenceInput = id("licence-input")

}
