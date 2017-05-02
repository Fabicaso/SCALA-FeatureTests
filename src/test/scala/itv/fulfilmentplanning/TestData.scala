package itv.fulfilmentplanning

import java.time.LocalDate

case class Credentials(email: String, password: String)

object Credentials {

  val testCredentials = Credentials("automationtestcraft@gmail.com", "@utomationtestcraft")

}

object TestData {
  def dateFrom(requiredBy: String) = {
    requiredBy match {
      case "a required by date" => Some(LocalDate.now().toString)
      case _                    => None
    }
  }
}

case class Job(client: String, jobType: String, deliveryMedium: String, deliveryMethod: String)

case class ExpectedAsset(productionId: String,
                         licenceId: String,
                         programmeTitle: String,
                         duration: String,
                         source: String,
                         job: Job)

object ExpectedData {

  import org.scalatest.Assertions.fail

  def assetFor(productionId: String): ExpectedAsset =
    ExpectedData.expectedAssets
      .find(_.productionId == productionId)
      .getOrElse(fail(s"Unable to find expected asset for $productionId"))

  private val skyJob       = Job("Sky-Test", "PullAndDeliver", "HardDrive", "Delivery Method for sky")
  private val bbcJob       = Job("BBC-Test", "Transcode", "Online", "Delivery Method for bbc")
  private val skyItaliaJob = Job("SKY-ITALIA-Test", "TapeAsSource", "Tape", "Delivery Method for sky italia")
  private val tmzJob       = Job("TMZ-Test", "PullAndDeliver", "HardDrive", "Delivery Method for tmz")
  private val raiJob       = Job("RAI-Test", "TapeAsSource", "Tape", "Delivery Method for rai")
  private val itvJob       = Job("ITV-Test", "TapeAsSource", "Tape", "Delivery Method for itv")

  private val expectedAssets: List[ExpectedAsset] = List(
    ExpectedAsset("1/5576/0012#002", "123669", "Miss Marple - Series 5", "60", "ProRes HD", skyJob),
    ExpectedAsset("1/9946/0001#001", "123665", "Hebrides: Islands on the Edge", "60", "ProRes HD", skyJob),
    ExpectedAsset("2/1761/0001#001", "123665", "Desert Seas", "60", "ProRes HD", skyJob),
    ExpectedAsset("2/2384/0001#002", "123665", "Harry at 30", "60", "ProRes HD", bbcJob),
    ExpectedAsset("1/5634/0030#002", "123555", "Entry Wounds", "120", "ProRes HD", skyItaliaJob),
    ExpectedAsset("1/5634/0031#002", "123555", "Entry Wounds", "120", "ProRes HD", skyItaliaJob),
    ExpectedAsset("1/6195/0044#001", "123667", "Murdoch Mysteries", "60", "ProRes HD", tmzJob),
    ExpectedAsset("1/6195/0045#001", "123667", "Murdoch Mysteries", "60", "ProRes HD", tmzJob),
    ExpectedAsset("9L/91828", "127099", "An Audience with Victoria Wood", "60", "ProRes HD", raiJob),
    ExpectedAsset("2/2990/0001#001", "127093", "Angelby", "60", "ProRes HD", raiJob),
    ExpectedAsset("2/1229/0001#002", "127093", "Endeavour", "60", "ProRes HD", raiJob),
    ExpectedAsset("2/4463/0001#001", "127093", "Old Money", "60", "ProRes HD", raiJob),
    ExpectedAsset("1/7314/0008#002", "123333", "Vera - Series 2", "60", "ProRes HD", itvJob)
  )

}
