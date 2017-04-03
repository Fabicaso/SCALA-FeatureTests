package itv.fulfilmentplanning.jdbc

import java.io.InputStream
import java.sql.{Connection, DriverManager}

import com.typesafe.scalalogging.StrictLogging

import scala.io.Source

object InitData extends App with StrictLogging {

  case class UrlConnection(server: String, database: String, user: String, password: String)

  val urlConfig: Option[UrlConnection] = Some(
    UrlConnection("db-craft.stg.fp.itv.com", "craftqa", "craft", "craftpassword"))

  urlConfig.foreach { url =>
    DriverManager.registerDriver(new org.postgresql.Driver())

    val connection = DriverManager.getConnection(
      s"jdbc:postgresql://${url.server}/${url.database}?user=${url.user}&password=${url.password}")
    val is = Thread.currentThread().getClass.getResourceAsStream("/delete_licence.sql")

    executeScript(connection, is)

  }

  private def executeScript(connection: Connection, is: InputStream) = {

    logger.info(s"Executing script")
    val filter = Source
      .fromInputStream(is)
      .getLines()
      .map(_.trim)
      .filter(s => !s.startsWith("--"))
      .filter(_.nonEmpty)
    filter
      .foreach { line =>
        val st = connection.createStatement()
        try {
          logger.info(s"Executing $line ...")
          st.execute(line)
        } catch {
          case e: Exception =>
            logger.error(s"Unable to execute: $line because ${e.getMessage}", e)
        } finally {
          st.close()
        }
      }
  }
}
