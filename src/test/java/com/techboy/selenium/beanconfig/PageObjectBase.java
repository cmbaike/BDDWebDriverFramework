package com.techboy.selenium.beanconfig;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class PageObjectBase<T extends LoadableComponent<T>> extends LoadableComponent<T> {

    @Autowired
    protected WebDriver driver;

    @Autowired
    protected Environment environment;

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, this);
    }

}
