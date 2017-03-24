package steps

import com.typesafe.scalalogging.StrictLogging
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.utils.{WebBrowserUtils, WebDriverOps}
import org.scalatest.concurrent.Eventually
import org.scalatest.{Assertions, Inspectors, Matchers}
import org.scalatest.selenium.WebBrowser
import org.slf4j.MarkerFactory

trait BaseSteps  extends ScalaDsl
  with EN with WebBrowserUtils
  with WebBrowser
  with WebDriverOps
  with Matchers
  with Inspectors
  with Eventually
  with Assertions
  with StrictLogging {

  def scenarioId = currentScenario.fold("No id")(_.getId)

  implicit def scenarioMarker = MarkerFactory.getMarker(scenarioId)

}
