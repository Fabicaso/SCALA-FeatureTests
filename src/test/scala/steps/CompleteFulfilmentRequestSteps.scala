package steps

import itv.fulfilmentplanning.ExpectedData._
import itv.fulfilmentplanning.pageobjects._
import itv.fulfilmentplanning.{ExpectedAsset, Job}

import scala.concurrent.duration._

class CompleteFulfilmentRequestSteps extends BaseSteps with NewRequestPageObject with ConfirmRequestPageObject {

  override implicit val patienceConfig = PatienceConfig(10.seconds, 200.milliseconds)

  When("""^I complete the fulfilment request for '(.*)' and ProdID '(.*)' with '(.*)' selecting '(.*)'$""") {
    (series: String, productionIds: String, requiredDate: String, expectedAssetsToSelect: String) =>
      logger.info(scenarioMarker, s"Completing the fulfilment request")
      waitUntilPageIsLoaded()

      val productionIdsToSelect = productionIds.split(",")
      selectAssets(series, expectedAssetsToSelect, productionIdsToSelect)
      RequestNextButton.clickWhenIsDisplayed
      RequestConfirmLoaded.whenIsEnabled
      fillRequestDetails(assetFor(productionIdsToSelect.last))
      setRequiredByToAsset(assetFor(productionIdsToSelect.last).licenceId,
                           productionIds,
                           RequiredByDateQuery(requiredDate))

      SendRequestButton.clickWhenIsDisplayed
      eventually {
        SentRequestConfirmation.whenIsDisplayed(PatienceConfig(10.seconds, 100.milliseconds), scenarioMarker)
      }
      logger.info(scenarioMarker, s"Request has been sent!")
  }

  private def selectAssets(series: String, expectedAssetsToSelect: String, productionIdsToSelect: Array[String]) = {
    eventually {
      if (!ProductionIdButton(productionIdsToSelect.head).findElement.exists(_.isDisplayed)) {
        newRequestSeriesRow(series).clickWhenIsDisplayed
      }
      ProductionIdButton(productionIdsToSelect.head).findElement.map(_.isDisplayed) should ===(Some(true))
    }

    productionIdsToSelect.foreach { productionId =>
      ProductionIdButton(productionId).clickWhenIsEnabled
      val assetsToSelect = AssetsToSelect(expectedAssetsToSelect, productionId)
      if (assetsToSelect.isEmpty)
        fail(s"Unsupported assets to select type: $expectedAssetsToSelect")
      else {
        if (assetsToSelect.size > 1)
          SelectMultipleAssets.clickWhenIsDisplayed
        eventually {
          assetsToSelect.foreach { nextAssetToSelect =>
            nextAssetToSelect.clickWhenIsDisplayed
          }
        }
        if (assetsToSelect.size > 1) {
          CloseAssetBoxButton(productionId).clickWhenIsDisplayed
          ProductionIdMultiple(productionId).whenIsDisplayed
        } else
          ProductionIdSelected(productionId).whenIsDisplayed
      }
    }
  }

  private def fillRequestDetails(expectedAsset: ExpectedAsset) = {
    fillRequestFor(expectedAsset.job)
    NextButtonOnSendRequestPage.clickWhenIsDisplayed
  }

  private def fillRequestFor(job: Job) = {
    textField(ClientField).value = job.client
    DeliveryMediumField.clickWhenIsDisplayed
    DeliveryMediumValue(job.deliveryMedium).clickWhenIsDisplayed
    JobField.clickWhenIsDisplayed
    JobValue(job.jobType).clickWhenIsDisplayed
    textArea(DeliveryMethod).value = job.deliveryMethod
    job.preferredOutputFrameRate.foreach { value =>
      FrameRate.clickWhenIsDisplayed
      FrameRateValue(value).clickWhenIsDisplayed
    }
    job.spec.foreach { value =>
      textField(SpecField).value = value
    }
  }

  private def waitUntilPageIsLoaded() = {
    PageLoadedRequest
      .whenIsEnabled(patienceConfig = PatienceConfig(20.seconds, 1.second), scenarioMarker)
      .isEnabled shouldBe true
  }

  private def setRequiredByToAsset(licenceId: String, productionIds: String, date: Option[Query]) = {
    logger.debug(scenarioMarker, s"Selecting the assets in order to add the Required By date")
    PageLoadedAssetRequestDates.whenIsEnabled

    val productionIdsToSelect = productionIds.split(",")
    productionIdsToSelect.foreach { productionId =>
      selectAssetsOnRequiredByPage(licenceId, productionId).clickWhenIsDisplayed
      date.foreach { dateQuery =>
        logger.debug(scenarioMarker, s"Setting date $dateQuery")
        RequiredByDateField.clickWhenIsDisplayed
        EditRequiredByDateDropdown.clickWhenIsDisplayed
        eventually(click on dateQuery.whenIsDisplayed)
      }
    }
  }

}
