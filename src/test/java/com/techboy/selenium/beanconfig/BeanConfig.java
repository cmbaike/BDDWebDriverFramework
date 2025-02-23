package com.techboy.selenium.beanconfig;

import com.techboy.selenium.browserdriver.BrowserDriverExtended;
import com.techboy.selenium.config.BrowserCapabilities;
import com.techboy.selenium.config.TryFunction;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * {@link} BeanConfig config class for all bean creation
 */
@PropertySource({"classpath:app.properties"})
@Configuration
public class BeanConfig {

    @Autowired
    private Environment environment;

    @Autowired(required = false)
    private URL seleniumGridURL;

    private static final Logger LOG = LoggerFactory.getLogger(BeanConfig.class);
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");

    @Value("${browser}")
    private String browser;

    @Value("${remote}")
    private String remote;

    @Value("${gridURL}")
    private String gridURL;

    @Value("${desiredBrowserVersion}")
    private String desiredBrowserVersion;

    @Value("${desiredPlatform}")
    private String desiredPlatform;


    /**
     * @link Initialize system path variables for browsers
     */
    @PostConstruct
    public void getEnvironmentInfo() throws IOException {

        LOG.info(" ");
        LOG.info("Current Operating System: " + operatingSystem);
        LOG.info("Current Architecture: " + systemArchitecture);
        LOG.info("Current Browser Selection: " + browser);
        LOG.info("Use RemoteWebDriver: " + remote);
        LOG.info(" ");

    }

    @PostConstruct
    public void setDriverPath() throws IOException {

        TryFunction<String, String> path = location -> new File(".").getCanonicalPath()+"/src/test/resources/"+location;

        switch (browser.toUpperCase()){
            case "CHROME":
        if (operatingSystem.contains("WINDOWS")) {
            System.setProperty("webdriver.chrome.driver",path.apply("selenium_browser_drivers/windowsChromedriver/chromedriver.exe"));
        } else if (operatingSystem.contains("MAC")) {
            System.setProperty("webdriver.chrome.driver",path.apply("selenium_browser_drivers/macChromedriver/chromedriver"));
        } else if (operatingSystem.contains("LINUX")) {
            System.setProperty("webdriver.chrome.driver",path.apply("selenium_browser_drivers/linuxChromedriver/chromedriver"));
        }
                break;
            case "IE":
                System.setProperty("webdriver.ie.driver",path.apply("selenium_browser_drivers/windowsIEdriver/IEDriverServer.exe"));
                break;
    }
        }

    /**
     * @link internetExplorer bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.IECondition.class)
    @Autowired
    public BrowserDriverExtended.InternetExplorerDriverExtended internetExplorer(DesiredCapabilities capabilities) {
        return new BrowserDriverExtended.InternetExplorerDriverExtended(capabilities);
    }

    /**
     * @link firefox bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.DefaultFirefoxCondition.class)
    @Autowired
    public BrowserDriverExtended.FirefoxDriverExtended firefox(DesiredCapabilities capabilities) {
        return new BrowserDriverExtended.FirefoxDriverExtended(capabilities);
    }

    /**
     * @link Chrome bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.ChromeCondition.class)
    @Autowired
    public BrowserDriverExtended.ChromeDriverExtended chrome(DesiredCapabilities capabilities) {
        return new BrowserDriverExtended.ChromeDriverExtended(capabilities);
    }

   @Bean
   @Conditional(BeanConfig.gridURLCondition.class)
    public URL seleniumGridURL() throws MalformedURLException {
       return new URL(environment.getProperty("gridURL"));
    }

    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.RemoteWebDriverCondition.class)
    @Autowired
    public BrowserDriverExtended.RemoteWebDriverExtended remote(DesiredCapabilities capabilities) throws MalformedURLException {
        String desiredPlatform=environment.getProperty("desiredPlatform");
        String desiredBrowserVersion=environment.getProperty("desiredBrowserVersion");
        if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
            capabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
        }
        if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
            capabilities.setVersion(desiredBrowserVersion);
        }
        return new BrowserDriverExtended.RemoteWebDriverExtended(seleniumGridURL, capabilities);
    }


    @Bean
    @Conditional(BeanConfig.FirefoxCapabilityCondition.class)
    public DesiredCapabilities firefoxDesiredCapabilities() throws IOException {
        return BrowserCapabilities.newInstance().getFirefoxCapabilities();
    }

    @Bean
    @Conditional(BeanConfig.ChromeCapabilityCondition.class)
    public DesiredCapabilities chromeDesiredCapabilities(){
        return BrowserCapabilities.newInstance().getChromeCapabilities();
    }

    @Bean
    @Conditional(BeanConfig.IECapabilityCondition.class)
    public DesiredCapabilities ieDesiredCapabilities(){
        return BrowserCapabilities.newInstance().getIECapabilities();
    }

    private static class FirefoxCapabilityCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            return browser.equalsIgnoreCase("firefox")||browser.isEmpty();
        }
    }

    private static class ChromeCapabilityCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            return browser.equalsIgnoreCase("chrome");
        }
    }

    private static class IECapabilityCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            return browser.equalsIgnoreCase("IE");
        }
    }

    /**
     * @link Condition for creating firefox browser bean as default
     */
    private static class DefaultFirefoxCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            List<Boolean>firefoxSelector=new ArrayList<>();
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            String remote=env.getProperty("remote", "false");
            firefoxSelector.add(browser.equalsIgnoreCase("firefox") || browser.isEmpty());
            firefoxSelector.add(remote.equalsIgnoreCase("false") || remote.isEmpty());
            return firefoxSelector.get(0)&&firefoxSelector.get(1);
        }
    }

    /**
     * @link Condition for creating chrome browser bean
     */
    private static class ChromeCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            List<Boolean>chromeSelector=new ArrayList<>();
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            String remote=env.getProperty("remote", "false");
            chromeSelector.add(browser.equalsIgnoreCase("chrome"));
            chromeSelector.add(remote.equalsIgnoreCase("false") || remote.isEmpty());
            return chromeSelector.get(0)&&chromeSelector.get(1);
        }
    }

    /**
     * @link Condition for creating IE browser bean
     */

    private static class IECondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            List<Boolean>ieSelector=new ArrayList<>();
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            String remote=env.getProperty("remote", "false");
            ieSelector.add(browser.equalsIgnoreCase("IE"));
            ieSelector.add(remote.equalsIgnoreCase("false") || remote.isEmpty());
            return ieSelector.get(0)&&ieSelector.get(1);
        }
    }

    private static class RemoteWebDriverCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env=context.getEnvironment();
            String remote=env.getProperty("remote", "false");
            return remote.equalsIgnoreCase("true");
        }
    }

    private static class gridURLCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env=context.getEnvironment();
            return !env.getProperty("gridURL").isEmpty();
        }
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}






