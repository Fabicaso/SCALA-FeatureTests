package itv.fulfilmentplanning.utils

import com.typesafe.scalalogging.StrictLogging
import org.openqa.selenium.WebDriver
import org.scalatest.Assertions
import org.scalatest.Matchers._
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.WebBrowser



trait WebBrowserUtils extends WebBrowser with Assertions with Eventually with StrictLogging { outer =>
  implicit def webDriver: WebDriver

  implicit class QueryTestingW(query: Query) {
    def elementOrFail: Element =
      query.findElement.getOrElse(fail(s"Could not find element by $query"))

    def whenDisplayed = {
      eventually{
        logger.info(s"Waiting for $query to be displayed")
        val element = query.elementOrFail
        element.isDisplayed shouldBe true
        element
      }
    }

    def whenEnabled =
      eventually{
        logger.info(s"Waiting for $query to be enabled")
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
