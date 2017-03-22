name := "fulfilmentplanning-featuretests"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val cucumberVersion: String = "1.2.5"
  Seq(
    "com.typesafe"               % "config"          % "1.3.1" % "test",
    "org.scalactic"              %% "scalactic"      % "3.0.1",
    "org.scalatest"              %% "scalatest"      % "3.0.1" % "test",
    "info.cukes"                 %% "cucumber-scala" % cucumberVersion % "test",
    "info.cukes"                 % "cucumber-junit"  % cucumberVersion % "test",
    "com.novocode"               % "junit-interface" % "0.11" % "test",
    "org.seleniumhq.selenium"    % "selenium-java"   % "2.53.0" % "test",
    "com.typesafe.scala-logging" %% "scala-logging"  % "3.5.0",
    "ch.qos.logback"             % "logback-classic" % "1.2.2"
  )
}

parallelExecution in Test := true
