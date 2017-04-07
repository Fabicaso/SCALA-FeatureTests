package steps

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._
import org.scalactic.StringNormalizations._

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
      (AssetStatusOnProductionRow(licenceId).whenIsDisplayed.text should ===(fromAssetStatus))(after being lowerCased)
      NavigationActionMenu.clickWhenIsDisplayed
      EditStatus.clickWhenIsDisplayed
      AssetStatus(toAssetStatus).clickWhenIsDisplayed
      TodaysDate.clickWhenIsDisplayed
  }

  And(
    """^the Asset Status is '(.*)' for Production ID 1/5634/0030/31#001 and licence number '(.*)' on the Overwiev page""") {
    (fromAssetStatus: String, licenceId: String) =>
      (AssetStatusOnProductionRow(licenceId).whenIsDisplayed.text should ===(fromAssetStatus))(after being lowerCased)
  }

  Then("""^today's date is displayed for the 'Fulfilled' date$""") { () =>
    logger.info(scenarioMarker, "Today's date is displayed on the Fulfilled section on side menu bar")
    val expectedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now())
    eventually {
      FulfilledSideBarDate.whenIsDisplayed.text should ===(expectedDate)
    }
  }

  Then(
    """^the label status on the Overview page has changed to '(.*)' for ProdId '(.*)' and '(.*)' and licence number '(.*)'""") {
    (toAssetStatus: String, productionId: String, series: String, licenceId: String) =>
      logger.info(scenarioMarker, s"the status on the Overview page has changed to Fulfilled")
      reloadPage()
      SeriesRow(series).clickWhenIsDisplayed
      ProductionRow(productionId).clickWhenIsDisplayed
      eventually {
        (AssetStatusOnProductionRow(licenceId).whenIsDisplayed.text should ===(toAssetStatus))(after being lowerCased)
      }
  }

  private def waitPageToBeLoaded() =
    PageLoadedOverview.whenIsEnabled(PatienceConfig(10.seconds, 200.milliseconds), scenarioMarker)

}
