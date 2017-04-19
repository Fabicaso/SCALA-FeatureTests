package itv.fulfilmentplanning.ext

import com.typesafe.scalalogging.StrictLogging
import cucumber.api.Scenario
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.Config
import org.openqa.selenium.remote.Augmenter
import org.openqa.selenium.{Dimension, OutputType, TakesScreenshot, WebDriver}
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
    setDriveIfNeededFor(scenario)
    logger.info(scenarioMarker, s"Init scenario ${scenario.getName} ...")
  }

  After { scenario =>
    if (scenario.isFailed) {
      logger.info(s"${scenario.getName} scenario failed!")
      takeScreenshot(scenario)
    } else {
      logger.info(scenarioMarker, s"${scenario.getName} scenario scenario success!")
    }
    WebDriverOps.done(scenario)

  }

  private def takeScreenshot(scenario: Scenario) = {
    Try(new Augmenter().augment(webDriver) match {
      case t: TakesScreenshot =>
        scenario.embed(t.getScreenshotAs(OutputType.BYTES), "image/png")
      case _ => logger.warn(s"Unable to create the screen shoot for $scenario")
    }).recover {
      case e: Exception =>
        logger.error(s"Unable to create the screen shoot for $scenario because ${e.getMessage()}", e)
    }
  }
}

object WebDriverOps extends StrictLogging {

  import scala.collection.concurrent._

  private val webDriverPerScenario = TrieMap[Scenario, WebDriver]()

  def getDriver(scenario: Scenario): Option[WebDriver] =
    webDriverPerScenario.get(scenario)

  def done(scenario: Scenario) = getDriver(scenario).foreach(Config.webDriverPool.returnObject)

  def setDriveIfNeededFor(scenario: Scenario): Unit = {
    val scenarioMarker = ScenarioHelper.scenarioMarker(scenario.getId)
    if (!webDriverPerScenario.contains(scenario)) {
      logger.info(scenarioMarker, s"Set web driver for ${scenario.getId}")
      webDriverPerScenario.put(scenario, Config.webDriverPool.borrowObject())
      val webDriver = webDriverPerScenario(scenario)
      val dimension = new Dimension(1920, 1200)
      logger.info(s"Config web driver dimension $dimension")
      Try(webDriver.manage.window().setSize(dimension)).recover {
        case exception =>
          logger.error(s"Unable to set dimension because ${exception.getMessage}", exception)
      }
    }
  }
}

object ScenarioHelper {
  def idFrom(currentScenario: Option[Scenario]) = currentScenario.fold("No id")(_.getId)

  def scenarioMarker(scenarioId: String) = MarkerFactory.getMarker(scenarioId)
}
