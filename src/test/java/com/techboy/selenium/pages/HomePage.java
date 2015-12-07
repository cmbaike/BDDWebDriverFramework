package com.techboy.selenium.pages;

import com.techboy.selenium.beanconfig.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertTrue;

/**
 * Created by Christopher on 06/12/2015.
 */

@Scope("cucumber-glue")
@PageObject
@Component
public class HomePage extends LoadableComponent<HomePage> {

    @Autowired
    private WebDriver driver;

    @Autowired
    private Environment environment;

    static String title = "Google";

    @Override
    protected void load() {
        driver.get(environment.getProperty("Homepage_url"));
    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getTitle().contains(title));
    }

    public Search search(){
        return  new Search();
    }
}
