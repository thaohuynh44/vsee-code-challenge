package com.vsee.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    protected abstract boolean isLoaded();

    public void goTo(String url) {
        this.driver.get(url);
    }

    protected void waitAndInputText(WebElement element, String text, Keys... extraKeys) {
        WebElement visibleElement = this.wait.until(ExpectedConditions.visibilityOf(element));
        visibleElement.clear();
        visibleElement.sendKeys(text);

        if (extraKeys != null && extraKeys.length > 0) {
            for (Keys key : extraKeys) {
                visibleElement.sendKeys(key);
            }
        }
    }

    protected void waitAndClick(WebElement element) {
        this.wait.until(ExpectedConditions.visibilityOf(element))
                .click();
    }

    protected boolean waitUntilAnyElementVisible(List<WebElement> elements, int timeoutInSec) {
        WebDriverWait _wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSec));
        try {
            return _wait.until(driver -> {
                for(WebElement element: elements) {
                    try {
                        if(element.isDisplayed()) {
                            return true;
                        }
                    } catch (Exception ignored) {}
                    return false;
                }
                return false;
            });
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void waitAndSwitchToFrame(WebElement iframeElement) {
        this.wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeElement))
                .switchTo().frame(iframeElement);
    }

    protected List<String> getInnerTexts(List<WebElement> elements) {
        return this.wait.until(ExpectedConditions.visibilityOfAllElements(elements))
                .stream().map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
