package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYLLABUS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.model.subject.Syllabus;
import seedu.address.model.util.SubjectsUtil;

/**
 * Appends a syllabus topic to a specified subject for a specified student in the TutorHelper.
 */
public class AddSyllCommand extends Command {

    public static final String COMMAND_WORD = "addsyll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the syllabus of the student identified "
            + "by the student index number used in the displayed student list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: STUDENT_INDEX SUBJECT_INDEX "
            + "" + PREFIX_SYLLABUS + "SYLLABUS\n"
            + "Example: " + COMMAND_WORD + " 1 1 " + PREFIX_SYLLABUS + "Integration";

    public static final String MESSAGE_ADDSYLL_SUCCESS = "Added syllabus to Student: %1$s";
    public static final String MESSAGE_DUPLICATE_SYLLABUS = "Syllabus is already in Student: %1$s";

    private final Index studentIndex;
    private final Index subjectIndex;
    private final List<Syllabus> syllabuses;

    public AddSyllCommand(Index studentIndex, Index subjectIndex, List<Syllabus> syllabuses) {
        requireNonNull(studentIndex);
        requireNonNull(subjectIndex);
        requireNonNull(syllabuses);
        this.studentIndex = studentIndex;
        this.subjectIndex = subjectIndex;
        this.syllabuses = removeDuplicateSyllabusEntries(syllabuses);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentTarget = lastShownList.get(studentIndex.getZeroBased());

        if (subjectIndex.getZeroBased() >= studentTarget.getSubjects().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBJECT_INDEX);
        }

        Set<Subject> addedSubjectContent = addSubjectContentTo(studentTarget, subjectIndex, syllabuses);
        Student studentSubjUpdated = SubjectsUtil.createStudentWithNewSubjects(studentTarget, addedSubjectContent);

        model.updateStudentInternalField(studentTarget, studentSubjUpdated);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.commitTutorHelper();
        return new CommandResult(String.format(MESSAGE_ADDSYLL_SUCCESS, studentSubjUpdated));
    }

    /**
     * Add syllabus to the student.
     * @param studentTarget The student to add to.
     * @param subjectIndex The index of subject to add to.
     * @param syllabuses The list of syllabus to add.
     * @return a new {@code Set<Subject>} with the specified syllabus added
     */
    public Set<Subject> addSubjectContentTo(Student studentTarget, Index subjectIndex, Collection<Syllabus> syllabuses)
        throws CommandException {
        List<Subject> subjects = new ArrayList<>(studentTarget.getSubjects());
        Subject selectedSubject = subjects.get(subjectIndex.getZeroBased());
        Subject updatedSubject = selectedSubject;

        for (Syllabus syllabus: syllabuses) {
            if (selectedSubject.contains(syllabus)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_SYLLABUS, studentTarget));
            }
            updatedSubject = updatedSubject.add(syllabus);
        }

        subjects.set(subjectIndex.getZeroBased(), updatedSubject);
        return new HashSet<>(subjects);
    }

    /**
     * Remove duplicate syllabus entries to be added.
     * @param syllabuses unfiltered list.
     * @return the filtered syllabus list with no duplicates.
     */
    private List<Syllabus> removeDuplicateSyllabusEntries(List<Syllabus> syllabuses) {
        List<Syllabus> filteredList = new ArrayList<>();

        for (Syllabus filtered: syllabuses) {
            if (!filteredList.contains(filtered)) {
                filteredList.add(filtered);
            }
        }
        return filteredList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSyllCommand // instanceof handles nulls
                && studentIndex.equals(((AddSyllCommand) other).studentIndex))
                && syllabuses.equals(((AddSyllCommand) other).syllabuses); // state check
    }

    /**
     * Stores the details of the AddSyll command format.
     */
    public static class AddSyllFormatChecker {
        public static final int STUDENT_INDEX = 0;
        public static final int SUBJECT_INDEX = 1;
        public static final int NUMBER_OF_ARGS = 2;
    }
}
