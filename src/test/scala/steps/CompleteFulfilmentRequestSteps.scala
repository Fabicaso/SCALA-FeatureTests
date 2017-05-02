package steps

import itv.fulfilmentplanning.ExpectedData._
import itv.fulfilmentplanning.{ExpectedAsset, Job}
import itv.fulfilmentplanning.pageobjects._

import scala.concurrent.duration._

class CompleteFulfilmentRequestSteps extends BaseSteps with NewRequestPageObject with ConfirmRequestPageObject {

  override implicit val patienceConfig = PatienceConfig(10.seconds, 200.milliseconds)

  When("""^I complete the fulfilment request for '(.*)' and ProdID '(.*)' with '(.*)' selecting '(.*)'$""") {
    (series: String, productionIds: String, requiredDate: String, expectedAssetsToSelect: String) =>
      logger.info(scenarioMarker, s"Completing the fulfilment request")
      reloadPage()
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
      SentRequestConfirmation.whenIsDisplayed(PatienceConfig(5.seconds, 100.milliseconds), scenarioMarker)
      logger.info(scenarioMarker, s"Request has been sent!")
  }

  private def selectAssets(series: String, expectedAssetsToSelect: String, productionIdsToSelect: Array[String]) = {
    eventually {
      if (!ProductionIdButton(productionIdsToSelect.head).findElement.exists(_.isDisplayed)) {
        click on newRequestSeriesRow(series).whenIsDisplayed
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
    expectedAsset.job.jobType match {
      case "Transcode" => fillCommonRequestFor(OnlineDeliveryMedium, TranscodeJob, expectedAsset.job)
      case "PullAndDeliver" =>
        fillCommonRequestFor(HardDriveDeliveryMedium, PullAndDeliverJob, expectedAsset.job)
      case "TapeAsSource" => fillCommonRequestFor(TapeDeliveryMedium, TapeAsSourceJob, expectedAsset.job)
      case unsupportedJob => fail(s"Unsupported job type $unsupportedJob in $expectedAsset")
    }
    NextButtonOnSendRequestPage.clickWhenIsDisplayed
  }

  private def waitUntilPageIsLoaded() = {
    PageLoadedRequest
      .whenIsEnabled(patienceConfig = PatienceConfig(20.seconds, 1.second), scenarioMarker)
      .isEnabled shouldBe true
  }

  private def fillCommonRequestFor(deliveryMedium: Query, jobToSelect: Query, job: Job) = {
    textField(ClientField).value = job.client
    DeliveryMediumField.clickWhenIsDisplayed
    deliveryMedium.clickWhenIsDisplayed
    JobField.clickWhenIsDisplayed
    jobToSelect.clickWhenIsDisplayed
    textArea(DeliveryMethod).value = job.deliveryMethod
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
