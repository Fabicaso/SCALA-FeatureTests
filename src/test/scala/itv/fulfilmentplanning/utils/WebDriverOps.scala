package itv.fulfilmentplanning.utils

import java.util.concurrent.atomic.AtomicReference

import com.typesafe.scalalogging.StrictLogging
import cucumber.api.Scenario
import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.slf4j.MarkerFactory

import scala.util.Try

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
    logger.info(scenarioMarker, s"Init scenario $scenario")

  }

  After { scenario =>
    logger.info(scenarioMarker, s"Shutting down $scenario ...")
    WebDriverOps.getDriver(scenario).foreach(_.quit)
  }
}

object WebDriverOps extends StrictLogging {
  private var webDriverPerScenario: AtomicReference[Map[Scenario, WebDriver]] = new AtomicReference(
    Map.empty[Scenario, WebDriver])

  def getDriver(scenario: Scenario): Option[WebDriver] = {
    webDriverPerScenario.get().get(scenario)
  }

  def createDriverIfNeeded(scenario: Scenario): Unit = {
    val scenarioMarker = ScenarioHelper.scenarioMarker(scenario.getId)
    while (true) {
      val oldMap = webDriverPerScenario.get
      if (oldMap.contains(scenario)) return
      else {
        logger.info(scenarioMarker, s"Creating web driver")
        val driver = new ChromeDriver()
        val newMap = oldMap + (scenario -> driver)
        Try(
          if (webDriverPerScenario.compareAndSet(oldMap, newMap))
            return
        ).recover {
          case exception: Exception =>
            logger.error(scenarioMarker, s"Error updating the map", exception)
            driver.quit()
            throw exception
        }

      }
    }
  }
}

object ScenarioHelper {
  def idFrom(currentScenario: Option[Scenario]) = currentScenario.fold("No id")(_.getId)

  def scenarioMarker(scenarioId: String) = MarkerFactory.getMarker(scenarioId)
}
