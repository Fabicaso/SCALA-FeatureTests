package steps

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import itv.fulfilmentplanning.ExpectedAsset
import itv.fulfilmentplanning.ExpectedData.assetFor
import itv.fulfilmentplanning.ext._
import itv.fulfilmentplanning.pageobjects._
import org.scalactic.StringNormalizations._
import org.scalatest.time.{Second, Seconds, Span}

import scala.concurrent.duration._

class OverviewSteps
    extends BaseSteps
    with OverviewPageObject
    with NewRequestPageObject
    with ConfirmRequestPageObject
    with MenuPageObject {

  override implicit val patienceConfig = PatienceConfig(10.seconds, 200.milliseconds)

  Then("""^the 'start date' is displayed on Overview page$""") { () =>
    logger.info(scenarioMarker, s"Licence Start Date should be displayed on the Overview page")
    waitPageToBeLoaded()

    LicenceStartDate.whenIsDisplayed.text should ===("13/02/2015")
    logger.info(scenarioMarker, "Licence Start Date is correctly displayed!")

  }

  Then("""^the '(.*)' is displayed correctly on the Overview Page$""") { (statusNotices: String) =>
    waitPageToBeLoaded()

    (LicenceStatusNotices.whenIsDisplayed.text should ===(statusNotices))(after being lowerCased)
    logger.info(scenarioMarker, "Licence Status Notices is correctly displayed!")
  }

  Then("""^the '(.*)' is displayed on the Overview Page$""") { (licenceStatus: String) =>
    waitPageToBeLoaded()

    (LicenceStatus.whenIsDisplayed.text should ===(licenceStatus))(after being lowerCased)
    logger.info(scenarioMarker, "Licence Status is correctly displayed!")
  }

  Then("""^The 'Create New Request' is disabled$""") { () =>
    waitPageToBeLoaded()

    CreateNewRequestButton.whenIsDisplayed.isEnabled should ===(false)
  }

  Then(
    """^I (?:can|am able to) change the status from '(.*)' to '(.*)' for ProdId '(.*)' and '(.*)' and licence number '(.*)'$""") {
    (fromAssetStatus: String, toAssetStatus: String, productionId: String, series: String, licenceId: String) =>
      logger.info(scenarioMarker, s"Change the Asset Status")
      waitPageToBeLoaded()

      selectSeriesRow(series)

      selectProductionRow(productionId)

      (AssetStatusOnProductionRow(licenceId, productionId).whenIsDisplayed.text should ===(fromAssetStatus))(
        after being lowerCased)

      val (action, date) = selectAction(toAssetStatus)

      setAssetState(productionId, licenceId, action, date)
  }

  And(
    """^the Asset Status is '(.*)' for Series '(.*)' and Production ID '(.*)' and licence number '(.*)' on the Overview page$""") {
    (fromAssetStatus: String, series: String, productionId: String, licenceId: String) =>
      logger.info(scenarioMarker, s"the Asset Status for $productionId is set to $fromAssetStatus")
      waitPageToBeLoaded()

      selectSeriesRow(series)

      selectProductionRow(productionId)

      (AssetStatusOnProductionRow(licenceId, productionId).whenIsDisplayed.text should ===(fromAssetStatus))(
        after being lowerCased)
  }

  Then("""^the pie chart is correctly updated for external fulfilment$""") { () =>
    {
      logger.info(scenarioMarker, s"the pie chart is updated for external fulfilment")
      eventually {
        FulfilledCountStats.whenIsDisplayed.text should ===("1")
        RequestedCountStats.whenIsDisplayed.text should ===("0")
        AvailableCountStats.whenIsDisplayed.text should ===("6")
      }

    }
  }

  Then(
    """^the label status on the Overview page has changed to '(.*)' for ProdId '(.*)' and '(.*)' and licence number '(.*)'""") {
    (toAssetStatus: String, productionId: String, series: String, licenceId: String) =>
      logger.info(scenarioMarker, s"the status on the Overview page has changed to Fulfilled")

      waitPageToBeLoaded()

      selectSeriesRow(series)

      selectProductionRow(productionId)

      eventually {
        (AssetStatusOnProductionRow(productionId, licenceId).whenIsDisplayed.text should ===(toAssetStatus))(
          after being lowerCased)
      }
  }

  Then("""^'(.*)' date on the right Selection Details menu for Production ID '(.*)' of '(.*)' is '(.*)'""") {
    (statusDatesOnSideBarMenu: String, productionId: String, series: String, date: String) =>
      val expectedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now())
      waitPageToBeLoaded()
      reloadPage()
      waitPageToBeLoaded()

      selectSeriesRow(series)

      selectProductionRow(productionId)

      if (date == "today's date") {
        var date = expectedDate
        eventually(timeout(Span(10, Seconds)), interval(Span(1, Second))) {
          statusDatesCheckOnSideBarMenu(statusDatesOnSideBarMenu, date)
        }
        logger.info(scenarioMarker,
                    s"'The right Selection Details menu date should be TODAY's DATE and it's displaying : $date ")
      } else {
        logger.info(scenarioMarker,
                    s"'The right Selection Details menu date should be '-' and it's displaying : $date ")
        eventually(timeout(Span(10, Seconds)), interval(Span(1, Second))) {
          statusDatesCheckOnSideBarMenu(statusDatesOnSideBarMenu, date)
        }
      }
  }

  Then(
    """^I can edit and set the '(.*)' date to the past for '(.*)' and production ID '(.*)' and licence number '(.*)'""") {
    (productionStatus: String, series: String, productionId: String, licenceId: String) =>
      logger.info(scenarioMarker, s"Edit Dates for $productionStatus status of $series")
      val expectedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now().minusDays(1L))

      waitPageToBeLoaded()

      SeriesRow(series).clickWhenIsDisplayed

      editAssetDate(productionId, productionStatus)

      eventually {
        statusDatesCheckOnSideBarMenu(productionStatus, expectedDate)
      }
  }

  private def editAssetDate(productionId: String, productionStatus: String) = {
    eventually(timeout(Span(10, Seconds)), interval(Span(1, Second))) {
      selectProductionRow(productionId)
      click on NavigationActionMenu.elementOrFail
      click on EditDates.elementOrFail
      click on EditDatesStatus(productionStatus.toLowerCase).elementOrFail
      click on YesterdaysDate.elementOrFail
    }
  }

  Then("""^the Asset source is set to '(.*)' for ProdId '(.*)' series '(.*)' and licence number '(.*)'$""") {
    (sourceAsset: String, productionId: String, series: String, licenceId: String) =>
      {
        logger.info(scenarioMarker, s"the status Asset source is set to External for ProdId: $productionId")

        selectSeriesRow(series)

        selectProductionRow(productionId)

        (SourceAsset(licenceId, productionId).whenIsDisplayed.text should ===(sourceAsset))(after being lowerCased)
      }

  }

  Then(
    """^the previously fulfilled history details for Production id '(.*)' of '(.*)' and licence '(.*)' are displayed$""") {
    (productionId: String, series: String, licenceId: String) =>
      logger.info(scenarioMarker, s"previously fulfilled history details are correctly dislayed")
      val licence: ExpectedAsset = assetFor(productionId)
      val expectedDate           = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now())

      waitPageToBeLoaded()

      selectSeriesRow(series)

      selectProductionRow(productionId)

      eventually {
        PrevFulfilled_AssetUpdated.whenIsDisplayed.text should ===(expectedDate)
        PrevFulfilled_LicenceNo(licenceId).whenIsDisplayed.text should ===(licence.licenceId)
        PrevFulfilled_FulfilledDate(licenceId).whenIsDisplayed.text should ===(expectedDate)
        PrevFulfilled_SourceUsed(licenceId).whenIsDisplayed.text should ===(licence.format)
      }
  }

  Then(
    """^'(.*)' is flagged as Previous Fulfilled and the ProductionId '(.*)' is flagged with a dot as Previous Fulfilled and licence '(.*)'$""") {
    (series: String, productionId: String, licence: String) =>
      val prodId: ExpectedAsset = assetFor(productionId)
      logger.info(scenarioMarker, s"$series is flagged as Previous Fulfilled")
      eventually {
        PrevFulfilledSeriesFlag(series).element.isDisplayed
      }
      logger.info(scenarioMarker, s"the $productionId is flagged with a dot as Previous Fulfilled")

      eventually {
        selectSeriesRow(series)
        PreviouslyFulfilledProductionDot(licence, productionId).element.isDisplayed
      }
  }

  Then("""^I can set the status to '(.*)' for multiple assets '(.*)' of '(.*)' and licence number '(.*)'""") {
    (toAssetStatus: String, productionIds: String, series: String, licenceId: String) =>
      {
        logger.info(scenarioMarker, s"the status to $toAssetStatus for multiple assets")

        waitPageToBeLoaded()

        val (production1, production2) = firstAndLastProduction(productionIds)

        val firstProduction = openSeries(series, production1)

        eventually(timeout(Span(10, Seconds)), interval(Span(1, Second))) {
          dragAndSelect(
            firstProduction,
            ExactText(production2).elementOrFail
          )
          SideBarTitle.element.text should include("4 Items Selected")

          val (action, date) = selectAction(toAssetStatus)

          setAssetState(production1, licenceId, action, date)
        }

        eventually(timeout(Span(10, Seconds)), interval(Span(1, Second))) {
          AssetStatusOnProductionRow(production1, licenceId).elementOrFail.text should ===(toAssetStatus)
          AssetStatusOnProductionRow(production2, licenceId).elementOrFail.text should ===(toAssetStatus)
        }
      }
  }

  And("""^I set to External Fulfilled the status of ProdId '(.*)' and '(.*)' and licence number '(.*)'$""") {
    (productionId: String, series: String, licenceId: String) =>
      {
        logger.info(scenarioMarker, s"Change the Asset Status to External Fulfilled")

        waitPageToBeLoaded()

        selectSeriesRow(series)

        selectProductionRow(productionId)

        (AssetStatusOnProductionRow(licenceId, productionId).whenIsDisplayed.text should ===("Outstanding"))(
          after being lowerCased)

        setAssetState(productionId, licenceId, "requested", Some(TodaysDate))

        setAssetState(productionId, licenceId, "inProgress")

        setAssetState(productionId, licenceId, "delivered")

        setAssetState(productionId, licenceId, "fulfilled", Some(TodaysDate))
      }
  }

  private def selectProductionRow(productionId: String) =
    if (SidebarHeader.element.text == ("No production selected")) {
      ProductionRow(productionId).clickWhenIsDisplayed(PatienceConfig(10.seconds, 100.milliseconds), scenarioMarker)
      if (SideBarProductionId.whenIsDisplayed.text != (productionId)) {
        ProductionRow(productionId).clickWhenIsDisplayed
      }
    }

  private def setAssetState(productionId: String, licenceId: String, action: String, date: Option[Query] = None) =
    eventually {
      NavigationActionMenu.clickWhenIsDisplayed //overview-navbar-actions
      EditStatus.clickWhenIsDisplayed
      AssetStatus(action).clickWhenIsDisplayed
      date.foreach(_.clickWhenIsDisplayed)
      eventually {
        val state = action match {
          case "cancelled" => "Outstanding"
          case _           => action
        }
        (AssetStatusOnProductionRow(productionId, licenceId).whenIsDisplayed.text
          should ===(state))(after being lowerCased.and(withoutWhiteSpaces))
      }
    }

  private def firstAndLastProduction(productionIds: String) = {
    val (production1, production2) = productionIds.split(",") match {
      case Array(productionId1, productionId2) => {
        (productionId1, productionId2)
      }
    }
    (production1, production2)
  }

  private def openSeries(series: String, production1: String) = {
    waitPageToBeLoaded()
    scrollDown()

    selectSeriesRow(series)

    val firstProduction = ExactText(production1)
      .whenIsDisplayed(PatienceConfig(10.seconds, 100.milliseconds), scenarioMarker)
    firstProduction
  }

  private def waitPageToBeLoaded() =
    PageLoadedOverview.whenIsEnabled(PatienceConfig(15.seconds, 500.milliseconds), scenarioMarker)

  private def selectSeriesRow(series: String) =
    if (!CollapseAll.element.isEnabled) {
      SeriesRow(series).clickWhenIsDisplayed
    }

  private def statusDatesCheckOnSideBarMenu(statusDatesOnSideBarMenu: String, date: String) = {
    statusDatesOnSideBarMenu match {
      case "Fulfilled"   => FulfilledSideBarDate.whenIsDisplayed.text should ===(s"$date")
      case "Requested"   => RequestedSideBarDate.whenIsDisplayed.text should ===(s"$date")
      case "Required By" => RequiredBySideBarDate.whenIsDisplayed.text should ===(s"$date")
      case _             => fail(s"Unsupported dates On Side Bar Menu: $statusDatesOnSideBarMenu")
    }
  }

  private def selectAction(toAssetStatus: String) =
    toAssetStatus match {
      case "Not Required" => ("notRequired", None)
      case "Cancelled"    => ("cancelled", None)
      case "In Progress"  => ("inProgress", None)
      case "Delivered"    => ("delivered", None)
      case _              => (toAssetStatus.toLowerCase(), Some(TodaysDate))
    }

}
