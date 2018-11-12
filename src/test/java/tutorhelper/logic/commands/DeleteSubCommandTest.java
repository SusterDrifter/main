package tutorhelper.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_SUBJECT_INDEX;
import static tutorhelper.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorhelper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorhelper.logic.commands.DeleteSubCommand.MESSAGE_DELETE_ONLY_SUBJECT;
import static tutorhelper.model.util.SubjectsUtil.createStudentWithNewSubjects;
import static tutorhelper.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static tutorhelper.testutil.TypicalIndexes.INDEX_FIRST_SUBJECT;
import static tutorhelper.testutil.TypicalIndexes.INDEX_SECOND_SUBJECT;
import static tutorhelper.testutil.TypicalIndexes.INDEX_THIRD_STUDENT;
import static tutorhelper.testutil.TypicalStudents.getTypicalTutorHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import tutorhelper.commons.core.index.Index;
import tutorhelper.logic.CommandHistory;
import tutorhelper.model.Model;
import tutorhelper.model.ModelManager;
import tutorhelper.model.UserPrefs;
import tutorhelper.model.student.Student;
import tutorhelper.model.subject.Subject;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteSubCommand}.
 */
public class DeleteSubCommandTest {

    private Model model = new ModelManager(getTypicalTutorHelper(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getTutorHelper(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexesUnfilteredList_success() {
        Student studentTarget = model.getFilteredStudentList().get(INDEX_THIRD_STUDENT.getZeroBased());
        DeleteSubCommand deleteSubCommand = new DeleteSubCommand(INDEX_THIRD_STUDENT, INDEX_FIRST_SUBJECT);

        String expectedMessage = String.format(DeleteSubCommand.MESSAGE_DELETESUB_SUCCESS, studentTarget);
        ModelManager expectedModel = new ModelManager(model.getTutorHelper(), new UserPrefs());

        Student newStudent = simulateDeleteSubCommand(studentTarget, INDEX_FIRST_SUBJECT);

        expectedModel.updateStudent(studentTarget, newStudent);
        expectedModel.commitTutorHelper();

        assertCommandSuccess(deleteSubCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        DeleteSubCommand deleteSubCommand = new DeleteSubCommand(outOfBoundIndex, INDEX_FIRST_SUBJECT);

        assertCommandFailure(deleteSubCommand, model, commandHistory, MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexSubjectUnfilteredList_throwsCommandException() {
        Student studentTarget = model.getFilteredStudentList().get(INDEX_THIRD_STUDENT.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(studentTarget.getSubjects().size() + 1);
        DeleteSubCommand deleteSubCommand = new DeleteSubCommand(INDEX_THIRD_STUDENT, outOfBoundIndex);

        assertCommandFailure(deleteSubCommand, model, commandHistory, MESSAGE_INVALID_SUBJECT_INDEX);
    }

    @Test
    public void execute_validIndexesFilteredList_success() {
        Student studentTarget = model.getFilteredStudentList().get(INDEX_THIRD_STUDENT.getZeroBased());
        DeleteSubCommand deleteSubCommand = new DeleteSubCommand(INDEX_THIRD_STUDENT, INDEX_FIRST_SUBJECT);

        String expectedMessage = String.format(DeleteSubCommand.MESSAGE_DELETESUB_SUCCESS, studentTarget);
        Student updatedStudent = simulateDeleteSubCommand(studentTarget, INDEX_FIRST_SUBJECT);
        expectedModel.updateStudent(studentTarget, updatedStudent);
        expectedModel.commitTutorHelper();

        assertCommandSuccess(deleteSubCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexFilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        DeleteSubCommand deleteSubCommand = new DeleteSubCommand(outOfBoundIndex, INDEX_FIRST_SUBJECT);

        assertCommandFailure(deleteSubCommand, model, commandHistory, MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }


    @Test
    public void execute_invalidSubjectIndexFilteredList_throwsCommandException() {
        Student studentTarget = model.getFilteredStudentList().get(INDEX_THIRD_STUDENT.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(studentTarget.getSubjects().size() + 1);
        DeleteSubCommand deleteSubCommand = new DeleteSubCommand(INDEX_THIRD_STUDENT, outOfBoundIndex);

        assertCommandFailure(deleteSubCommand, model, commandHistory, MESSAGE_INVALID_SUBJECT_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Student studentTarget = model.getFilteredStudentList().get(INDEX_THIRD_STUDENT.getZeroBased());
        DeleteSubCommand deleteSubCommand = new DeleteSubCommand(INDEX_THIRD_STUDENT, INDEX_FIRST_SUBJECT);
        Model expectedModel = new ModelManager(model.getTutorHelper(), new UserPrefs());

        Student newStudent = simulateDeleteSubCommand(studentTarget, INDEX_FIRST_SUBJECT);
        expectedModel.updateStudent(studentTarget, newStudent);
        expectedModel.commitTutorHelper();

        // DeleteSub -> first student subject is erased
        deleteSubCommand.execute(model, commandHistory);

        // undo -> reverts TutorHelper back to previous state and filtered student list to show all students
        expectedModel.undoTutorHelper();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first student deleted again
        expectedModel.redoTutorHelper();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        DeleteSubCommand deleteSubCommand = new DeleteSubCommand(outOfBoundIndex, INDEX_FIRST_SUBJECT);

        // execution failed -> TutorHelper state not added into model
        assertCommandFailure(deleteSubCommand, model, commandHistory, MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);

        // single TutorHelper state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeleteSubCommand deleteSubCommand1 = new DeleteSubCommand(INDEX_THIRD_STUDENT, INDEX_FIRST_SUBJECT);
        DeleteSubCommand deleteSubCommand2 = new DeleteSubCommand(INDEX_THIRD_STUDENT, INDEX_SECOND_SUBJECT);

        // same object -> returns true
        assertEquals(deleteSubCommand1, deleteSubCommand1);

        // same values -> returns true
        DeleteSubCommand deleteSubCommand1Copy = new DeleteSubCommand(INDEX_THIRD_STUDENT, INDEX_FIRST_SUBJECT);
        assertEquals(deleteSubCommand1, deleteSubCommand1Copy);

        // different types -> returns false
        assertNotEquals(deleteSubCommand1, 1);

        // null -> returns false
        assertNotEquals(deleteSubCommand1, null);

        // different command -> returns false
        assertNotEquals(deleteSubCommand1, deleteSubCommand2);
    }

    @Test
    public void execute_deleteOnlySubject_throwsCommandException() {
        DeleteSubCommand deleteSubCommand = new DeleteSubCommand(INDEX_FIRST_STUDENT, INDEX_FIRST_SUBJECT);

        assertCommandFailure(deleteSubCommand, model, commandHistory,
                String.format(MESSAGE_DELETE_ONLY_SUBJECT,
                        model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased())));
    }

    /**
     * Simulates and returns a new {@code Student} created by DeleteSubCommand.
     */
    private Student simulateDeleteSubCommand(Student studentTarget, Index subjectIndex) {
        List<Subject> subjects = new ArrayList<>(studentTarget.getSubjects());
        subjects.remove(subjectIndex.getZeroBased());

        Set<Subject> newSubjects = new HashSet<>(subjects);

        return createStudentWithNewSubjects(studentTarget, newSubjects);
    }

}