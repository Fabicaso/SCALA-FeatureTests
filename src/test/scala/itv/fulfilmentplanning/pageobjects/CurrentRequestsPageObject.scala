package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait CurrentRequestsPageObject extends WebBrowser {
  private val noDate = "noDate"

  def RequestList: ClassNameQuery = className("page-requestList")

  def CurrentRequestsPageLoaded: IdQuery = id("page-loaded-request-list")

  def RequestedAssetsForDate(date: Option[String]) =
    id(s"request-list-section-header-${date.getOrElse(noDate)}-date")

  def RequestedAssetRowBy(productionId: String) = xpath(s"//span[contains(text(),'$productionId')] ")

  def ProgrammeTitleAssetRow(productionId: String,
                             licenceId: String,
                             assetId: String,
                             client: String,
                             date: Option[String]) =
    idInRequestListSectionBy(licenceId, assetId, client, date, "programmeTitle")

  def SourceAssetRow(productionId: String, licenceId: String, assetId: String, client: String, date: Option[String]) =
    idInRequestListSectionBy(licenceId, assetId, client, date, "format")

  def DurationAssetRow(productionId: String,
                       licenceId: String,
                       assetId: String,
                       client: String,
                       date: Option[String]) =
    idInRequestListSectionBy(licenceId, assetId, client, date, "duration")

  def JobTypeAssetRow(productionId: String,
                      licenceId: String,
                      assetId: String,
                      client: String,
                      date: Option[String],
                      jobType: String) =
    idInRequestListSectionBy(licenceId, assetId, client, date, s"jobType-${jobType.toLowerCase}")

  private def idInRequestListSectionBy(licenceId: String,
                                       assetId: String,
                                       client: String,
                                       date: Option[String],
                                       propertyName: String) =
    id(s"request-list-section-content-${date.getOrElse(noDate)}-$client-entry-$licenceId-$assetId-$propertyName")

}