package com.vsee.pages.shared;

import com.vsee.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CallOptionsPanel extends BasePage {

    @FindBy(css = ".call_options[data-link*='inbrowser_calling']")
    private WebElement inBrowserCallingOption;

    @FindBy(css = ".call_options[data-mobile-app-link]")
    private WebElement vseeMessageAppOption;

    public CallOptionsPanel(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isLoaded() {
        this.wait.until(ExpectedConditions.visibilityOf(inBrowserCallingOption));
        return this.inBrowserCallingOption.isDisplayed();
    }

    public void selectInBrowserCallingOption() {
        this.waitAndClick(inBrowserCallingOption);
    }

    public void selectVSeeMessageAppOption() {
        this.waitAndClick(vseeMessageAppOption);
    }
}
