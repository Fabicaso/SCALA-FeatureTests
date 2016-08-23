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

    println("Fulfilment page opened")
  }


  And("""^I have entered licence id "(.+)"$""") { (licenceId: String) =>
    println("Typing licence ID")
    textField(LicenceNumberField).value = licenceId

      println(s"I've typed in licence: $licenceId")

    click on SelectAssetButton
    println("I've clicked the 'Select Asset' Button")

  }


  When("""^I Skip All the Product IDs$""") { () =>
    eventually{

      SkipAllButton.findAllElements should not be empty

    }
    click on SkipAllButton
    println("I've click the Skip All Button")


  }

  //---------------------[Type licence--> Pick assets]--------------------

  And("""^I select only one asset for the firstProdID$""") {
    () =>
         click on SelectSkippedAsset
      println("I've selected the Asset: for ProductionID : " + ProductionID)

      click on SelectFormat
      Thread.sleep(5000)
  }

  And("""^I enter a new client profile "(.+)"$""") { (clientProfile: String) =>
    eventually(textField(clientProfileInput)).value = clientProfile

  }

  Then("""^After I have clicked on the Create button the Fulfilment Order is COMPLETE$""") {
    () =>
      click on CreateButton
      println(s"Order Creation: Complete")
      Thread.sleep(5000)


  }

  After { _ =>
    println("Shutting down new fulfilment request page")
    quit()

  }
}
