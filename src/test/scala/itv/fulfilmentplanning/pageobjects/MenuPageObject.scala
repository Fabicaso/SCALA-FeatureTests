package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait MenuPageObject extends WebBrowser {

  def EnterLicenceSection: XPathQuery = xpath("//div[contains(text(),'Enter Licence')]")

  def Breadcrumb = id("breadcrumb")

  def LicenceInput = id("licence-input")

}
