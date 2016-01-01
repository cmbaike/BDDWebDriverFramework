#BDDWebDriverFramework Using Spring, Cucumber and PageObject model and Javascript Sizzle implementation for advanced element location

A maven BDD Selenium framework that is built which allows Spring to manage dependency via dependency injection.

1. Open a terminal window/command prompt
2. Clone this project.
3. CD into project directory
4. mvn clean verify

_All specific driver executable dependencies are already installed in project directory

_Firefox will always launch with firebug installed in case you need to debug

_You can also use Sizzle selector in your project. Each browser object has been extended to support sizzle selectors

##What I Should do to run my test

- A webDriver object will be wired automatically using the @Autowired or @inject annotation
  
- Each page created for Homepage should be annotated with @component so spring can implicitly create a bean out of it

##Further Information

- -Dbrowser=firefox
- -Dbrowser=chrome
- -Dbrowser=ie

-You don't need to download the IEDriverServer, or chromedriver binaries, they are already added to the project directory

-You can specify a grid to connect to where you can choose your browser, browser version and platform

- -Dremote=true 
- -DseleniumGridURL=http://{username}:{accessKey}@ondemand.saucelabs.com:80/wd/hub 
- -Dplatform=xp 
- -Dbrowser=firefox 
- -DbrowserVersion=42

-You can also specify a proxy to use

- -DproxyEnabled=true
- -DproxyHost=localhost
- -DproxyPort=8080

-For more information and benefit on using sizzle ... Please visit this link https://github.com/jquery/sizzle/wiki

