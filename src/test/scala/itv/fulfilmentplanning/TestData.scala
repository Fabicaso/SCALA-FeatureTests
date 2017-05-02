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

case class Job(
    deliveryMedium: String,
    client: String
)

case class ExpectedAsset(licenceId: String,
                         productionId: String,
                         programmeTitle: String,
                         duration: String,
                         source: String,
                         assetJobType: String,
                         job: Job)

object ExpectedData {

  val skyJob       = Job("HardDrive", "Sky-Test")
  val bbcJob       = Job("Online", "BBC-Test")
  val skyItaliaJob = Job("Tape", "SKY-ITALIA-Test")
  val tmzJob       = Job("HardDrive", "TMZ-Test")
  val raiJob       = Job("Tape", "RAI-Test")
  val itvJob       = Job("Tape", "ITV-Test")

  val expectedAssets: List[ExpectedAsset] = List(
    ExpectedAsset("123669", "1/5576/0012#002", "Miss Marple - Series 5", "60", "ProRes HD", "PullAndDeliver", skyJob),
    ExpectedAsset("123665",
                  "1/9946/0001#001",
                  "Hebrides: Islands on the Edge",
                  "60",
                  "ProRes HD",
                  "PullAndDeliver",
                  skyJob),
    ExpectedAsset("123665", "2/1761/0001#001", "Desert Seas", "60", "ProRes HD", "PullAndDeliver", skyJob),
    ExpectedAsset("123665", "2/2384/0001#002", "Harry at 30", "60", "ProRes HD", "Transcode", skyItaliaJob),
    ExpectedAsset("123555", "1/5634/0030#002", "Entry Wounds", "120", "ProRes HD", "TapeAsSource", skyItaliaJob),
    ExpectedAsset("123555", "1/5634/0031#002", "Entry Wounds", "120", "ProRes HD", "TapeAsSource", skyItaliaJob),
    ExpectedAsset("123667", "1/6195/0044#001", "Murdoch Mysteries", "60", "ProRes HD", "PullAndDeliver", tmzJob),
    ExpectedAsset("123667", "1/6195/0045#001", "Murdoch Mysteries", "60", "ProRes HD", "PullAndDeliver", tmzJob),
    ExpectedAsset("127099", "9L/91828", "An Audience with Victoria Wood", "60", "ProRes HD", "TapeAsSource", raiJob),
    ExpectedAsset("127093", "2/2990/0001#001", "Angelby", "60", "ProRes HD", "TapeAsSource", raiJob),
    ExpectedAsset("127093", "2/1229/0001#002", "Endeavour", "60", "ProRes HD", "TapeAsSource", raiJob),
    ExpectedAsset("127093", "2/4463/0001#001", "Old Money", "60", "ProRes HD", "TapeAsSource", raiJob),
    ExpectedAsset("123333", "1/7314/0008#002", "Vera - Series 2", "60", "ProRes HD", "TapeAsSource", itvJob)
  )

}

object ExpectedAsset {
  import org.scalatest.Assertions.fail

  def apply(productionId: String): ExpectedAsset =
    ExpectedData.expectedAssets
      .find(_.productionId == productionId)
      .getOrElse(fail(s"Unable to find expected asset for $productionId"))

}
