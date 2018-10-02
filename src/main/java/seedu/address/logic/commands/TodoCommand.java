package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYLLABUS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.syllabusbook.Syllabus;

/**
 * Finds all persons whose name matches the keyword and add the to do element to the data.
 * Find is case-insensitive.
 */
public class TodoCommand extends Command {

    public static final String COMMAND_WORD = "todo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the syllabus of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX "
            + "[" + PREFIX_SYLLABUS + "SYLLABUS]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_SYLLABUS + "Integration";

    private static final String MESSAGE_SYLLABUS_SUCCESS = "Added syllabus to Person: %1$s";

    private final Index index;
    private final Syllabus syllabus;

    public TodoCommand(Index index, Syllabus syllabus) {
        this.index = index;
        this.syllabus = syllabus;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personTarget = lastShownList.get(index.getZeroBased());

        Person personToAddSyllabus = new Person(personTarget.getName(),
                personTarget.getPhone(), personTarget.getEmail(), personTarget.getAddress(),
                personTarget.getTags(), personTarget.getSyllabusBook().addToSyllabusBook(syllabus),
                personTarget.getPayments());

        model.updatePerson(personTarget, personToAddSyllabus);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SYLLABUS_SUCCESS, personToAddSyllabus));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoCommand // instanceof handles nulls
                && index.equals(((TodoCommand) other).index))
                && syllabus.equals(((TodoCommand) other).syllabus); // state check
    }
}
