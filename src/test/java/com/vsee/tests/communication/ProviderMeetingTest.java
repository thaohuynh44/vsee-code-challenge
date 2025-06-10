package com.vsee.tests.communication;

import com.vsee.models.CommTestData;
import com.vsee.pages.provider.DashboardPage;
import com.vsee.pages.provider.LoginPage;
import com.vsee.pages.shared.CallOptionsPanel;
import com.vsee.pages.shared.ConferenceRoomPage;
import com.vsee.utils.Config;
import com.vsee.utils.Constants;
import com.vsee.utils.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.vsee.tests.BaseTest;


public class ProviderMeetingTest extends BaseTest {

    private LoginPage providerLoginPage;
    private DashboardPage dashboardPage;
    private CallOptionsPanel callOptionsPanel;
    private ConferenceRoomPage conferenceRoomPage;
    private CommTestData commTestData;


    @Parameters("testDataPath")
    @BeforeTest()
    public void setUp(String testDataPath) {
        this.providerLoginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.callOptionsPanel = new CallOptionsPanel(driver);
        this.conferenceRoomPage = new ConferenceRoomPage(driver);

        this.commTestData = JsonUtil.getTestData(testDataPath, CommTestData.class);
    }

    @Test
    public void shouldAllowProviderStartCallAndSendMessageToVisitor() throws InterruptedException {
        providerLoginPage.goTo(Config.get(Constants.PROVIDER_LOGIN_URL));
        providerLoginPage.login(Config.get(Constants.DEFAULT_PROVIDER_USR), Config.get(Constants.DEFAULT_PROVIDER_PWD));

        dashboardPage.isLoaded();
        Assert.assertEquals(this.driver.getCurrentUrl(), Config.get(Constants.PROVIDER_DASHBOARD_URL));
        dashboardPage.getNumberOfReadyForAppointment();

        Assert.assertTrue(dashboardPage.waitForHavingReadyVisitor(120));
        dashboardPage.startCallWithVisitorByName(commTestData.visitorName());

        callOptionsPanel.selectInBrowserCallingOption();
        conferenceRoomPage.isLoaded();
        conferenceRoomPage.clickJoinNow();
        conferenceRoomPage.clickOnToolboxMessageButton();

        conferenceRoomPage.enterMessageIntoChatbox(commTestData.commMessage());

        // Need to wait for the message to be synced up with visitor
        Thread.sleep(2000);
        conferenceRoomPage.clickOnHangUpButton();
    }

}
