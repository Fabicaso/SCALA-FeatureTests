package itv.fulfilmentplanning.utils

import com.typesafe.scalalogging.StrictLogging
import cucumber.api.Scenario
import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.slf4j.MarkerFactory

trait WebDriverOps extends StrictLogging { self: ScalaDsl with EN =>
  import WebDriverOps._

  private def scenarioId = ScenarioHelper.idFrom(currentScenario)

  implicit def scenarioMarker = ScenarioHelper.scenarioMarker(scenarioId)

  private var currentScenario: Option[Scenario] = None

  implicit def webDriver: WebDriver =
    currentScenario
      .flatMap(WebDriverOps.getDriver)
      .getOrElse(throw new RuntimeException(s"Unable to get a driver for $currentScenario"))

  Before { (scenario: Scenario) =>
    currentScenario = Some(scenario)
    createDriverIfNeeded(scenario)
    getDriver(scenario).foreach(_.manage().window().maximize())
    logger.info(scenarioMarker, s"Init scenario $scenario")

  }

  After { scenario =>
    logger.info(scenarioMarker, s"Shutting down $scenario ...")
    WebDriverOps.getDriver(scenario).foreach(_.quit)
  }
}

object WebDriverOps extends StrictLogging {
  import scala.collection.concurrent._
  private val webDriverPerScenario = TrieMap[Scenario, WebDriver]()

  def getDriver(scenario: Scenario): Option[WebDriver] = {
    webDriverPerScenario.get(scenario)
  }

  def createDriverIfNeeded(scenario: Scenario): Unit = {
    val scenarioMarker = ScenarioHelper.scenarioMarker(scenario.getId)
    if (!webDriverPerScenario.contains(scenario)) {
      logger.info(scenarioMarker, s"Creating web driver")
      webDriverPerScenario.put(scenario, new ChromeDriver())
    }
  }
}

object ScenarioHelper {
  def idFrom(currentScenario: Option[Scenario]) = currentScenario.fold("No id")(_.getId)

  def scenarioMarker(scenarioId: String) = MarkerFactory.getMarker(scenarioId)
}
