package stepdefs

import com.typesafe.config.ConfigFactory
import cucumber.api.scala.{EN, ScalaDsl}
import itv.fulfilmentplanning.Config
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.WebBrowser
import org.scalatest.{Inspectors, Matchers}

import scala.concurrent.duration._


class CreateFulfilmentOrderSteps extends ScalaDsl with EN
  with NewFulfilmentOrderPageObject with WebBrowserUtils with WebBrowser with WebDriverOps
  with Matchers with Inspectors with Eventually {

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

  When("""^I Skip All the Product IDs$""") { () =>
    eventually {
      SkipAllButton.findAllElements should not be empty
    }
    click on SkipAllButton
    println("I've click the Skip All Button")


  }

  //---------------------[Type licence--> Pick assets]--------------------

  And("""^I select only one asset for the firstProdID$""") {
    () =>
      click on eventually(SelectSkippedAsset.findElementOrFail)
      println("I've selected the Asset: for ProductionID : " + ProductionID)

      click on eventually(SelectFormat.findElementOrFail)

  }

  And("""^I enter client profile "(.+)"$""") { (clientProfile: String) =>
    eventually(textField(clientProfileInput)).value = clientProfile
    println(s"I've typed  in the ClientProfile: $clientProfile")
  }

  And("""^I have clicked on the Create button$""") { () =>
    clickIfEnabled(eventually(CreateButton.findElementOrFail))
    println(s"Order Creation: Complete")
  }


  And ("""^I enter Required By Date "(.+)"$""") { (requiredBy: String) =>
    eventually(textField(RequiredByInput)).value = requiredBy
    println(s"I've typed in the Required By Date : $requiredBy")

    Thread.sleep(5000)
  }


  Then("""^There should be some productions available$""") { () =>
    eventually {
      AssetButtonList.findAllElements should not be 'empty
    }
  }

  Then("""^the Fulfilment Order is COMPLETE$""") { () =>

  }

}

