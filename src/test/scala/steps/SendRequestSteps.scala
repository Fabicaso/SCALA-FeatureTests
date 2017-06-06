package steps

import itv.fulfilmentplanning.ExpectedData.assetFor
import itv.fulfilmentplanning.pageobjects._
import itv.fulfilmentplanning.{ExpectedAsset, Job}

import scala.concurrent.duration._

class SendRequestSteps
    extends BaseSteps
    with MenuPageObject
    with OverviewPageObject
    with NewRequestPageObject
    with ConfirmRequestPageObject {

  override implicit val patienceConfig = PatienceConfig(10.seconds, 200.milliseconds)

  Given(
    """^I am on the 'Send Request' page using the following licence number '(.*)' series '(.*)' and ProdID '(.*)' selecting '(.*)'$""") {
    (licenceId: String, series: String, productionIds: String, expectedAssetsToSelect: String) =>
      openNewRequestPage(licenceId)
      openSendRequestPage(series, productionIds, expectedAssetsToSelect)
  }

  Given(
    """^I am on the 'Required By' page using the following licence number '(.*)' series '(.*)' and ProdID '(.*)' selecting '(.*)'$""") {
    (licenceId: String, series: String, productionIds: String, expectedAssetsToSelect: String) =>
      openNewRequestPage(licenceId)
      openSendRequestPage(series, productionIds, expectedAssetsToSelect)
      fillRequestDetails(assetFor(productionIds))
      PageLoadedAssetRequestDates.whenIsEnabled
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

  Then("""^the fields on the Send Request Form are already prepopulated for ProdID '(.*)'$""") {
    (productionId: String) =>
      val prodId: ExpectedAsset = assetFor(productionId)
      eventually {
        ClientField.element.attribute("value").contains(prodId.job.client)
        DeliveryMediumField.element.attribute("value").contains(prodId.job.deliveryMedium)
        JobField.element.attribute("value").contains(prodId.job.jobType)
        FrameRate.element.attribute("value").contains(prodId.job.preferredOutputFrameRate)
        SpecField.element.attribute("value").contains(prodId.job.spec)
        DeliveryMethod.element.attribute("value").contains(prodId.job.deliveryMethod)
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
}
