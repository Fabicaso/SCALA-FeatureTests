package itv.fulfilmentplanning

import com.typesafe.{config => typesafe}

case class Config(homePageUrl: String)

object Config {
  def load(typesafeConfig: typesafe.Config): Config = {
    Config(typesafeConfig.getString("homePageUrl"))
  }
}
