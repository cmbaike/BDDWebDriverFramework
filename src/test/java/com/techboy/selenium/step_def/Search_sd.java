package com.techboy.selenium.step_def;

import com.techboy.selenium.beanconfig.BeanConfig;
import com.techboy.selenium.pages.HomePage;
import com.techboy.selenium.pages.Search;
import com.techboy.selenium.pages.SearchPageResult;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Christopher on 06/12/2015.
 */

@ContextConfiguration(classes={BeanConfig.class,SearchPageResult.class,HomePage.class,Search.class})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
public class Search_sd {

    @Autowired
    private HomePage homepage;

    @Autowired
    private SearchPageResult searchPageResult;

    @Autowired
    private Search search;


    @Autowired
    private Environment environment;


    @Given("^I am on the google search page$")
    public void navigateToSearch() throws Throwable {
        homepage.get();
    }

    @When("^I search for the term \"([^\"]*)\"$")
    public void I_search_for_the_term(String search_term) throws Throwable {
       search.sendQuery(search_term);

    }

    @Then("^the Thomson Reuters company website link is returned within the results$")
    public void the_Thomson_Reuters_company_website_link_is_returned_within_the_results()
        throws Throwable {

        List<String> searchResultList = searchPageResult.getSearchResultLink();
        assertTrue(searchResultList.contains(environment.getProperty("result_link")));
    }
}
