package com.techboy.selenium.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by Christopher on 06/12/2015.
 */

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, strict = true,
    tags = {"@smoke-test"},
    features = "src/test/resources/com.techboy.selenium.features/",
    format = {"html:target/cucumber"},
    glue = {"com/techboy/selenium/step_def"}

)
public class Runner {
}
