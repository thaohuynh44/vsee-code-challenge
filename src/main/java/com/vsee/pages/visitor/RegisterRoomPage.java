package com.vsee.pages.visitor;

import com.vsee.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class RegisterRoomPage extends BasePage {

    @FindBy(css = ".page-title h3")
    private WebElement welcomeLabel;

    @FindBy(css = "input[name='first_name']")
    private WebElement nameInput;

    @FindBy(css = "textarea[name='reason_for_visit']")
    private WebElement reasonForVisitInput;

    @FindBy(css = "input[name='consent']")
    private WebElement consentCheckbox;

    @FindBy(css = "input.btn[type='submit']")
    private WebElement enterWaitingRoomButton;

    @FindBy(css = ".menu-item-provider-login a")
    private WebElement forProvidersLink;

    public RegisterRoomPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isLoaded() {
        this.wait.until(ExpectedConditions.visibilityOf(welcomeLabel));
        return this.welcomeLabel.isDisplayed();
    }

    public void enterName(String name) {
        this.waitAndInputText(nameInput, name);
    }

    public void enterReasonForVisit(String reason) {
        this.waitAndInputText(reasonForVisitInput, reason);
    }

    /**
     * Check the consent checkbox only if it's not already checked
     */
    public void checkConsentCheckbox() {
        if(!consentCheckbox.isSelected()) {
            this.waitAndClick(consentCheckbox);
        }
    }

    public void clickEnterWaitingRoom() {
        this.waitAndClick(enterWaitingRoomButton);
    }

    public void registerToEnterRoom(String visitorName, String reasonForVisit) {
        enterName(visitorName);
        enterReasonForVisit(reasonForVisit);
        checkConsentCheckbox();
        clickEnterWaitingRoom();
    }

    public void clickOnForProviders() {
        this.waitAndClick(forProvidersLink);
    }

}
