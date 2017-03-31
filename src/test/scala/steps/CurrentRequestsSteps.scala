package steps

import itv.fulfilmentplanning.pageobjects.{CurrentRequestsPageObject, MenuPageObject}

import scala.concurrent.duration._
import org.scalatest.Matchers._

class CurrentRequestsSteps extends BaseSteps with CurrentRequestsPageObject with MenuPageObject {

  override implicit val patienceConfig = PatienceConfig(2.seconds, 100.milliseconds)

  And("""^I am on the 'New Request' page using the following licence number (\d+)$""") { (licenceId: Int) =>
    logger.info(scenarioMarker, s"Go to licence number: $licenceId")
    click on EnterLicenceSection.whenIsDisplayed
    eventually(numberField(LicenceInput)).value = licenceId.toString
    submit()
  }

  Then("""^the 'Current Requests' page is displayed$""") { () =>
    logger.info(scenarioMarker, "Go to current request")
    waitUntilPageIsLoaded()
    logger.info(scenarioMarker, "Success!")
  }

  Then(
    """^the asset requested with production id '(.*)' and licence number (\d+) is displayed on the 'Current Requests' page under 'No Required By Date' section$""") {
    (productionId: String, licenceId: Int) =>
      logger.info(scenarioMarker,
                  s"Verify asset with production id $productionId has been requested for licence number: $licenceId")
      click on CurrentRequestSection.whenIsDisplayed
      waitUntilPageIsLoaded()

      click on RequestedAssetsForDate(None)

      val result = AssetWithProductionId(productionId).whenIsDisplayed

      val domIdForProduction = result.attribute("id").getOrElse(fail)

      val list            = domIdForProduction.split("-").init
      val assetId         = list.last.toInt
      val actualLicenceId = list.tail.last.toInt
      actualLicenceId shouldBe (actualLicenceId)

      logger.info(scenarioMarker, "Success!")
  }

  private def waitUntilPageIsLoaded() = {
    CurrentRequestsPageLoaded
      .whenIsEnabled(patienceConfig = PatienceConfig(10.seconds, 1.second), scenarioMarker)
      .isEnabled shouldBe true
  }

}
