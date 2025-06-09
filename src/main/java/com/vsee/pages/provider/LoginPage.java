package com.vsee.pages.provider;

import com.vsee.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(id = "AppUserUsername")
    private WebElement emailInput;

    @FindBy(id = "AppUserPassword")
    private WebElement passwordInput;

    @FindBy(id = "btnSignIn")
    private WebElement signInButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        this.waitAndInputText(emailInput, email);
    }

    public void enterPassword(String password) {
        this.waitAndInputText(passwordInput, password);
    }

    public void clickSignIn() {
        this.waitAndClick(signInButton);
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }

    public boolean isLoaded() {
        this.wait.until(ExpectedConditions.visibilityOf(signInButton));
        return this.signInButton.isDisplayed();
    }
}
