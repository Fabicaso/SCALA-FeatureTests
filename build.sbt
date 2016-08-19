name := "fulfilmentplanning-featuretests"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val cucumberVersion: String = "1.2.4"
  Seq(
    "com.typesafe" % "config" % "1.3.0" % "test",
    "org.scalactic" %% "scalactic" % "3.0.0",
    "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    "info.cukes" %% "cucumber-scala" % cucumberVersion % "test",
    "info.cukes" % "cucumber-junit" % cucumberVersion % "test",
    "com.novocode" % "junit-interface" % "0.11" % "test",
    "org.seleniumhq.selenium" % "selenium-java" % "2.53.0" % "test"
  )
}
