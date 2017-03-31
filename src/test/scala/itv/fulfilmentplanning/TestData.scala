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

case class AssetRequested(productionId: String,
                          programmeTitle: String,
                          duration: String,
                          source: String,
                          assetJobType: String)

object AssetRequested {

  val requestedAssets = Map(
    "2/1761/0001#001" -> AssetRequested("2/1761/0001#001", "Desert Seas", "60 Min", "ProRes HD", "PullAndDeliver"),
    "2/2384/0001#002" -> AssetRequested("2/2384/0001#002", "Harry at 30", "60 Min", "ProRes HD", "Transcode")
  )

}
