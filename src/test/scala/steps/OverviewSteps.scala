package steps
import itv.fulfilmentplanning.pageobjects._
import scala.concurrent.duration._

class OverviewSteps extends BaseSteps with OverviewPageObject {

  override implicit val patienceConfig = PatienceConfig(10.seconds, 200.milliseconds)

  Then("""^the 'start date' is displayed on Overview page$""") { () =>
    logger.info(scenarioMarker, s"Licence Start Date should be displayed on the Overview page")
    waitPageToBeLoaded()
    LicenceStartDate.whenIsDisplayed.text should ===("13/02/2015")
    logger.info(scenarioMarker, "Licence Start Date is correctly displayed!")

  }

  private def waitPageToBeLoaded() =
    PageLoadedOverview.whenIsEnabled

}
