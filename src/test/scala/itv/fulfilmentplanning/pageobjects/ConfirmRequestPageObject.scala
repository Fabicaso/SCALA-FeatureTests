package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait ConfirmRequestPageObject extends WebBrowser {

  def DeliveryMediumValue(value: String) =
    id(s"request-sidebar-form-deliveryMedium-dropdown-menu-${value.toUpperCase()}")
  def selectAssetsOnRequiredByPage(licenceId: String, productionId: String) = id(s"$licenceId-$productionId-id")
  def RequestConfirmLoaded                                                  = id("page-loaded-request-confirm")
  def NextButtonOnSendRequestPage                                           = id("request-sidebar-form-button-submit")
  def SendRequestButton                                                     = id("request-requiredby-sidebar-button-submit")
  def SentRequestConfirmation                                               = xpath("//p[contains(text(),'Request Sent')]")
  def ClientField                                                           = id("request-sidebar-form-client")
  def DeliveryMediumField                                                   = id("request-sidebar-form-deliveryMedium-dropdown")
  def JobField                                                              = id("request-sidebar-form-job-dropdown")
  def JobValue(value: String)                                               = id(s"request-sidebar-form-job-dropdown-menu-${value.replaceAll(" ", "")}")
  def FrameRate                                                             = id("request-sidebar-form-frameRate-dropdown")
  def FrameRateValue(value: String)                                         = id(s"request-sidebar-form-frameRate-dropdown-menu-$value")
  def DeliveryMethod                                                        = id("request-sidebar-form-deliveryMethod")
  def RequiredByDateField                                                   = id("request-requiredby-content-header-menu")
  def EditRequiredByDateDropdown                                            = id("request-requiredby-content-header-menu-requiredby")
  def SpecField                                                             = id("request-sidebar-form-spec")
  def SendRequestsPageLoaded                                                = id("page-loaded-request-confirm")
  def SpecOptionalField                                                     = id("request-sidebar-form-spec")
  def ResolutionOutput                                                      = xpath(s"//*[@id='request-sidebar-form']/form/div/div[4]/div[2]/label")

  def RequiredByDateQuery(requiredDate: String) = {
    requiredDate match {
      case "a required by date" =>
        Some(TodayInCalendar)
      case _ => None
    }
  }
  private def TodayInCalendar = className("react-datepicker__day--today")

}
