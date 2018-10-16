package seedu.address.model.tuitionTiming;

import java.time.DayOfWeek;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class TuitionTimingContainsKeywordsPredicate implements Predicate<Person> {

    private final String keyword; //note that keyword is capitalised for day.

    public TuitionTimingContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        DayOfWeek day = person.getTuitionTiming().day;
        String time = person.getTuitionTiming().time;

        if ((keyword.toLowerCase()).matches((TuitionTiming.DAY_REGEX).toLowerCase())) {
            return day.equals(DayOfWeek.valueOf(keyword.toUpperCase()));
        } else if ((keyword.toLowerCase()).matches((TuitionTiming.TIME_REGEX).toLowerCase())) {
            return StringUtil.containsWordIgnoreCase(time, keyword);
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TuitionTimingContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((TuitionTimingContainsKeywordsPredicate) other).keyword)); // state check
    }
}
