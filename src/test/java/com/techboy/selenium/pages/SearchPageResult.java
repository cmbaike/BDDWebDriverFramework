package com.techboy.selenium.pages;

import com.techboy.selenium.beanconfig.PageObjectBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Christopher on 06/12/2015.
 */

public class SearchPageResult extends PageObjectBase {

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

    public Search search() {
        return new Search();
    }

    @Override
    protected void load() {
    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getTitle().contains(this.query));
    }

    @FindBy(css = ".r>a")
    List<WebElement> displayResult;

}
