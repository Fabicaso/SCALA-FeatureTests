package itv.fulfilmentplanning

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("classpath:features"),
  glue = Array("classpath:steps"),
  plugin =
    Array("pretty", "html:target/cucumber-report", "junit:target/junit-report.xml", "json:target/json-report.json"),
  tags = Array("@login, @currentRequests, @licence", "~@Ignore")
)
class RunCucumberTest {}
