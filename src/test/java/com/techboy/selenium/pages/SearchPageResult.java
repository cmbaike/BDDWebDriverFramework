package com.techboy.selenium.pages;

import com.techboy.selenium.beanconfig.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Christopher on 06/12/2015.
 */
@Component
@Scope("cucumber-glue")
@PageObject
public class SearchPageResult extends LoadableComponent<SearchPageResult>{

    @Autowired
    private WebDriver driver;

    private String query;

    // Return all the search result after search in an Array
    public List<String> getSearchResultLink() {

        List<String> searchResult_content = new ArrayList<String>();

        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(displayResult));

        List<WebElement> searchResultList = driver.findElement(By.id("search")).findElements(By.tagName("a"));

        for (WebElement item : searchResultList)
            searchResult_content.add(item.getAttribute("href"));
        return searchResult_content;

    }

    public Search search(){
        return  new Search();
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getTitle().contains(this.query));
    }

    @FindBy(css=".r>a")
    List <WebElement> displayResult;
}
