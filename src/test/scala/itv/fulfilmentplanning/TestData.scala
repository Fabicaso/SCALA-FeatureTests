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
    "1/5576/0012#002" -> AssetRequested("123669",
                                        "1/5576/0012#002",
                                        "HardDrive",
                                        "Miss Marple - Series 5",
                                        "60",
                                        "ProRes HD",
                                        "PullAndDeliver",
                                        "Sky-Test"),
    "1/9946/0001#001" -> AssetRequested("123665",
                                        "1/9946/0001#001",
                                        "HardDrive",
                                        "Hebrides: Islands on the Edge",
                                        "60",
                                        "ProRes HD",
                                        "PullAndDeliver",
                                        "Sky-Test"),
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
    "1/5634/0030#002" -> AssetRequested("123555",
                                        "1/5634/0030#002",
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
                                        "TMZ-Test"),
    "2/3150/0001#002" -> AssetRequested("127093",
                                        "2/3150/0001#002",
                                        "Tape",
                                        "Tutankhamun",
                                        "60",
                                        "ProRes HD",
                                        "TapeAsSource",
                                        "RAI-Test"),
    "2/3150/0002#002" -> AssetRequested("127093",
                                        "2/3150/0002#002",
                                        "Tape",
                                        "Tutankhamun",
                                        "60",
                                        "ProRes HD",
                                        "TapeAsSource",
                                        "RAI2-Test"),
    "1/7314/0008#002" -> AssetRequested("123333",
                                        "1/7314/0008#002",
                                        "Tape",
                                        "Vera - Series 2",
                                        "60",
                                        "ProRes HD",
                                        "TapeAsSource",
                                        "ITV-Test")
  )

}
