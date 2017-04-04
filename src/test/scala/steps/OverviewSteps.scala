package steps

import itv.fulfilmentplanning.Credentials
import itv.fulfilmentplanning.pageobjects._
import org.openqa.selenium.JavascriptExecutor

import scala.concurrent.duration._

class OverviewSteps
    extends BaseSteps
    with SignInPageObject
    with MenuPageObject
    with GoogleAuthPageObject
    with OfflineAccess
    with OverviewPageObject {

  override implicit val patienceConfig = PatienceConfig(2.seconds, 100.milliseconds)

  Then("""^'(.*)' msg is displayed for the production id - '(.*)'$""") {
    (NoAssetFoundMessage: String, productionId: String) =>
      logger.info(scenarioMarker, s"Warning message to be displayed is: $NoAssetFoundMessage for $productionId")
      FindContainsText(productionId).whenIsDisplayed
      FindExactText(NoAssetFoundMessage).whenIsDisplayed
      logger.info(scenarioMarker, "Success!")
  }

}
