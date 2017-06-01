package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait OverviewPageObject extends WebBrowser {

  def SideBarTitle = id("sidebar-selection-count")

  def SideBarProductionId = id("sidebar-production-id")

  def CollapseAll = id("overview-navbar-collapse")

  def PageLoadedOverview = id("page-loaded-overview")

  def CreateNewRequestButton = id("create-new-request-button")

  def LicenceStartDate = id("overview-header-startDate")

  def LicenceStatusNotices = id("overview-header-state-material")

  def LicenceStatus = id("overview-header-state-licence")

  def ProductionRow(productionId: String) = id(s"itemTitle-$productionId")

  def SeriesRow(series: String) = id(s"overview-series-$series-title")

  def NavigationActionMenu = id("overview-navbar-actions")

  def EditStatus = id("menu-dropdown-editStatus")

  def AssetStatus(status: String) = id(s"menu-dropdown-editStatus-$status")

  def TodaysDate = className("react-datepicker__day--today")

  def YesterdaysDate = xpath("//div[contains(@class, 'react-datepicker__day--today')]/preceding-sibling::div[1]")

  def FulfilledSideBarDate = id("sidebar-dates-fulfilled-value")

  def RequiredBySideBarDate = id("sidebar-dates-requiredby-value")

  def RequestedSideBarDate = id("sidebar-dates-requested-value")

  def ActionsMenu = id("overview-navbar-actions-dropdown-button")

  def EditDates = id("menu-dropdown-editDate")

  def PrevFulfilledSeriesFlag(series: String) = id(s"series-$series-prev-fulfilled-flag")
  def PrevFulfilled_AssetUpdated                           = id("sidebar-metadata-entry-updated-value")
  def PrevFulfilled_LicenceNo(licenceId: String)           = id(s"sidebar-metadata-entry-$licenceId-licenceNo-value")
  def PrevFulfilled_FulfilledDate(licenceId: String)       = id(s"sidebar-metadata-entry-$licenceId-fulfilled-value")
  def PrevFulfilled_SourceUsed(licenceId: String)          = id(s"sidebar-metadata-entry-$licenceId-sourceUsed-value")
  def EditDatesStatus(productionStatus: String)            = id(s"menu-dropdown-editDate-$productionStatus")
  def SourceAsset(licenceId: String, productionId: String) = id(s"$licenceId-$productionId-labels-external")
  def FulfilledCountStats                                  = id("overview-header-stats-label-fulfilled-count")
  def RequestedCountStats                                  = id("overview-header-stats-label-requested-count")
  def AvailableCountStats                                  = id("overview-header-stats-label-available-count")

  def SidebarHeader = xpath(s"//p[contains(@id, 'sidebar')]")

  def PrevFulfilledProductionDot(licence: String, productionId: String) =
    id(s"$licence-$productionId-prev-fulfilled-flag")
  def AssetStatusOnProductionRow(licenceId: String, productionId: String) =
    xpath(
      s"//span[contains(@id, '$licenceId') and contains(@id, '$productionId') and contains(@id, '-labels-state-node-status')]")

}
