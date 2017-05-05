package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait OverviewPageObject extends WebBrowser {

  def PageLoadedOverview = id("page-loaded-overview")

  def CreateNewRequestButton = id("create-new-request-button")

  def LicenceStartDate = id("overview-header-startDate")

  def LicenceStatusNotices = id("overview-header-state-material")

  def LicenceStatus = id("overview-header-state-licence")

  def ProductionRow(productionId: String) = id(s"itemTitle-$productionId")

  def SeriesRow(series: String) = xpath(s"//span[contains(@id,'$series')]")

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

  def EditDatesStatus(productionStatus: String) = id(s"menu-dropdown-editDate-$productionStatus")

  def SourceAsset(licenceId: String, productionId: String) = id(s"$licenceId-$productionId-labels-external")

  def FulfilledCountStats = id("overview-header-stats-label-fulfilled-count")

  def RequestedCountStats = id("overview-header-stats-label-requested-count")

  def AvailableCountStats = id("overview-header-stats-label-available-count")

  def AssetStatusOnProductionRow(licenceId: String, productionId: String) =
    xpath(s"//span[contains(@id, '$licenceId-$productionId') and contains(@id, '-labels-state-node-status')]")

  def ProductionStatus(licenceId: String, productionId: String) =
    id(s"$licenceId-$productionId-labels-state-node-status")

}
