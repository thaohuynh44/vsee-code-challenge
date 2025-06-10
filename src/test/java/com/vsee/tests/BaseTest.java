package com.vsee.tests;

import com.vsee.utils.Config;
import com.vsee.utils.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;

    @BeforeSuite
    public void setupConfig() {
        Config.initialize();
    }

    @BeforeTest
    public void setDriver() throws MalformedURLException {
        this.driver = Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED)) ? getRemoteDriver() : getLocalDriver();
        driver.manage().window().maximize();
    }

    private WebDriver getLocalDriver() {
        ChromeOptions options = getChromeOptions();

        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(options);
    }

    private WebDriver getRemoteDriver() throws MalformedURLException {
        Capabilities capabilities = getChromeOptions();
        if(Constants.FIREFOX.equalsIgnoreCase(Config.get(Constants.BROWSER))) {
            capabilities = new FirefoxOptions();
        }

        String urlFormat = Config.get(Constants.GRID_URL_FORMAT);
        String hubHost = Config.get(Constants.GRID_HUB_HOST);
        String url = String.format(urlFormat, hubHost);
        log.info("Grid URL: {}", url);

        return new RemoteWebDriver(new URL(url), capabilities);
    }

    protected ChromeOptions getChromeOptions() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        prefs.put("profile.default_content_setting_values.geolocation", 1);
        prefs.put("profile.default_content_setting_values.notifications", 1);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        return options;
    }

    @AfterTest
    public void tearDown() {
        this.driver.quit();
    }
}
