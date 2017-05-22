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
  tags = Array("@currentRequests, @sendRequest", "~@Ignore")
)
class FirstRunCucumberTest {}

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("classpath:features"),
  glue = Array("classpath:steps"),
  plugin = Array("pretty", "json:target/cucumber-html-reports/second-json-report.json"),
  tags = Array("@licence, @assetsstatus, @fulfilment", "~@Ignore")
)
class SecondRunCucumberTest {}

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("classpath:features"),
  glue = Array("classpath:steps"),
  plugin = Array("pretty", "json:target/cucumber-html-reports/third-json-report.json"),
  tags = Array("@licenceStatus", "~@Ignore")
)
class ThirdRunCucumberTest {}

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("classpath:features"),
  glue = Array("classpath:steps"),
  plugin = Array("pretty", "json:target/cucumber-html-reports/fourth-json-report.json"),
  tags = Array("@assetsdates, @display", "~@Ignore")
)
class FourthRunCucumberTest {}
