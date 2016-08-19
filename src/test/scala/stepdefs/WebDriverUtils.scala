package stepdefs

import org.openqa.selenium.WebElement
import org.scalatest.Assertions
import org.scalatest.selenium.Chrome
import org.scalatest.Matchers._

trait WebDriverUtils extends Chrome with Assertions { outer =>
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
