package seedu.address.model;

import java.util.Objects;
import seedu.address.commons.core.index.Index;

public class Payment {

    public static final String MESSAGE_PAYMENT_AMOUNT_CONSTRAINTS =
            "Amount of payment should only contain numbers";
    public static final String MESSAGE_PAYMENT_MONTH_CONSTRAINTS =
            "Month of payment should only contain numbers between 1 to 12, inclusive";
    public static final String MESSAGE_PAYMENT_YEAR_CONSTRAINTS =
            "Year of payment should only contain numbers";

    public static final String TAG_VALIDATION_REGEX = "(.)*(\\d)(.)*";

    private final Index studentIndex;
    private final int amount;
    private final int month;
    private final int year;

    /**
     * Constructs a {@code Tag}.
     *
     * @param index A valid index name.
     * @param amount A valid amount.
     * @param month A valid month value.
     * @param year A valid year value
     */
    public Payment(Index index, int amount, int month, int year ) {
        this.studentIndex = index;
        this.amount = amount;
        this.month = month;
        this.year = year;
    }

    /**
     * Returns true if a given int is a valid number.
     */
    public static boolean isValidAmount(int test) {
        return String.valueOf(test).matches(TAG_VALIDATION_REGEX);
    }


    /**
     * Returns true if a given int is a valid month.
     */
    public static boolean isValidMonth(int test) {

        //Check if month is within the correct range of jan - dec
        if(String.valueOf(test).matches(TAG_VALIDATION_REGEX)){
            int testMonth = test;
            if(testMonth>=1 && testMonth<=12) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a given int is a valid year.
     */
    public static boolean isValidYear(int test) {
        return String.valueOf(test).matches(TAG_VALIDATION_REGEX);
    }

    /**
     * Returns student index
     */
    public Index getIndex(){
        return this.studentIndex;
    }

    /*
     * Return month of payment
     */
    public int getMonth(){
        return this.month;
    }

    /*
     * Returns year of payment
     */
    public int getYear(){
        return this.year;
    }

    /*
     * Return amount of payment
     */
    public int getAmount(){
        return this.amount;
    }


    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return  " [Month: "+ month+" Year: " + year + " Amount: " + amount+"]";
    }

    public boolean equals(Object other){
        return other == this ||
                (other instanceof Payment
                && this.studentIndex == (((Payment) other).studentIndex)
                && this.amount == (((Payment) other).amount)
                && this.month == (((Payment) other).month)
                && this.year == (((Payment) other).year));
    }

    public int hashCode(){
        return  Objects.hash(studentIndex, amount, month, year);
    }


}


