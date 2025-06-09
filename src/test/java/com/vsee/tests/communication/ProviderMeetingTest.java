package com.vsee.tests.communication;

import com.vsee.pages.provider.DashboardPage;
import com.vsee.pages.provider.LoginPage;
import com.vsee.pages.shared.CallOptionsPanel;
import com.vsee.pages.shared.ConferenceRoomPage;
import com.vsee.utils.Config;
import com.vsee.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.vsee.tests.BaseTest;

public class ProviderMeetingTest extends BaseTest {

    private LoginPage providerLoginPage;
    private DashboardPage dashboardPage;
    private CallOptionsPanel callOptionsPanel;
    private ConferenceRoomPage conferenceRoomPage;

    @BeforeTest()
    public void setUpPages() {
        this.providerLoginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.callOptionsPanel = new CallOptionsPanel(driver);
        this.conferenceRoomPage = new ConferenceRoomPage(driver);
    }

    @Test
    public void shouldAllowProviderStartCallAndSendMessageToPatient() {
        providerLoginPage.goTo(Config.get(Constants.PROVIDER_LOGIN_URL));
        providerLoginPage.login("huynhngocthao989@gmail.com", "@veryTest123");

        dashboardPage.isLoaded();
        Assert.assertEquals(this.driver.getCurrentUrl(), Config.get(Constants.PROVIDER_DASHBOARD_URL));
        dashboardPage.getNumberOfReadyForAppointment();

        Assert.assertTrue(dashboardPage.waitForHavingReadyVisitor(120));
        dashboardPage.startCallWithVisitorByName("thaotest123");

        callOptionsPanel.selectInBrowserCallingOption();
        conferenceRoomPage.isLoaded();
        conferenceRoomPage.clickJoinNow();
        conferenceRoomPage.clickOnToolboxMessageButton();

        conferenceRoomPage.enterMessageIntoChatbox("Hello visitor, this is a test message");

        this.driver.quit();
    }


}
