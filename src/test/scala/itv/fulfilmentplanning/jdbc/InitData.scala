package itv.fulfilmentplanning.jdbc

import java.io.InputStream
import java.sql.{Connection, DriverManager}

import com.typesafe.scalalogging.StrictLogging

import scala.io.Source

object InitData extends StrictLogging {

  case class DatabaseConfig(server: String, database: String, username: String, password: String)

  def cleanData(database: DatabaseConfig): Unit = {
    DriverManager.registerDriver(new org.postgresql.Driver())

    logger.debug(s"Create database connection")
    val connection = DriverManager.getConnection(
      s"jdbc:postgresql://${database.server}/${database.database}?user=${database.username}&password=${database.password}")

    logger.debug(s"Read the script")
    val is = this.getClass.getResourceAsStream("/delete_licence.sql")
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
