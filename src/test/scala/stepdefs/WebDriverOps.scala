package stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

trait WebDriverOps { self: ScalaDsl with EN =>

  var webDriverMaybe: Option[WebDriver] = None

  implicit def webDriver: WebDriver = webDriverMaybe.getOrElse(throw new IllegalStateException("no web driver created"))

  Before { _ =>
    println("Creating new Chrome web driver")
    webDriverMaybe = Some(new ChromeDriver())
  }

  After { _ =>
    println("Shutting down new fulfilment request page from CloseAll Class")
    webDriverMaybe.foreach(_.quit())
  }
}
