package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.StudentPanelSelectionChangedEvent;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private Student lastStudent;

    @FXML
    private Label nameLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label tuitionTimingDayLabel;

    @FXML
    private Label tuitionTimingTimeLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private FlowPane paymentAmount;

    @FXML
    private FlowPane paymentMonth;

    @FXML
    private FlowPane paymentYear;

    @FXML
    private FlowPane subjectList;

    @FXML
    private FlowPane subjectsShort;

    @FXML
    private FlowPane tagsShort;

    @FXML
    private AnchorPane paymentBackground;

    @FXML
    private Line dividerHori;

    @FXML
    private Line dividerVert;

    @FXML
    private Label paymentLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Label yearLabel;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadStudentPage(null);
        registerAsAnEventHandler(this);
    }

    /**
     * Loads a student's information into the AnchorPane.
     * @param student The student whose page is to be loaded into the AnchorPane.
     */
    private void loadStudentPage(Student student) {
        if (student != null) {

            setBackgroundState(true);
            // Clear previous information
            subjectsShort.getChildren().clear();
            tagsShort.getChildren().clear();
            paymentAmount.getChildren().clear();
            paymentMonth.getChildren().clear();
            paymentYear.getChildren().clear();
            subjectList.getChildren().clear();

            // Fill the labels with info from the student object.
            nameLabel.setText(student.getName().fullName);
            addressLabel.setText(student.getAddress().value);
            emailLabel.setText(student.getEmail().value);
            phoneLabel.setText(student.getPhone().value);

            tuitionTimingDayLabel.setText(student.getTuitionTiming().day.toString().substring(0, 3));
            tuitionTimingTimeLabel.setText(student.getTuitionTiming().time);

            student.getSubjects().forEach(subject -> subjectsShort.getChildren().add(
                    new Label(subject.getSubjectName())));
            student.getTags().forEach(tag -> tagsShort.getChildren().add(new Label(tag.tagName)));

            student.getPayments().forEach(amount -> paymentAmount.getChildren().add(
                    new Label(String.valueOf(amount.getAmount()))));
            student.getPayments().forEach(amount -> paymentMonth.getChildren().add(
                    new Label(String.valueOf(amount.getMonth()))));
            student.getPayments().forEach(amount -> paymentYear.getChildren().add(
                    new Label(String.valueOf(amount.getYear()))));

            for (int i = 0; i < student.getSubjects().size(); i++) {
                List<Subject> subject = new ArrayList<>(student.getSubjects());
                Index currentIndex = Index.fromZeroBased(i);
                subjectList.getChildren().add(
                        new Label("(" + currentIndex.getOneBased() + ") " + subject.get(i).toString()));
            }

        } else {
            // Student is null, remove all the text.
            nameLabel.setText("");
            addressLabel.setText("");
            emailLabel.setText("");
            phoneLabel.setText("");
            tuitionTimingDayLabel.setText("");
            tuitionTimingTimeLabel.setText("");
            paymentAmount.getChildren().clear();
            paymentMonth.getChildren().clear();
            paymentYear.getChildren().clear();
            tagsShort.getChildren().clear();
            subjectsShort.getChildren().clear();
            subjectList.getChildren().clear();
            setBackgroundState(false);
        }

        lastStudent = student;
    }

    /**
     * Set visibility of background components based on {@code state}
     */
    private void setBackgroundState(boolean state) {
        paymentBackground.setVisible(state);
        dividerHori.setVisible(state);
        dividerVert.setVisible(state);
        paymentLabel.setVisible(state);
        monthLabel.setVisible(state);
        amountLabel.setVisible(state);
        yearLabel.setVisible(state);
    }

    @Subscribe
    private void handleStudentPanelSelectionChangedEvent(StudentPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadStudentPage(event.getNewSelection());
    }
}
