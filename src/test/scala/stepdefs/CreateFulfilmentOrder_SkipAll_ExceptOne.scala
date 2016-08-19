package stepdefs

import com.typesafe.config.ConfigFactory
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.Config
import org.scalatest.concurrent.Eventually
import org.scalatest.{Inspectors, Matchers}
import org.scalatest.selenium.Chrome

import scala.concurrent.duration._

class CreateFulfilmentOrder_SkipAll_ExceptOne extends ScalaDsl with EN
  with NewFulfilmentOrderPageObject with Chrome with Matchers with Inspectors with Eventually {

  val config = Config.load(ConfigFactory.load())

  implicit val patience = PatienceConfig(5.seconds, 500.milliseconds)

  //---------------------[Type licence]--------------------
  Given("""^I have navigated to the new fulfilment request page$""") { () =>
    go to config.homePageUrl

    println("Type licence Page Title:" + pageTitle)
    println("I'm in the New Fulfilment Request Page ")
  }


  And("""^I have entered licence id "(.+)"$""") {
    (licenceId: String) =>
      textField(LicenceNumberField).value = licenceId

      println(s"I've typed in licence: $licenceId")
  }


  When("""^I Skip All the Asset and I click on the first Select Assets Button$""") {
    () =>
      click on SelectAssetButton
      println("I've clicked the 'Select Asset' Button")
  }

  //---------------------[Type licence--> Pick assets]--------------------

  And("""^Select all the Formats for each Assets$""") {
    () => print("Waiting 'Pick assets' page to load...")
      Thread.sleep(3000)
      println(".... 'Pick assets' page loaded")
      println("Pick assets Page Title: " + pageTitle)
      println("I'm in 'Type licence--> Pick assets' page ")


      click on SkipAllButton
      println("I've click the Skip All Button")
      Thread.sleep(1000)
      click on SelectSkippedAsset
      println("I've selected the first Skipped Format")
      Thread.sleep(1000)
      click on SelectFormat
      println("I've selected the Format: GE MEZZ HD FILE for ProductionID : " + ProductionID)
  }

  Then("""^After I have clicked on the Create button the Fulfilment Order is COMPLETE$""") {
    () =>
      click on CreateButton
      println(s"Order Creation: Complete")
      Thread.sleep(10000)


  }

  After { _ =>
    println("Shutting down new fulfilment request page")
    quit
  }
}
