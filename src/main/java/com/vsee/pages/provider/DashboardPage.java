package com.vsee.pages.provider;

import com.vsee.pages.BasePage;
import com.vsee.utils.RegexUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DashboardPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(DashboardPage.class);

    @FindBy(xpath = "(//h4[contains(@class, 'visit-group-title')])[1]")
    private WebElement readyForVisitsLabel;

    @FindBy(xpath = "(//div[contains(@class, 'panel-body pd-0')])[1]")
    private WebElement readyForVisitsSection;

    @FindBy(xpath = "(//h4[contains(@class, 'visit-group-title')])[2]")
    private WebElement gettingReadyLabel;

    @FindBy(xpath = "(//h4[contains(@class, 'visit-group-title')])[3]")
    private WebElement inProgressLabel;

    @FindBy(xpath = "(//h4[contains(@class, 'visit-group-title')])[4]")
    private WebElement recentLabel;

    @FindBy(css = "a.call_button")
    private List<WebElement> startCallButton;

    @FindBy(css = "[data-visit-id] .cell-info a")
    private List<WebElement> readyVisitorsNameTxt;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isLoaded() {
        this.wait.until(ExpectedConditions.visibilityOf(readyForVisitsLabel));
        return this.readyForVisitsLabel.isDisplayed();
    }

    public String getNumberOfReadyForAppointment() {
        String labelText = this.readyForVisitsLabel.getText();
        String foundNumber = RegexUtils.extractNumberInParentheses(labelText);

        log.info("Found number in readyForAppointmentLabel: {}", foundNumber);
        return foundNumber;
    }

    public void startCallWithRandomReadyVisitor() {
        int random = ThreadLocalRandom.current().nextInt(0, startCallButton.size());
        WebElement selectedCallButton = startCallButton.get(random);
        String visitorName = readyVisitorsNameTxt.get(random).getText();

        log.info("Starting a call with visitor: {}", visitorName);

        this.waitAndClick(selectedCallButton);
    }

    public void startCallWithVisitorByName(String visitorName) {
        for (int i = 0; i < readyVisitorsNameTxt.size(); i++) {
            WebElement currentVisitorName = readyVisitorsNameTxt.get(i);
            if (currentVisitorName.getText().equals(visitorName)) {
                log.info("Found ready visitor with name: {}, starting call", visitorName);

                this.waitAndClick(startCallButton.get(i));
                return;
            }
        }
    }

    public boolean waitForHavingReadyVisitor(int timeoutInSec) {
        boolean isHavingVisitor = this.waitUntilAnyElementVisible(readyVisitorsNameTxt, timeoutInSec);

        if(!isHavingVisitor) {
            log.info("Timed out after {} seconds while waiting for a visitor to join.", timeoutInSec);
        }

        return isHavingVisitor;
    }
}
