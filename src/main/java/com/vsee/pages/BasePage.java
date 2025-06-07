package com.vsee.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public abstract boolean isLoaded();

    public void goTo(String url) {
        this.driver.get(url);
    }

    public void waitAndInputText(WebElement element, String text) {
        this.wait.until(ExpectedConditions.visibilityOf(element))
                .sendKeys(text);
    }

    public void waitAndClick(WebElement element) {
        this.wait.until(ExpectedConditions.visibilityOf(element))
                .click();
    }
}
