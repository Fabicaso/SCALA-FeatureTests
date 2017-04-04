package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait CommonPageObject extends WebBrowser {
  def FindExactText(value: String) = xpath(s"//*[text()='$value']")
}
