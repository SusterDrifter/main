package seedu.address.logic.commands;

import java.util.List;
import java.util.logging.Logger;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Payment;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class PayCommand extends Command {
    public static final String COMMAND_WORD = "paid";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates if a person has paid.\n"
            + "Parameters: \n"
            + "INDEX(must be a positive integer)\n"
            + "AMOUNT(must be a positive integer)\n"
            + "MONTH(must be a valid month)\n"
            + "YEAR(must be a valid year)\n"
            + "Example: " + COMMAND_WORD + " idx/1 amt/200 mth/08 yr/2018";

    private final Logger logger = LogsCenter.getLogger(PayCommand.class);

    public static final String MESSAGE_DUPLICATE_PERSON = "Payment has already been updated for this person";
    public static final String MESSAGE_PAYMENT_SUCCESS = "Payment for this person is added: %1$s";

    private Index targetIndex;
    Payment newPayment;

    public PayCommand(Payment payment){
        this.targetIndex = payment.getIndex();
        this.newPayment = payment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personTarget = lastShownList.get(targetIndex.getZeroBased());
        Person personToPay = personTarget.updatePayment(newPayment);

        //(debug) Print out who paid
        /*
        ArrayList<Payment> paymentArr = personToPay.getPayments();
        logger.info("size is " + paymentArr.size());
        for (int i=0; i<paymentArr.size(); i++){
            logger.info("Payment made is" + paymentArr.get(i));
        }
        */

        model.updatePerson(personTarget, personToPay);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_PAYMENT_SUCCESS, personToPay));
    }

}
