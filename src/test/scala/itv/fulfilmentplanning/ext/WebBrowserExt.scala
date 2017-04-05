package itv.fulfilmentplanning.ext

import com.typesafe.scalalogging.StrictLogging
import org.openqa.selenium.WebDriver
import org.scalatest.Assertions
import org.scalatest.Matchers._
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.WebBrowser
import org.slf4j.Marker

trait WebBrowserExt extends WebBrowser with Assertions with Eventually with StrictLogging { outer =>
  implicit def webDriver: WebDriver

  implicit class QueryTestingW(query: Query) {
    def elementOrFail: Element =
      query.findElement.getOrElse(fail(s"Could not find element by $query"))

    def elements: Iterator[Element] =
      query.findAllElements

    def clickWhenIsDisplayed(implicit patienceConfig: PatienceConfig, scenarioMarker: Marker): Unit = {
      eventually(timeout(patienceConfig.timeout), interval(patienceConfig.interval)) {
        logger.info(s"Trying to click on $query")
        val element = query.elementOrFail
        element.isDisplayed shouldBe true
        click on element
      }
    }

    def clickWhenIsEnabled(implicit patienceConfig: PatienceConfig, scenarioMarker: Marker): Unit = {
      eventually(timeout(patienceConfig.timeout), interval(patienceConfig.interval)) {
        logger.info(s"Trying to click on $query")
        val element = query.elementOrFail
        element.isEnabled shouldBe true
        click on element
      }
    }

    def whenIsDisplayed(implicit patienceConfig: PatienceConfig, scenarioMarker: Marker): Element = {
      eventually(timeout(patienceConfig.timeout), interval(patienceConfig.interval)) {
        logger.info(scenarioMarker, s"Waiting for $query to be displayed")

        val element = query.elementOrFail
        element.isDisplayed shouldBe true
        element
      }
    }

    def whenIsEnabled(implicit patienceConfig: PatienceConfig, scenarioMarker: Marker): Element =
      eventually(timeout(patienceConfig.timeout), interval(patienceConfig.interval)) {
        logger.info(scenarioMarker, s"Waiting for $query to be enabled")
        val element = query.elementOrFail
        element.isEnabled shouldBe true
        element
      }

  }
  implicit class WebElementW(element: Element) {
    def clickIfEnabled: Unit = outer.clickIfEnabled(element)
  }

  def clickIfEnabled(element: Element): Unit = {
    element.isEnabled shouldBe true
    click on element
  }
}
