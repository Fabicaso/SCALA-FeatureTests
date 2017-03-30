package steps

import com.typesafe.scalalogging.StrictLogging
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.utils.{WebBrowserUtils, WebDriverOps}
import org.scalatest.concurrent.Eventually
import org.scalatest.{Assertions, Inspectors, Matchers}
import org.scalatest.selenium.WebBrowser

trait BaseSteps
    extends ScalaDsl
    with EN
    with WebBrowserUtils
    with WebBrowser
    with WebDriverOps
    with Matchers
    with Inspectors
    with Eventually
    with Assertions
    with StrictLogging {}
