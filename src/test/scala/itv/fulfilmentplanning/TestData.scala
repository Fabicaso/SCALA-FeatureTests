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

case class AssetRequested(deliveryMedium: String,
                          productionId: String,
                          programmeTitle: String,
                          duration: String,
                          source: String,
                          assetJobType: String,
                          client: String)

object AssetRequested {

  val requestedAssets = Map(
    "2/1761/0001#001" -> AssetRequested("HardDrive",
                                        "2/1761/0001#001",
                                        "Desert Seas",
                                        "60",
                                        "ProRes HD",
                                        "PullAndDeliver",
                                        "Sky-Test"),
    "2/2384/0001#002" -> AssetRequested("Online",
                                        "2/2384/0001#002",
                                        "Harry at 30",
                                        "60",
                                        "ProRes HD",
                                        "Transcode",
                                        "BBC-Test"),
    "1/5634/0030/31#001" -> AssetRequested("Tape",
                                           "2/2384/0001#002",
                                           "Entry Wounds",
                                           "120",
                                           "ProRes HD",
                                           "TapeAsSource",
                                           "SKY-ITALIA-Test")
  )

}
