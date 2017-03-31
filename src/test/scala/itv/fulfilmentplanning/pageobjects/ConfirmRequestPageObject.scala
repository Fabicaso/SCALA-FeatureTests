package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait ConfirmRequestPageObject extends WebBrowser {

  def RequestConfirmLoaded = id("page-loaded-request-confirm")

  def SendRequestButton = id("request-sidebar-form-button-submit")

  def SentRequestConfirmation = xpath("//p[contains(text(),'Request sent')] ")

  def ClientField          = id("request-sidebar-form-client")
  def DeliveryMediumField  = id("request-sidebar-form-deliveryMedium-dropdown")
  def OnlineDeliveryMedium = className("dropdown-ONLINE")
  def JobField             = id("request-sidebar-form-job-dropdown")
  def PullAndDeliverJob    = id("request-sidebar-form-job-dropdown-menu-PullAndDeliver")
  def DeliveryMethod       = id("request-sidebar-form-deliveryMethod")

}
