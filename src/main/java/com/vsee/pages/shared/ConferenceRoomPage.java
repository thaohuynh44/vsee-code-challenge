package com.vsee.pages.shared;

import com.vsee.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ConferenceRoomPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(ConferenceRoomPage.class);

    @FindBy(css = "[aria-label='Join Now']")
    private WebElement joinNowButton;

    @FindBy(css = "[aria-label='Open chat']")
    private WebElement toolBoxMessageButton;

    @FindBy(css = "#webchat-container [type='textarea']")
    private WebElement chatboxMessageInput;

    @FindBy(css = ".webchat-message-bubble")
    private List<WebElement> chatboxMessages;

    @FindBy(css = "button[data-chatroom]")
    private WebElement chatRoomButton;

    @FindBy(css = ".hangup-button")
    private WebElement hangUpButton;

    public ConferenceRoomPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isLoaded() {
       return this.wait.until(ExpectedConditions.urlContains("calling"));
    }

    public void clickJoinNow() {
        switchToConferenceIframe();
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label='Join Now']")))
                .click();
    }

    public void clickOnToolboxMessageButton() {
        this.waitAndClick(toolBoxMessageButton);
    }

    public void enterMessageIntoChatbox(String message) {
        this.driver.switchTo().defaultContent();

        this.waitAndInputText(chatboxMessageInput, message, Keys.ENTER);
    }

    public List<String> getListOfReceivedMessage() {
        this.driver.switchTo().defaultContent();

        List<String> receivedMsg = this.getInnerTexts(chatboxMessages);
        log.info("List of received message: {}", receivedMsg);

        return receivedMsg;
    }

    public void clickOnChatRoomButton() {
        this.driver.switchTo().defaultContent();
        this.waitAndClick(chatRoomButton);
    }

    public void clickOnHangUpButton() {
        switchToConferenceIframe();
        this.waitAndClick(hangUpButton);
    }

    private void switchToConferenceIframe() {
        this.driver.switchTo().frame("jitsiConferenceFrame0");
    }

}
