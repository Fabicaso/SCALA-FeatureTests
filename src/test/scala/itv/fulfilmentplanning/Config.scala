package itv.fulfilmentplanning

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.StrictLogging
import itv.fulfilmentplanning.jdbc.InitData
import itv.fulfilmentplanning.jdbc.InitData.DatabaseConfig
import itv.fulfilmentplanning.webdriver.WebDriverPool

import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader.arbitraryTypeValueReader

import scala.util.Try

case class Config(homePageUrl: String, database: Option[DatabaseConfig])

object ConfigRunner extends App with StrictLogging {

  logger.info(s"${Config.config} loaded!")

}

object Config extends StrictLogging {

  val environment = Option(System.getProperty("env"))

  logger.info(s"Loading config for $environment ...")

  private val configLoaded = environment.fold(ConfigFactory.load()) { fileToLoad =>
    ConfigFactory.load(s"$fileToLoad.conf")
  }
  val config = Config(configLoaded.getString("homePageUrl"), configLoaded.as[Option[DatabaseConfig]]("db"))

  config.database.foreach { database =>
    logger.info(s"Cleaning data ...")
    InitData.cleanData(database)
  }

  logger.info(s"Creating web driver pool")
  val webDriverPool = WebDriverPool.pool()
  Runtime.getRuntime.addShutdownHook(new Thread {
    override def run(): Unit = {
      Try {
        logger.debug("Closing pool of chrome drivers")
        webDriverPool.close()
        logger.debug("Pool of chrome drivers closed!")
      }.recover {
        case e: Exception =>
          logger.error(s"Couldn't close the driver: ${e.getMessage}", e)
      }
    }
  })
  logger.info("Config done!")
}
