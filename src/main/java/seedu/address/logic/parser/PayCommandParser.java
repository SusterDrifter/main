package seedu.address.logic.parser;

import java.util.logging.Logger;
import java.util.stream.Stream;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Payment;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PAYMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PAYMENT_MONTH;
import static seedu.address.logic.parser.CliSyntax.PAYMENT_YEAR;

public class PayCommandParser implements Parser<PayCommand> {

    private final Logger logger = LogsCenter.getLogger(PayCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PayCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PAYMENT_INDEX, PAYMENT_AMOUNT, PAYMENT_MONTH, PAYMENT_YEAR);

        if (!arePrefixesPresent(argMultimap, PAYMENT_INDEX, PAYMENT_AMOUNT, PAYMENT_MONTH, PAYMENT_YEAR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PAYMENT_INDEX).get());
        int amount = ParserUtil.parseAmount(argMultimap.getValue(PAYMENT_AMOUNT).get());
        int month = ParserUtil.parseMonth(argMultimap.getValue(PAYMENT_MONTH).get());
        int year = ParserUtil.parseYear(argMultimap.getValue(PAYMENT_YEAR).get());

        Payment payment = new Payment(index, amount, month, year);
        return new PayCommand(payment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
