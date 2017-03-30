package steps

import itv.fulfilmentplanning.pageobjects.{CurrentRequestsPageObject, MenuPageObject, OverviewPageObject}

import scala.concurrent.duration._

class OverviewSteps extends BaseSteps with CurrentRequestsPageObject with MenuPageObject with OverviewPageObject {

  override implicit val patienceConfig = PatienceConfig(2.seconds, 100.milliseconds)

  When("""^I complete the fulfilment request for '1: Desert Seas' of 'Desert Seas'$""") { () =>
    logger.info(scenarioMarker, s"Complete the fulfilment request")
    PageLoadedOverview.whenIsEnabled
    eventually(click on CreateNewRequestButton.whenIsDisplayed)
  }

}
