package steps

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

  private def waitPageToBeLoaded() =
    PageLoadedOverview.whenIsEnabled(PatienceConfig(10.seconds, 200.milliseconds), scenarioMarker)

}
