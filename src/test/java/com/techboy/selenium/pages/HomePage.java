package com.techboy.selenium.pages;

import com.techboy.selenium.beanconfig.PageObjectBase;
import org.springframework.context.annotation.Scope;

import static org.junit.Assert.assertTrue;

/**
 * Created by Christopher on 06/12/2015.
 */


public class HomePage extends PageObjectBase {

    @Override
    protected void load() {
        driver.get(environment.getProperty("Homepage_url"));
    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getTitle().contains(getTitle()));
    }

    public Search search() {
        return new Search();
    }

    public String getTitle() {
        return "Google";
    }
}
