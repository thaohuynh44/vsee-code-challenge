package com.vsee.tests.communication;

import com.vsee.pages.provider.DashboardPage;
import com.vsee.pages.provider.LoginPage;
import com.vsee.pages.shared.CallOptionsPanel;
import com.vsee.pages.shared.ConferenceRoomPage;
import com.vsee.pages.visitor.RegisterRoomPage;
import com.vsee.tests.BaseTest;
import com.vsee.utils.Config;
import com.vsee.utils.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class E2ETest extends BaseTest {

    WebDriver userADriver;
    WebDriver userBDriver;

    RegisterRoomPage visitorRegisterRoom;
    RegisterRoomPage providerRegisterRoom;
    CallOptionsPanel visitorCallOptionsPanel;
    CallOptionsPanel providerCallOptionsPanel;
    ConferenceRoomPage visitorConferenceRoomPage;
    ConferenceRoomPage providerConferenceRoomPage;
    LoginPage providerLoginPage;
    DashboardPage dashboardPage;


    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();

        userADriver = new ChromeDriver(getChromeOptions());
        userBDriver = new ChromeDriver(getChromeOptions());
    }

    @Test
    public void e2eProviderVisitorStartCallAndSendChatTest() throws InterruptedException {

        visitorRegisterRoom = new RegisterRoomPage(userADriver);
        visitorRegisterRoom.goTo(Config.get(Constants.VISITOR_ROOM_URL));
        visitorRegisterRoom.registerToEnterRoom("userA", "Testing meeting flow");

        visitorCallOptionsPanel = new CallOptionsPanel(userADriver);
        visitorCallOptionsPanel.isLoaded();
        visitorCallOptionsPanel.selectInBrowserCallingOption();

        visitorConferenceRoomPage = new ConferenceRoomPage(userADriver);
        visitorConferenceRoomPage.isLoaded();
        visitorConferenceRoomPage.clickJoinNow();

        providerRegisterRoom = new RegisterRoomPage(userBDriver);
        providerRegisterRoom.goTo(Config.get(Constants.VISITOR_ROOM_URL));
        providerRegisterRoom.clickOnForProviders();

        providerLoginPage = new LoginPage(userBDriver);
        providerLoginPage.isLoaded();
        providerLoginPage.login(Config.get(Constants.DEFAULT_PROVIDER_USR), Config.get(Constants.DEFAULT_PROVIDER_PWD));

        dashboardPage = new DashboardPage(userBDriver);
        dashboardPage.isLoaded();
        dashboardPage.startCallWithVisitorByName("userA");

        providerCallOptionsPanel = new CallOptionsPanel(userBDriver);
        providerCallOptionsPanel.selectInBrowserCallingOption();

        providerConferenceRoomPage = new ConferenceRoomPage(userBDriver);
        providerConferenceRoomPage.isLoaded();
        providerConferenceRoomPage.clickJoinNow();
        providerConferenceRoomPage.clickOnChatRoomButton();
        providerConferenceRoomPage.enterMessageIntoChatbox("This is the test message, send to userA");
        Thread.sleep(2000);

        List<String> actualReceivedMessages = visitorConferenceRoomPage.getListOfReceivedMessage();
        Assert.assertEquals(actualReceivedMessages.size(), 1, "Incorrect number of received message, expected 1 but got " + actualReceivedMessages.size());
        Assert.assertEquals(actualReceivedMessages.getFirst(), "This is the test message, send to userA", String.format("Incorrect received message content, expected: %s but got: %s","This is the test message, send to userA", actualReceivedMessages.getFirst()));
        providerConferenceRoomPage.clickOnHangUpButton();
        visitorConferenceRoomPage.clickOnHangUpButton();

        userADriver.quit();
        userBDriver.quit();
    }
}
