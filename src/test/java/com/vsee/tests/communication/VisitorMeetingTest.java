package com.vsee.tests.communication;

import com.vsee.models.CommTestData;
import com.vsee.pages.shared.CallOptionsPanel;
import com.vsee.pages.shared.ConferenceRoomPage;
import com.vsee.pages.visitor.RegisterRoomPage;
import com.vsee.utils.Config;
import com.vsee.utils.Constants;
import com.vsee.utils.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.vsee.tests.BaseTest;

import java.util.List;

public class VisitorMeetingTest extends BaseTest {

    private RegisterRoomPage registerRoom;
    private CallOptionsPanel callOptionsPanel;
    private ConferenceRoomPage conferenceRoomPage;
    private CommTestData commTestData;

    @Parameters("testDataPath")
    @BeforeTest()
    public void setUp(String testDataPath) {
        this.registerRoom = new RegisterRoomPage(driver);
        this.callOptionsPanel = new CallOptionsPanel(driver);
        this.conferenceRoomPage = new ConferenceRoomPage(driver);

        commTestData = JsonUtil.getTestData(testDataPath, CommTestData.class);
    }

    @Test
    public void shouldAllowVisitorJoinsWaitingRoomAndReceivesChat() {

        registerRoom.goTo(Config.get(Constants.VISITOR_ROOM_URL));
        registerRoom.registerToEnterRoom(commTestData.visitorName(), commTestData.reasonForVisit());

        callOptionsPanel.isLoaded();
        callOptionsPanel.selectInBrowserCallingOption();

        conferenceRoomPage.isLoaded();
        conferenceRoomPage.clickJoinNow();

        List<String> actualReceivedMessages = conferenceRoomPage.getListOfReceivedMessage();
        Assert.assertEquals(actualReceivedMessages.size(), 1, "Incorrect number of received message, expected 1 but got " + actualReceivedMessages.size());
        Assert.assertEquals(actualReceivedMessages.getFirst(), commTestData.commMessage(), String.format("Incorrect received message content, expected: %s but got: %s",commTestData.commMessage(), actualReceivedMessages.getFirst()));
        conferenceRoomPage.clickOnHangUpButton();
    }
}
