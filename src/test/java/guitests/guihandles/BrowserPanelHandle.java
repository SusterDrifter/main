package guitests.guihandles;

import java.util.logging.Level;
import java.util.logging.Logger;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#browser";
    public static final Student DEFAULT_STUDENT = new StudentBuilder().build();
    private Student lastStudent = null;
    private boolean isLoaded = true;
    private final Logger logger = LogsCenter.getLogger(getClass());

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);
        StackPane stackPaneParent = getChildNode(BROWSER_ID);
        new GuiRobot().interact(() -> {
            if (stackPaneParent.isNeedsLayout()) {
                isLoaded = false;
            } else {
                stackPaneParent.requestLayout();
                isLoaded = true;
            }
        });
    }

    public Scene getLoadedScene() {
        return getChildNode(BROWSER_ID).getScene();
    }

    public void rememberStudent() {
        lastStudent = getLoadedStudent();
    }

    public boolean isStudentChanged() {
        return !lastStudent.equals(getLoadedScene());
    }

    /**
     * Translates the given URL into the equivalent {@code Student}
     */
    public Student getLoadedStudent() {

        Scene scene = getLoadedScene();
        StudentBuilder createdStudent = new StudentBuilder();

        String name = ((Label)scene.lookup("#nameLabel")).getText();
        String address = ((Label)scene.lookup("#addressLabel")).getText();
        String phone = ((Label)scene.lookup("#phoneLabel")).getText();
        String email = ((Label)scene.lookup("#emailLabel")).getText();

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            logger.log(Level.WARNING,  "No student has been loaded. " +
                    "Using default student information instead.");
            lastStudent = DEFAULT_STUDENT;
            return DEFAULT_STUDENT;
        }

        createdStudent.withName(name);
        createdStudent.withAddress(address);
        createdStudent.withPhone(phone);
        createdStudent.withEmail(email);

        return createdStudent.build();
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        return isLoaded;
    }
}
