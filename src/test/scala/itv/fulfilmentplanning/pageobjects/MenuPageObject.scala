package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait MenuPageObject extends WebBrowser {

  def EnterLicenceSection              = id("globalNav-enter-licence")
  def CurrentRequestSection            = id("globalNav-current-requests")
  def Breadcrumb                       = id("breadcrumb")
  def LicenceInput                     = id("licence-input")
  def ITVLogo                          = xpath("//span[contains(@class, 'Logo--itv')]")
  def CraftLogo                        = xpath("//*[@id=\"app\"]/div/nav/div[1]/div/div/div[1]/div/a/p/span[3]")
  def PageBreadcrumbLink(page: String) = id(s"breadcrumb-$page")
  //def ExactText(value: String)    = xpath(s"//*[text()='$value']")

}
