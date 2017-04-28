package steps

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.scalactic.StringNormalizations._
import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._
import org.openqa.selenium.{JavascriptExecutor, Keys, WebElement}
import org.openqa.selenium.interactions.Actions

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
      ProductionRow(productionId).clickWhenIsDisplayed
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
      ProductionRow(productionId).clickWhenIsDisplayed
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
      ProductionRow(productionId).clickWhenIsDisplayed

      if (date == "today's date") {
        var date = expectedDate
        eventually {
          statusDatesCheckOnSideBarMenu(statusDatesOnSideBarMenu, date)
        }
        logger.info(scenarioMarker,
                    s"'The left Selection Details menu date should be TODAY's DATE and it's displaying : $date ")
      } else {
        logger.info(scenarioMarker,
                    s"'The left Selection Details menu date should be '-' and it's displaying : $date ")
        eventually {
          statusDatesCheckOnSideBarMenu(statusDatesOnSideBarMenu, date)
        }
      }
  }

  Then("""^I can edit and set the '(.*)' date to the past for '(.*)' and production ID '(.*)'""") {
    (productionStatus: String, series: String, productionId: String) =>
      logger.info(scenarioMarker, s"Edit Dates for $productionStatus status of $series")
      val expectedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now().minusDays(1L))
      reloadPage
      waitPageToBeLoaded()
      SeriesRow(series).clickWhenIsDisplayed
      ProductionRow(productionId).clickWhenIsDisplayed
      ActionsMenu.clickWhenIsDisplayed
      EditDates.clickWhenIsDisplayed
      EditDatesStatus((productionStatus: String).toLowerCase).clickWhenIsDisplayed
      YesterdaysDate.clickWhenIsDisplayed
      eventually {
        statusDatesCheckOnSideBarMenu(productionStatus, expectedDate)
      }
  }

  Then("""^I can set the status to '(.*)' for multiple assets '(.*)' of '(.*)' and licence number '(.*)'""") {
    (toAssetStatus: String, productionIds: String, series: String, licenceId: String) =>
      {
        logger.info(scenarioMarker, s"the status to $toAssetStatus for multiple assets")
        waitPageToBeLoaded()
        SeriesRow(series).clickWhenIsDisplayed

        val productionIdsToSelect: Array[String] = productionIds.split(",")

        val (production1, production2) = productionIdsToSelect match {
          case Array(productionId1, productionId2) => {
            (productionId1, productionId2)
          }
        }

        webDriver match {
          case jse: JavascriptExecutor => jse.executeScript("window.scrollBy(0,500)", "")
          case _                       => logger.info(s"Unable to scrolldown")
        }

        dragAndSelect(
          ExactText(production1)
            .whenIsDisplayed(PatienceConfig(10.seconds, 100.milliseconds), scenarioMarker)
            .underlying,
          ExactText(production2).whenIsDisplayed.underlying
        )

        ActionsMenu.clickWhenIsDisplayed
        EditStatus.clickWhenIsDisplayed
        AssetStatus(toAssetStatus.toLowerCase).clickWhenIsDisplayed
        TodaysDate.clickWhenIsDisplayed

        eventually {
          ProductionStatus(licenceId, production1)
            .whenIsDisplayed(PatienceConfig(10.seconds, 100.milliseconds), scenarioMarker)
            .text should ===(toAssetStatus)
          eventually(ProductionStatus(licenceId, production2).elementOrFail).text should ===(toAssetStatus)
        }

      }
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
