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

case class AssetRequested(licenceId: String,
                          productionId: String,
                          deliveryMedium: String,
                          programmeTitle: String,
                          duration: String,
                          source: String,
                          assetJobType: String,
                          client: String)

object AssetRequested {

  val requestedAssets = Map(
    "2/1761/0001#001" -> AssetRequested("123665",
                                        "2/1761/0001#001",
                                        "HardDrive",
                                        "Desert Seas",
                                        "60",
                                        "ProRes HD",
                                        "PullAndDeliver",
                                        "Sky-Test"),
    "2/2384/0001#002" -> AssetRequested("123665",
                                        "2/2384/0001#002",
                                        "Online",
                                        "Harry at 30",
                                        "60",
                                        "ProRes HD",
                                        "Transcode",
                                        "BBC-Test"),
    "1/5634/0030/31#001" -> AssetRequested("123555",
                                           "1/5634/0030/31#001",
                                           "Tape",
                                           "Entry Wounds",
                                           "120",
                                           "ProRes HD",
                                           "TapeAsSource",
                                           "SKY-ITALIA-Test"),
    "1/6195/0044#001" -> AssetRequested("123667",
                                        "1/6195/0044#001",
                                        "HardDrive",
                                        "Murdoch Mysteries",
                                        "60",
                                        "ProRes HD",
                                        "PullAndDeliver",
                                        "TMZ-Test"),
    "1/6195/0045#001" -> AssetRequested("123667",
                                        "1/6195/0045#001",
                                        "HardDrive",
                                        "Murdoch Mysteries",
                                        "60",
                                        "ProRes HD",
                                        "PullAndDeliver",
                                        "TMZ-Test")
  )

}
