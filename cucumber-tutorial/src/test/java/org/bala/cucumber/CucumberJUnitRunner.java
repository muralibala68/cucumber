package org.bala.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
//        plugin = "json:target/cucumber-report.json"
        plugin = {"pretty",
                  "html:target/cucumber"},
        glue = {"classpath:org.bala.cucumber_internal",
                "classpath:org.bala.cucumber"},
        features = {"src/test/resources"},
        tags = "@smoke"
)
public class CucumberJUnitRunner {
}
