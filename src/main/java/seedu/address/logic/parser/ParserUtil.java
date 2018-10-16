package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Phone;
import seedu.address.model.subject.Subject;
import seedu.address.model.subject.SubjectType;
import seedu.address.model.subject.Syllabus;
import seedu.address.model.tag.Tag;
import seedu.address.model.tuitionTiming.TuitionTiming;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String subject} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!SubjectType.isValidSubjectName(trimmedSubject)) {
            throw new ParseException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
        }
        return new Subject(trimmedSubject);
    }

    /**
     * Parses {@code Collection<String> subjects} into a {@code Set<Subject>}.
     */
    public static Set<Subject> parseSubjects(Collection<String> subjects) throws ParseException {
        requireNonNull(subjects);
        final Set<Subject> subjectSet = new HashSet<>();
        for (String subjectName : subjects) {
            subjectSet.add(parseSubject(subjectName));
        }
        return subjectSet;
    }

    /**
     * Parses a {@code String tuitionTiming} into a {@code TuitionTiming}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tuitionTiming} is invalid.
     */
    public static TuitionTiming parseTuitionTiming(String tuitionTiming) throws ParseException {
        requireNonNull(tuitionTiming);
        String trimmedTuitionTiming = tuitionTiming.trim();
        if (!TuitionTiming.isValidTiming(trimmedTuitionTiming)) {
            throw new ParseException(TuitionTiming.MESSAGE_TUITION_TIMING_CONSTRAINTS);
        }
        return new TuitionTiming(trimmedTuitionTiming);
    }

    /**
     * Parses a {@code String amount} into an integer.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code integer} is invalid.
     */
    public static int parseAmount(String amount) throws ParseException {

        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        boolean doesNotContainAllDigits = false;

        for (int i = 0; i < trimmedAmount.length(); i++) {
            if (!Character.isDigit(trimmedAmount.charAt(i))) {
                doesNotContainAllDigits = true;
                break;
            }
        }

        if (doesNotContainAllDigits == true) {
            throw new ParseException(Payment.MESSAGE_PAYMENT_AMOUNT_CONSTRAINTS);
        }

        int integerAmount = Integer.parseInt(trimmedAmount);
        if (!Payment.isValidAmount(integerAmount)) {
            throw new ParseException(Payment.MESSAGE_PAYMENT_AMOUNT_CONSTRAINTS);
        }
        return integerAmount;
    }

    /**
     * Parses a {@code String month} into an integer.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code integer} is invalid.
     */
    public static int parseMonth(String month) throws ParseException {
        requireNonNull(month);
        String trimmedMonth = month.trim();
        if (!Payment.isValidMonth(Integer.parseInt(trimmedMonth))) {
            throw new ParseException(Payment.MESSAGE_PAYMENT_MONTH_CONSTRAINTS);
        }
        return Integer.parseInt(trimmedMonth);
    }

    /**
     * Parses a {@code String year} into an integer.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code integer} is invalid.
     */
    public static int parseYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.trim();
        if (!Payment.isValidYear(Integer.parseInt(trimmedYear))) {
            throw new ParseException(Payment.MESSAGE_PAYMENT_YEAR_CONSTRAINTS);
        }
        return Integer.parseInt(trimmedYear);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code oneBasedIndexes} into a list of {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndexes(String oneBasedIndexes) throws ParseException {
        String trimmedIndexes = oneBasedIndexes.trim();
        String[] separatedIndexes = trimmedIndexes.split("\\s");
        List<Index> listIndexes = new ArrayList<>();

        for (String indexCandidate : separatedIndexes) {
            listIndexes.add(ParserUtil.parseIndex(indexCandidate));
        }

        return listIndexes;
    }

    /**
     * Parses a {@code String syllabus} into a {@code Syllabus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code syllabus} input is invalid.
     */
    public static Syllabus parseSyllabus(String syllabus) throws ParseException {
        requireNonNull(syllabus);
        String trimmedSyllabus = syllabus.trim();
        if (!Syllabus.isValidSyllabus(trimmedSyllabus)) {
            throw new ParseException(Syllabus.MESSAGE_SYLLABUS_CONSTRAINTS);
        }
        return new Syllabus(trimmedSyllabus, false);
    }

    /**
     * Parses a {@code String day} into a {@code day}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code day} input is invalid.
     */
    public static DayOfWeek parseDay(String day) throws ParseException {
        requireNonNull(day);
        String trimmedDay = day.trim();
        if (!trimmedDay.matches(TuitionTiming.DAY_REGEX)) {
            throw new ParseException(GroupCommand.MESSAGE_USAGE);
        }
        return DayOfWeek.valueOf(trimmedDay.toUpperCase());

    }

    /**
     * Parses a {@code String time} into a {@code time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} input is invalid.
     */
    public static String parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();

        if (!trimmedTime.matches(TuitionTiming.TIME_REGEX)) {
            throw new ParseException(GroupCommand.MESSAGE_USAGE);
        }
        return trimmedTime;

    }

}
