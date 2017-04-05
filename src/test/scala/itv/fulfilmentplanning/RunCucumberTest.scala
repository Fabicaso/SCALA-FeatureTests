package itv.fulfilmentplanning

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

object RunCucumberTest {}

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("classpath:features"),
  glue = Array("classpath:steps"),
  plugin = Array("pretty", "json:target/cucumber-html-reports/first-json-report.json"),
  tags = Array("@currentRequests", "~@Ignore")
)
class FirstRunCucumberTest {}

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("classpath:features"),
  glue = Array("classpath:steps"),
  plugin = Array("pretty", "json:target/cucumber-html-reports/second-json-report.json"),
  tags = Array("@licence", "~@Ignore")
)
class SecondRunCucumberTest {}
