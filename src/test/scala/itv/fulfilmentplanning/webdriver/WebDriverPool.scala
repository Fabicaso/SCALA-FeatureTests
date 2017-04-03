package itv.fulfilmentplanning.webdriver

import com.typesafe.scalalogging.StrictLogging
import org.apache.commons.pool.PoolableObjectFactory
import org.apache.commons.pool.impl.GenericObjectPool
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}

import scala.util.Try

object WebDriverPool extends StrictLogging {

  logger.info(s"Init web driver pool ...")

  private val chromeOptions = {
    val chromeOptions = new ChromeOptions()
    chromeOptions.addArguments("test-type")
    chromeOptions.addArguments("start-maximized")
    chromeOptions.addArguments("--js-flags=--expose-gc")
    chromeOptions.addArguments("--enable-precise-memory-info")
    chromeOptions.addArguments("--disable-popup-blocking")
    chromeOptions.addArguments("--disable-default-apps")
    chromeOptions.addArguments("test-type=browser")
    chromeOptions.addArguments("disable-infobars")
    chromeOptions
  }

  val pool = new GenericObjectPool[WebDriver](new PoolableObjectFactory[WebDriver] {
    override def destroyObject(obj: WebDriver): Unit = {
      Try {
        logger.debug("Closing chrome driver")
        obj.quit()
        logger.debug("Chrome driver closed!")
      }.recover {
        case e: Exception =>
          logger.error(s"Couldn't close the driver: ${e.getMessage}", e)
      }
    }

    override def validateObject(obj: WebDriver): Boolean = true

    override def activateObject(obj: WebDriver): Unit = ()

    override def passivateObject(obj: WebDriver): Unit = ()

    override def makeObject(): WebDriver = {
      logger.info("Creating web driver ...")
      new ChromeDriver(chromeOptions)
    }
  })
  pool.setMaxActive(2)
  pool.setMaxIdle(-1)

  Runtime.getRuntime.addShutdownHook(new Thread {
    override def run(): Unit = {
      Try {
        logger.debug("Closing pool of chrome drivers")
        pool.close()
        logger.debug("Pool of chrome drivers closed!")
      }.recover {
        case e: Exception =>
          logger.error(s"Couldn't close the driver: ${e.getMessage}", e)
      }
    }
  })

}
