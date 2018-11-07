package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalStudents.ALICE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.commons.events.ui.StudentPanelSelectionChangedEvent;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class BrowserPanelTest extends GuiUnitTest {

    private StudentPanelSelectionChangedEvent selectionChangedEventStub;
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new StudentPanelSelectionChangedEvent(ALICE);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {

        // default view
        Student expectedDefaultStudent = new StudentBuilder().build();
        assertEquals(expectedDefaultStudent, browserPanelHandle.getLoadedStudent());

        // associated page of a student
        postNow(selectionChangedEventStub);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertTrue(ALICE.isSameStudent(browserPanelHandle.getLoadedStudent()));
    }
}
