package stepdefs

import com.typesafe.config.ConfigFactory
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.Config
import org.scalatest.concurrent.Eventually
import org.scalatest.{Inspectors, Matchers}

import scala.concurrent.duration._

class CreateFulfilmentOrder_SkipAll_ExceptOne extends ScalaDsl with EN
  //with NewFulfilmentOrderPageObject with Chrome
  with Matchers with Inspectors with Eventually
{

  val config = Config.load(ConfigFactory.load())

  implicit val patience = PatienceConfig(5.seconds, 500.milliseconds)






}
