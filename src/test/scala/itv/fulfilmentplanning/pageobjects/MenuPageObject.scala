package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait MenuPageObject extends WebBrowser {

  def EnterLicenceSection: XPathQuery = xpath("//div[.//text()='Current Request']")

}
