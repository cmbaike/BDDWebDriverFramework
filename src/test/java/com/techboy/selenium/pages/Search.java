package com.techboy.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

/**
 * Created by Christopher on 06/12/2015.
 */

@Scope("cucumber-glue")
public class Search {


    @Autowired
    protected WebDriver driver;

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "q")
    private WebElement searchfield;

    public SearchPageResult sendQuery(String query) {
        searchfield.clear();
        searchfield.sendKeys(query);
        searchfield.submit();
        return new SearchPageResult();
    }




}
