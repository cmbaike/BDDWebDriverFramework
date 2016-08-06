package com.techboy.selenium.config;

import java.io.IOException;


interface BrowserService <T> {

    T getFirefoxCapabilities() throws IOException;
    T getChromeCapabilities();
    T getIECapabilities();

}
