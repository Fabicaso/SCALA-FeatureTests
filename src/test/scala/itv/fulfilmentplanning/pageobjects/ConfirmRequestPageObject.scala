package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait ConfirmRequestPageObject extends WebBrowser {

  def RequestConfirmLoaded = id("page-loaded-request-confirm")

  def SendRequestButton = id("request-sidebar-form-button-submit")

  def SentRequestConfirmation = xpath("//p[contains(text(),'Request sent')] ")

}
