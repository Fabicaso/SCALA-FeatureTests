package stepdefs

import com.typesafe.config.ConfigFactory
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.Config
import org.openqa.selenium.WebElement
import org.scalatest.{Inspectors, Matchers}
import org.scalatest.concurrent.Eventually

import scala.concurrent.duration._


class CreateFulfilmentOrder_Selecting_All_Formats extends ScalaDsl with EN with Matchers with Inspectors with Eventually {

  val config = Config.load(ConfigFactory.load())

  implicit val patience = PatienceConfig(5.seconds, 500.milliseconds)

  val NewFulfilmentRequestPage = new PageObject


  //---------------------[Type licence]--------------------
  Given("""^I open the new Fulfilment Page$""") { () =>
    NewFulfilmentRequestPage.OpenHomePage(config.homePageUrl)
    println("Fulfilment page opened")
  }


  And("""^I enter the licence id "(.+)"$""") { (licenceId: String) =>
    println("Typing licence ID")
    NewFulfilmentRequestPage.LicenceNumberField.sendKeys(licenceId)

    NewFulfilmentRequestPage.SelectAssetButton.clickIfEnabled
    println("I've clicked the 'Select Asset' Button")

    println(s"I've typed in licence: $licenceId")
    println("Pick assets Page Title: " + NewFulfilmentRequestPage.PageTitle)
    println("I'm in 'Type licence--> Pick assets' page ")
  }


  When("""^I select all the Asset Formats$""") { () =>
    eventually {
      NewFulfilmentRequestPage.AssetButtonList should have size 7
    }
    forEvery(NewFulfilmentRequestPage.AssetButtonList) { assetSelectionButton =>
      eventually {
        assetSelectionButton.isDisplayed shouldBe true
        assetSelectionButton.getText should be("Assets").or(be("No assets found"))
      }

      if (assetSelectionButton.getText == "Assets") {
        assetSelectionButton.clickIfEnabled
        eventually {
          val availableAssetSelector = NewFulfilmentRequestPage.SelectFormat.getOrElse[WebElement](fail("Asset selector not found"))
          availableAssetSelector.isDisplayed shouldBe true
          availableAssetSelector.isEnabled shouldBe true
          availableAssetSelector
        }.clickIfEnabled
        eventually(NewFulfilmentRequestPage.SelectFormat shouldBe 'empty)
      }
    }
  }

  And("""^I enter client profile "(.+)"$""") { (clientProfile: String) =>
    eventually(NewFulfilmentRequestPage.clientProfileInput).sendKeys(clientProfile)
  }

  And("""^the Create button is clicked$""") { () =>
    val createButton = eventually(NewFulfilmentRequestPage.CreateButton)
    createButton.clickIfEnabled
    println(s"Order Creation: Complete")
  }

  After { _ =>
    println("Shutting down new fulfilment request page")
    NewFulfilmentRequestPage.Quit()
  }

}

