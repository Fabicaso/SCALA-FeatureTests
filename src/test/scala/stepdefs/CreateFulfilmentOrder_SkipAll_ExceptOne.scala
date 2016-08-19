package stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
class CreateFulfilmentOrder_SkipAll_ExceptOne extends ScalaDsl with EN with Matchers {

  val NewFulfilmentRequestPage = new PageObject


  //---------------------[Type licence]--------------------
  Given("""^I have navigated to the new fulfilment request page$""") { () => NewFulfilmentRequestPage.OpenHomePage("http://craft.stg.fp.itv.com")

    println("Type licence Page Title:"+ NewFulfilmentRequestPage.PageTitle)
    println("I'm in the New Fulfilment Request Page ")
  }


  And("""^I have entered licence id "(.+)"$""") {
    (licenceId: String) => NewFulfilmentRequestPage.LicenceNumberField.sendKeys(licenceId)

      println(s"I've typed in licence: $licenceId")
  }


  When("""^I Skip All the Asset and I click on the first Select Assets Button$""") {
    () => NewFulfilmentRequestPage.SelectAssetButton.click()
      println("I've clicked the 'Select Asset' Button")
  }

//---------------------[Type licence--> Pick assets]--------------------

  And("""^Select all the Formats for each Assets$""") {
    () => print("Waiting 'Pick assets' page to load...")
      Thread.sleep(3000)
      println(".... 'Pick assets' page loaded")
      println("Pick assets Page Title: "+ NewFulfilmentRequestPage.PageTitle)
      println("I'm in 'Type licence--> Pick assets' page ")


      NewFulfilmentRequestPage.SkipAllButton.click()
      println("I've click the Skip All Button")
      Thread.sleep(1000)
      NewFulfilmentRequestPage.SelectSkippedAsset.click()
      println("I've selected the first Skipped Format")
      Thread.sleep(1000)
      NewFulfilmentRequestPage.SelectFormat.click()
      println("I've selected the Format: GE MEZZ HD FILE for ProductionID : "+NewFulfilmentRequestPage.ProductionID)
  }

Then ("""^After I have clicked on the Create button the Fulfilment Order is COMPLETE$""") {
  () => NewFulfilmentRequestPage.CreateButton.click()
    println (s"Order Creation: Complete")
    Thread.sleep(10000)


}

  After { _ =>
    println("Shutting down new fulfilment request page")
    NewFulfilmentRequestPage.Quit()
  }
}
