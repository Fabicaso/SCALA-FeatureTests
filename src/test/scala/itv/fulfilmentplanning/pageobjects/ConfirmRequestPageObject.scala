package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait ConfirmRequestPageObject extends WebBrowser {
  def selectAssetsOnRequiredByPage(licenceId: String, productionId: String) = id(s"$licenceId-$productionId-id")
  def RequestConfirmLoaded                                                  = id("page-loaded-request-confirm")
  def NextButtonOnSendRequestPage                                           = id("request-sidebar-form-button-submit")
  def SendRequestButton                                                     = id("request-requiredby-sidebar-button-submit")
  def SentRequestConfirmation                                               = xpath("//*[@id='request-requiredby-sidebar']/p")
  def ClientField                                                           = id("request-sidebar-form-client")
  def DeliveryMediumField                                                   = id("request-sidebar-form-deliveryMedium-dropdown")
  def OnlineDeliveryMedium                                                  = className("dropdown-ONLINE")
  def HardDriveDeliveryMedium                                               = id("request-sidebar-form-deliveryMedium-dropdown-menu-HARDDRIVE")
  def JobField                                                              = id("request-sidebar-form-job-dropdown")
  def PullAndDeliverJob                                                     = id("request-sidebar-form-job-dropdown-menu-PullAndDeliver")
  def TapeDeliveryMedium                                                    = id("request-sidebar-form-deliveryMedium-dropdown-menu-TAPE")
  def TranscodeJob                                                          = id("request-sidebar-form-job-dropdown-menu-Transcode")
  def TapeAsSourceJob                                                       = id("request-sidebar-form-job-dropdown-menu-TapeAsSource")
  def DeliveryMethod                                                        = id("request-sidebar-form-deliveryMethod")
  def RequiredByDateField                                                   = id("request-requiredby-content-header-menu")
  def EditRequiredByDateDropdown                                            = id("request-requiredby-content-header-menu-requiredby")

  def RequiredByDateQuery(requiredDate: String) = {
    requiredDate match {
      case "a required by date" =>
        Some(TodayInCalendar)
      case _ => None
    }
  }
  private def TodayInCalendar = className("react-datepicker__day--today")

}
