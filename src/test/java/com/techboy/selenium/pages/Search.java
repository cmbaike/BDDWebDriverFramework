package com.techboy.selenium.pages;

import com.techboy.selenium.beanconfig.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 * Created by Christopher on 06/12/2015.
 */

@Scope("cucumber-glue")
@PageObject
public class Search {

    @Autowired
    private WebDriver driver;

    @FindBy(name = "q")
    private WebElement searchfield;


    public SearchPageResult sendQuery(String query) {
          searchfield.clear();
          searchfield.sendKeys(query);
          searchfield.submit();
          return new SearchPageResult();
    }

}
