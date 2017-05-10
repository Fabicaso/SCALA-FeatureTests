package steps

import itv.fulfilmentplanning.pageobjects._
import scala.concurrent.duration._

class SendRequestSteps
    extends BaseSteps
    with MenuPageObject
    with OverviewPageObject
    with NewRequestPageObject
    with ConfirmRequestPageObject {

  override implicit val patienceConfig = PatienceConfig(4.seconds, 200.milliseconds)

  Given(
    """^I am on the 'Send Request' page using the following licence number '(.*)' series '(.*)' and ProdID '(.*)' selecting '(.*)'$""") {
    (licenceId: String, series: String, productionIds: String, expectedAssetsToSelect: String) =>
      openNewRequestPage(licenceId)
      openSendRequestPage(series, productionIds, expectedAssetsToSelect)
  }

  When("""^I select '(.*)' as the 'Delivery Medium' and '(.*)' as the 'Job' on the 'Send Request'form$""") {
    (deliveryMedium: String, job: String) =>
      DeliveryMediumField.clickWhenIsDisplayed
      DeliveryMediumValue(deliveryMedium.replace(" ", "")).clickWhenIsDisplayed
      JobField.clickWhenIsDisplayed
      JobValue(job.replace(" ", "")).clickWhenIsDisplayed

  }

  Then("""^the options for 'Frame Rate Output' are displayed correctly$""") { () =>
    FrameRate.clickWhenIsDisplayed

    FrameRateValue("24").whenIsDisplayed.isDisplayed
    FrameRateValue("25").whenIsDisplayed.isDisplayed
    FrameRateValue("23-98").whenIsDisplayed.isDisplayed
    FrameRateValue("29-97").whenIsDisplayed.isDisplayed

    FrameRateValue("24").clickWhenIsDisplayed
  }

  And("""^Resolution Outputs and Spec Optional text box are displayed$""") { () =>
    ResolutionOutput.whenIsDisplayed.isDisplayed
    SpecOptionalField.whenIsDisplayed.isDisplayed
  }

  private def waitUntilPageIsLoaded() = {
    SendRequestsPageLoaded
      .whenIsEnabled(patienceConfig = PatienceConfig(20.seconds, 1.second), scenarioMarker)
      .isEnabled shouldBe true
  }

  private def openSendRequestPage(series: String, productionIds: String, expectedAssetsToSelect: String) = {
    logger.info(scenarioMarker, s"Go to Send Request Page for $series")
    val productionIdsToSelect = productionIds.split(",")
    selectAssets(series, expectedAssetsToSelect, productionIdsToSelect)
    RequestNextButton.clickWhenIsDisplayed
    RequestConfirmLoaded.whenIsEnabled
    logger.info(scenarioMarker, s"Send Request Page loaded")
  }

  private def openNewRequestPage(licenceId: String) = {
    logger.info(scenarioMarker, s"Go to licence number: $licenceId")
    EnterLicenceSection.clickWhenIsDisplayed
    eventually(numberField(LicenceInput)).value = licenceId.toString
    submit()
    logger.info(scenarioMarker, s"Overview Page loaded")
    CreateNewRequestButton.clickWhenIsDisplayed
    PageLoadedRequest.whenIsEnabled
    logger.info(scenarioMarker, s"New Request Page loaded")
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
}
