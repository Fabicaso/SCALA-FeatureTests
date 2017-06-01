name := "fulfilmentplanning-featuretests"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val cucumberVersion: String = "1.2.5"
  Seq(
    "com.typesafe"               % "config"             % "1.3.1" % "test",
    "org.scalactic"              %% "scalactic"         % "3.0.1",
    "org.scalatest"              %% "scalatest"         % "3.0.1" % "test",
    "info.cukes"                 %% "cucumber-scala"    % cucumberVersion % "test",
    "info.cukes"                 % "cucumber-junit"     % cucumberVersion % "test",
    "com.novocode"               % "junit-interface"    % "0.11" % "test",
    "org.seleniumhq.selenium"    % "selenium-java"      % "3.4.0" % "test",
    "com.typesafe.scala-logging" %% "scala-logging"     % "3.5.0",
    "ch.qos.logback"             % "logback-classic"    % "1.2.2",
    "commons-pool"               % "commons-pool"       % "1.6",
    "org.postgresql"             % "postgresql"         % "9.4-1201-jdbc41",
    "com.iheart"                 %% "ficus"             % "1.1.3",
    "net.masterthought"          % "cucumber-reporting" % "3.6.0"
  )
}

parallelExecution in Test := true

resolvers ++= Seq(
  "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
  Resolver.jcenterRepo //com.iheart:ficus
)
