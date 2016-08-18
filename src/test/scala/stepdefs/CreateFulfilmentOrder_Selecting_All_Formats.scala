package stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers


class CreateFulfilmentOrder_Selecting_All_Formats extends ScalaDsl with EN with Matchers {

  val NewFulfilmentRequestPage = new PageObject


  //---------------------[Type licence]--------------------
  Given("""^I open the new Fulfilment Page$""") { () =>
    NewFulfilmentRequestPage.OpenHomePage("http://craft.stg.fp.itv.com/")
    println("Fulfilment page opened")

  }


  And("""^I enter the licence id "(.+)"$""") { (licenceId: String) =>
    println("Typing licence ID")
    NewFulfilmentRequestPage.LicenceNumberField.sendKeys(licenceId)

    NewFulfilmentRequestPage.SelectAssetButton.click()
    println("I've clicked the 'Select Asset' Button")

    println(s"I've typed in licence: $licenceId")
    print("Waiting 'Pick assets' page to load...")
    Thread.sleep(3000)
    println(".... 'Pick assets' page loaded")
    println("Pick assets Page Title: " + NewFulfilmentRequestPage.PageTitle)
    println("I'm in 'Type licence--> Pick assets' page ")
  }


  When("""^I select all the Asset Formats$""") { () =>
    val AssetButtonNumber: Int = NewFulfilmentRequestPage.AssetButtonList.size()
    println("Production ID: " + NewFulfilmentRequestPage.ProductionID + "has :" + AssetButtonNumber + " assets")
    Thread.sleep(4000)
    for (i <- 0 until AssetButtonNumber) {

      val AssetText: String = NewFulfilmentRequestPage.AssetButtonList.get(i).getText()

      if (AssetText == "Assets") {
        NewFulfilmentRequestPage.AssetButtonList.get(i).click()
        NewFulfilmentRequestPage.SelectFormat.click()
        Thread.sleep(1000)
      }

      else if (AssetText == "No assets found") {
        println(NewFulfilmentRequestPage.NoAssetFoundText + "for Prod ID: " + NewFulfilmentRequestPage.ProductionID)
      }
    }

  }


  And("""^the Create button is clicked$""") { () =>
    Thread.sleep(2000)
    NewFulfilmentRequestPage.CreateButton.click()
    println(s"Order Creation: Complete")
    Thread.sleep(10000)
    NewFulfilmentRequestPage.Close()
  }


}

