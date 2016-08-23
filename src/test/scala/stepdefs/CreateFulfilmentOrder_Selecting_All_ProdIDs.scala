package stepdefs

import com.typesafe.config.ConfigFactory
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.Config
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.Chrome
import org.scalatest.{Inspectors, Matchers}

import scala.concurrent.duration._


class CreateFulfilmentOrder_Selecting_All_ProdIDs extends ScalaDsl with EN
  with NewFulfilmentOrderPageObject with Chrome with WebDriverUtils with Matchers with Inspectors with Eventually {

  val config = Config.load(ConfigFactory.load())

  implicit val patience = PatienceConfig(5.seconds, 500.milliseconds)

  //---------------------[Type licence]--------------------
  Given("""^I open the new Fulfilment Page$""") { () =>
    go to config.homePageUrl
    println("Fulfilment page opened")
  }


  And("""^I enter the licence id "(.+)"$""") { (licenceId: String) =>
    println("Typing licence ID")
    textField(LicenceNumberField).value = licenceId

    click on SelectAssetButton.findElementOrFail
    println("I've clicked the 'Select Asset' Button")

    println(s"I've typed in licence: $licenceId")
    println("Pick assets Page Title: " + pageTitle)
    println("I'm in 'Type licence--> Pick assets' page ")
  }

  Then("""^There should be some productions available$""") { () =>
    eventually {
      AssetButtonList.findAllElements should not be 'empty
    }
  }


  When("""^I select all the Asset Formats$""") { () =>
    forEvery(AssetButtonList.findAllElements.toList) { assetSelectionButton =>
      eventually {
        assetSelectionButton.isDisplayed shouldBe true
        assetSelectionButton.text should be("Assets").or(be("No assets found"))
      }

      if (assetSelectionButton.text == "Assets") {
        clickIfEnabled(assetSelectionButton)
        eventually {
          val availableAssetSelector = SelectFormat.findElementOrFail
          availableAssetSelector.isDisplayed shouldBe true
          availableAssetSelector.isEnabled shouldBe true
          availableAssetSelector
        }.clickIfEnabled
        eventually(SelectFormat.findElement shouldBe 'empty)
      }
    }
  }

  And("""^I enter client profile "(.+)"$""") { (clientProfile: String) =>
    eventually(textField(clientProfileInput)).value = clientProfile
  }

  And("""^the Create button is clicked$""") { () =>
    clickIfEnabled(eventually(CreateButton.findElementOrFail))
    println(s"Order Creation: Complete")
  }
/*
  After { _ =>
    println("Shutting down new fulfilment request page")
    quit()
  }
*/
}

