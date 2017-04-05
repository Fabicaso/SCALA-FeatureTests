package steps

import com.typesafe.scalalogging.StrictLogging
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.Config
import itv.fulfilmentplanning.ext.{WebBrowserExt, WebDriverOps}
import itv.fulfilmentplanning.pageobjects.CommonPageObject
import org.scalactic.TypeCheckedTripleEquals
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
    with TypeCheckedTripleEquals
    with StrictLogging {
  val config = Config.config

}
