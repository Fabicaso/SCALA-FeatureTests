package steps

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.scalactic.StringNormalizations._
import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._
import org.openqa.selenium.{JavascriptExecutor, Keys, WebElement}
import org.openqa.selenium.interactions.Actions
import org.scalatest.time.{Millis, Second, Seconds, Span}

class OverviewSteps extends BaseSteps with OverviewPageObject {

  override implicit val patienceConfig = PatienceConfig(4.seconds, 200.milliseconds)

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

  Then("""^I can change the status from '(.*)' to '(.*)' for ProdId '(.*)' and '(.*)' and licence number '(.*)'$""") {
    (fromAssetStatus: String, toAssetStatus: String, productionId: String, series: String, licenceId: String) =>
      logger.info(scenarioMarker, s"Change the Asset Status")
      waitPageToBeLoaded()
      SeriesRow(series).clickWhenIsDisplayed
      ProductionRow(productionId).clickWhenIsDisplayed(PatienceConfig(10.seconds, 100.milliseconds), scenarioMarker)
      (AssetStatusOnProductionRow(licenceId, productionId).whenIsDisplayed.text should ===(fromAssetStatus))(
        after being lowerCased)
      NavigationActionMenu.clickWhenIsDisplayed
      EditStatus.clickWhenIsDisplayed
      AssetStatus(toAssetStatus.toLowerCase).clickWhenIsDisplayed
      TodaysDate.clickWhenIsDisplayed
  }

  And("""^the Asset Status is '(.*)' for Production ID (.*) and licence number '(.*)' on the Overwiev page""") {
    (fromAssetStatus: String, productionId: String, licenceId: String) =>
      (AssetStatusOnProductionRow(licenceId, productionId).whenIsDisplayed.text should ===(fromAssetStatus))(
        after being lowerCased)
  }

  Then(
    """^the label status on the Overview page has changed to '(.*)' for ProdId '(.*)' and '(.*)' and licence number '(.*)'""") {
    (toAssetStatus: String, productionId: String, series: String, licenceId: String) =>
      logger.info(scenarioMarker, s"the status on the Overview page has changed to Fulfilled")
      reloadPage()
      waitPageToBeLoaded()
      SeriesRow(series).clickWhenIsDisplayed
      ProductionRow(productionId).clickWhenIsDisplayed(PatienceConfig(10.seconds, 100.milliseconds), scenarioMarker)
      eventually {
        (AssetStatusOnProductionRow(licenceId, productionId).whenIsDisplayed.text should ===(toAssetStatus))(
          after being lowerCased)
      }
  }

  Then("""^'(.*)' date on the left Selection Details menu for Production ID '(.*)' of '(.*)' is '(.*)'""") {
    (statusDatesOnSideBarMenu: String, productionId: String, series: String, date: String) =>
      val expectedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now())
      reloadPage()
      waitPageToBeLoaded()
      SeriesRow(series).clickWhenIsDisplayed
      ProductionRow(productionId).clickWhenIsDisplayed(PatienceConfig(10.seconds, 100.milliseconds), scenarioMarker)

      if (date == "today's date") {
        var date = expectedDate
        eventually(timeout(Span(10, Seconds)), interval(Span(1, Second))) {
          statusDatesCheckOnSideBarMenu(statusDatesOnSideBarMenu, date)
        }
        logger.info(scenarioMarker,
                    s"'The left Selection Details menu date should be TODAY's DATE and it's displaying : $date ")
      } else {
        logger.info(scenarioMarker,
                    s"'The left Selection Details menu date should be '-' and it's displaying : $date ")
        eventually(timeout(Span(10, Seconds)), interval(Span(1, Second))) {
          statusDatesCheckOnSideBarMenu(statusDatesOnSideBarMenu, date)
        }
      }
  }

  Then("""^I can edit and set the '(.*)' date to the past for '(.*)' and production ID '(.*)'""") {
    (productionStatus: String, series: String, productionId: String) =>
      logger.info(scenarioMarker, s"Edit Dates for $productionStatus status of $series")
      val expectedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now().minusDays(1L))
      reloadPage()
      waitPageToBeLoaded()
      SeriesRow(series).clickWhenIsDisplayed
      eventually(timeout(Span(10, Seconds)), interval(Span(1, Second))) {
        click on ProductionRow(productionId).elementOrFail
        click on ActionsMenu.elementOrFail
        click on EditDates.elementOrFail
        click on EditDatesStatus((productionStatus: String).toLowerCase).elementOrFail
        click on YesterdaysDate.elementOrFail
      }
      eventually {
        statusDatesCheckOnSideBarMenu(productionStatus, expectedDate)
      }
  }

  Then("""^I can set the status to '(.*)' for multiple assets '(.*)' of '(.*)' and licence number '(.*)'""") {
    (toAssetStatus: String, productionIds: String, series: String, licenceId: String) =>
      {
        logger.info(scenarioMarker, s"the status to $toAssetStatus for multiple assets")
        val (production1, production2) = firstAndLastProduction(productionIds)

        val firstProduction = openSeries(series, production1)

        eventually(timeout(Span(10, Seconds)), interval(Span(1, Second))) {
          dragAndSelect(
            firstProduction.underlying,
            ExactText(production2).elementOrFail.underlying
          )
          click on ActionsMenu.elementOrFail
          click on EditStatus.elementOrFail
          click on AssetStatus(toAssetStatus.toLowerCase).elementOrFail
          click on TodaysDate.elementOrFail
        }

//        Thread.sleep(100)
//        reloadPage()
//        openSeries(series, production1)

        eventually(timeout(Span(10, Seconds)), interval(Span(1, Second))) {
//          ProductionStatus(licenceId, production1).elementOrFail.text should ===(toAssetStatus) FIXME Sometimes UI is generating this id: 1/9946/0001#001-123665-1276130-labels-state-node-status
          ProductionStatus(licenceId, production2).elementOrFail.text should ===(toAssetStatus)
        }
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
    webDriver match {
      case jse: JavascriptExecutor => jse.executeScript("window.scrollBy(0,500)", "")
      case _                       => logger.info(s"Unable to scrolldown")
    }
    SeriesRow(series).clickWhenIsDisplayed

    val firstProduction = ExactText(production1)
      .whenIsDisplayed(PatienceConfig(10.seconds, 100.milliseconds), scenarioMarker)
    firstProduction
  }

  protected def dragAndSelect(firstProductionId: WebElement, lastProductionId: WebElement): Unit = {
    val builder = new Actions(webDriver)

    builder
      .clickAndHold(firstProductionId)
      .moveToElement(lastProductionId)
      .release(lastProductionId)
      .keyDown(Keys.SHIFT)
      .perform()
    builder.keyUp(Keys.SHIFT).perform()

  }

  private def statusDatesCheckOnSideBarMenu(statusDatesOnSideBarMenu: String, date: String) = {
    statusDatesOnSideBarMenu match {
      case "Fulfilled"   => FulfilledSideBarDate.whenIsDisplayed.text should ===(s"$date")
      case "Requested"   => RequestedSideBarDate.whenIsDisplayed.text should ===(s"$date")
      case "Required By" => RequiredBySideBarDate.whenIsDisplayed.text should ===(s"$date")
      case _             => fail(s"Unsupported dates On Side Bar Menu: $statusDatesOnSideBarMenu")
    }
  }

  private def waitPageToBeLoaded() =
    PageLoadedOverview.whenIsEnabled(PatienceConfig(10.seconds, 200.milliseconds), scenarioMarker)

}
