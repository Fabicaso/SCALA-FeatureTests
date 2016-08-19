import org.openqa.selenium.WebElement
import org.scalatest.Matchers._
import org.scalatest.selenium.Chrome

package object stepdefs {
  implicit class WebElementW(element: WebElement) {
    def clickIfEnabled: Unit = {
      element.isEnabled shouldBe true
      element.click()
    }
  }
}
