package steps

import com.typesafe.scalalogging.StrictLogging
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.Config
import itv.fulfilmentplanning.ext.WebDriverOps
import itv.fulfilmentplanning.pageobjects.CommonPageObject
import itv.fulfilmentplanning.browser.WebBrowserExt
import org.scalatest.concurrent.Eventually
import org.scalatest.{Assertions, Inspectors, Matchers}
import org.scalatest.selenium.WebBrowser

trait BaseSteps
    extends ScalaDsl
    with EN
    with WebBrowserExt
    with WebBrowser
    with WebDriverOps
    with Matchers
    with Inspectors
    with Eventually
    with Assertions
    with CommonPageObject
    with StrictLogging {
  val config = Config.config

}
