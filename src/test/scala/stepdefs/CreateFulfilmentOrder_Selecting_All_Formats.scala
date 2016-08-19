package stepdefs

import com.typesafe.config.ConfigFactory
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.Config
import org.scalatest.{Inspectors, Matchers}
import org.scalatest.concurrent.Eventually

import scala.concurrent.duration._


class CreateFulfilmentOrder_Selecting_All_Formats extends ScalaDsl with EN with Matchers with Inspectors with Eventually {

  val config = Config.load(ConfigFactory.load())

  implicit val patience = PatienceConfig(5.seconds, 1.second)

  val NewFulfilmentRequestPage = new PageObject


  //---------------------[Type licence]--------------------
  Given("""^I open the new Fulfilment Page$""") { () =>
    NewFulfilmentRequestPage.OpenHomePage(config.homePageUrl)
    println("Fulfilment page opened")
  }


  And("""^I enter the licence id "(.+)"$""") { (licenceId: String) =>
    println("Typing licence ID")
    NewFulfilmentRequestPage.LicenceNumberField.sendKeys(licenceId)

    NewFulfilmentRequestPage.SelectAssetButton.click()
    println("I've clicked the 'Select Asset' Button")

    println(s"I've typed in licence: $licenceId")
    println("Pick assets Page Title: " + NewFulfilmentRequestPage.PageTitle)
    println("I'm in 'Type licence--> Pick assets' page ")
  }


  When("""^I select all the Asset Formats$""") { () =>
    val numberOfProductions = eventually {
      val numberOfProductions: Int = NewFulfilmentRequestPage.AssetButtonList.size()
      numberOfProductions shouldBe 7
      numberOfProductions
    }
    forAll (0 until numberOfProductions) { i =>
      val button = eventually {
        val button = NewFulfilmentRequestPage.AssetButtonList.get(i)
        button.getText should be("Assets").or(be("No assets found"))
        button
      }
      if (button.getText == "Assets") {
        button.click()
        eventually(NewFulfilmentRequestPage.SelectFormat).click()
      }
      button.isDisplayed shouldBe true
    }
  }

  And("""^I enter client profile "(.+)"$""") { (clientProfile: String) =>
    eventually(NewFulfilmentRequestPage.clientProfileInput).sendKeys(clientProfile)
  }

  And("""^the Create button is clicked$""") { () =>
    eventually(NewFulfilmentRequestPage.CreateButton).click()
    println(s"Order Creation: Complete")
  }

  After { _ =>
    println("Shutting down new fulfilment request page")
    NewFulfilmentRequestPage.Quit()
  }

}

