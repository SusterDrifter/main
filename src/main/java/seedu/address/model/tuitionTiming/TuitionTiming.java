package seedu.address.model.tuitionTiming;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;

/**
 * Represents tuition timing in TutorHelper.
 * Guarantees: immutable; is valid as declared in {@link #isValidTiming(String)}
 */
public class TuitionTiming {

    public static final String MESSAGE_TUITION_TIMING_CONSTRAINTS =
            "Tuition Day and Time should not be blank and should be in the format:\n"
                    + "1) Day, Time\n"
                    + "2) Day Time\n"
                    + "Examples of Valid Input:\n"
                    + "Monday, 1:00pm\n"
                    + "Tuesday 1:15AM\n"
                    + "Wednesday, 12:30pm\n";
    public static final String DAY_REGEX = "^(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)";
    public static final String TIME_REGEX = "\\d{1,2}[:{1}]\\d{2}(am|pm|AM|PM)$";

    /**
     * The first character of the tuition time and day must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TUITION_TIMING_VALIDATION_REGEX = DAY_REGEX + ".{1,2}" + TIME_REGEX;

    public final DayOfWeek day;
    public final String time;
    public final String value;
    private String dayString;
    private String timeString;

    /**
     * Constructs an {@code TuitionTiming}.
     *
     * @param tuitionTiming A valid tuition time and day.
     */
    public TuitionTiming(String tuitionTiming) {
        requireNonNull(tuitionTiming);
        checkArgument(isValidTiming(tuitionTiming), MESSAGE_TUITION_TIMING_CONSTRAINTS);
        splitTuitionTiming(tuitionTiming);
        // this.day and this.time used for comparison purposes for Group Command
        this.day = DayOfWeek.valueOf(dayString.toUpperCase());
        this.time = timeString;
        value = tuitionTiming;
    }

    /**
     * Splits the {@code tuitionTiming} string into Day and Time.
     */
    private void splitTuitionTiming(String tuitionTiming) {
        tuitionTiming = tuitionTiming.replace(",", " ");
        String[] tuitionTimingArr = tuitionTiming.split("\\s+");
        dayString = tuitionTimingArr[0].trim();
        timeString = tuitionTimingArr[1].trim();
    }

    /**
     * Converts the 12hours time into 24hours time.
     * @param time Time in hh:mm format. E.g. 12:00pm.
     * @return a 24hour version of {@code time} as an int. E.g. 1:00pm converts to 1300.
     */
    public static SimpleDateFormat convertTwelveHourToTwentyFourHour(String time) {
        requireNonNull(time);
        SimpleDateFormat timeIn12Hour = new SimpleDateFormat("hh:mmaa");
        SimpleDateFormat timeIn24Hour = new SimpleDateFormat("HH:mm");
        try {
            timeIn24Hour.format(timeIn12Hour.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeIn24Hour;
    }

    /**
     * Returns true if a given string is a valid time and day
     */
    public static boolean isValidTiming(String test) {
        return test.matches(TUITION_TIMING_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TuitionTiming // instanceof handles nulls
                && value.equals(((TuitionTiming) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
