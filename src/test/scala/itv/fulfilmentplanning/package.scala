package itv

import org.openqa.selenium.WebElement
import org.scalatest.Matchers._

package object fulfilmentplanning {
  implicit class WebElementW(element: WebElement) {
    def clickIfEnabled: Unit = {
      element.isEnabled shouldBe true
      element.click()
    }
  }
}
