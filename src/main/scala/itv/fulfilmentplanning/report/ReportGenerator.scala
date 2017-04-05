package itv.fulfilmentplanning.report

import java.io.{File, FilenameFilter}

import com.typesafe.scalalogging.StrictLogging
import net.masterthought.cucumber.{Configuration, ReportBuilder}

import scala.util.Try

object ReportGenerator extends StrictLogging {
  import scala.collection.JavaConversions._

  private def reportConfiguration(outputFolder: File): Configuration = {
    val configuration = new Configuration(outputFolder, "Fulfilment planning feature tests")
    configuration.setParallelTesting(true)
    configuration.setRunWithJenkins(true)
    configuration.setBuildNumber("")
    configuration
  }

  def generateReport() =
    Try {
      val target       = "./target"
      val targetFolder = new File(s"$target/cucumber-html-reports")
      val files = Option(
        targetFolder
          .list(new FilenameFilter() {
            def accept(dir: File, name: String): Boolean = name.endsWith(".json")
          })
      ).fold(List.empty[String])(_.toList)
        .map(file => s"${s"$target/cucumber-html-reports"}/$file")
      logger.info(s"Reading the json files: $files")
      val reportBuilder = new ReportBuilder(files, reportConfiguration(new File(target)))
      val result        = reportBuilder.generateReports()
      logger.info(s"Report generated in $result")

    }.recover {
      case e: Exception =>
        logger.error(s"It could not generate the report", e)
    }
}

object ReportGeneratorRunner extends App {
  ReportGenerator.generateReport()
}
