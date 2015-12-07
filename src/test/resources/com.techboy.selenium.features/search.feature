@smoke-test
Feature: Accelus Search
    AS A User
    I WANT TO perform a search using the google search engine for the term Thomson Reuters
    SO THAT I can investigate the returned search results

    Scenario: A user searches for Thomson Reuters
        Given I am on the google search page
        When I search for the term "Thomson Reuters"
        Then the Thomson Reuters company website link is returned within the results
