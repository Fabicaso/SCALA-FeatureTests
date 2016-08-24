package stepdefs

import org.openqa.selenium.WebDriver
import org.scalatest.Assertions
import org.scalatest.Matchers._
import org.scalatest.selenium.WebBrowser

trait WebBrowserUtils extends WebBrowser with Assertions { outer =>
  implicit def webDriver: WebDriver

  implicit class QueryTestingW(query: Query) {
    def findElementOrFail: Element = {
      query.findElement.getOrElse(fail(s"Could not find element by $query"))
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
