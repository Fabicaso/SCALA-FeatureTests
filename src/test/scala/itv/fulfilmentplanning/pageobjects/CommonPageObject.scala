package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait CommonPageObject extends WebBrowser {
  def ExactText(value: String)    = xpath(s"//*[text()='$value']")
  def ContainsText(value: String) = xpath(s"//*[contains(@id, '$value')]")

}
