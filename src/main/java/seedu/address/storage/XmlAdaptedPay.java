package seedu.address.storage;

import java.util.Objects;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Payment;

/*
 * JAXB-friendly adapted version of Pay
 */
public class XmlAdaptedPay {

    private Index studentIndex;

    @XmlElement
    private int amount;

    @XmlElement
    private int year;

    @XmlElement
    private int month;

    private static final Logger logger = LogsCenter.getLogger(XmlAdaptedPay.class);


    /*
     * Constructs an XmlAdaptedPay.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPay(){

    }

    /**
     * Constructs a {@code XmlAdaptedPay} with the given {@code pay}
     * @param index
     * @param amount
     * @param month
     * @param year
     */
    public XmlAdaptedPay(Index index, int amount, int month, int year) {
        this.studentIndex = index;
        this.amount = amount;
        this.month = month;
        this.year = year;
    }

    /**
     * Converts a given Pay into this class for JAXB use.
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPay(Payment source){
        this.studentIndex = source.getIndex();
        this.amount = source.getAmount();
        this.month = source.getMonth();
        this.year = source.getYear();
    }

    public Payment toModelType()throws IllegalValueException  {

        if (!Payment.isValidAmount(amount)) {
            throw new IllegalValueException(Payment.MESSAGE_PAYMENT_AMOUNT_CONSTRAINTS );
        }else if(!Payment.isValidMonth(month)) {
            throw new IllegalValueException(Payment.MESSAGE_PAYMENT_MONTH_CONSTRAINTS );
        }else if(!Payment.isValidYear(year)) {
            throw new IllegalValueException(Payment.MESSAGE_PAYMENT_YEAR_CONSTRAINTS );
        }
        return new Payment(studentIndex, amount, month, year);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPay)) {
            return false;
        }

        return studentIndex.equals(((XmlAdaptedPay) other).studentIndex)
                &&  amount == (((XmlAdaptedPay) other).amount)
                &&  month == (((XmlAdaptedPay) other).month)
                &&  year == (((XmlAdaptedPay) other).year);
    }

    public int hashCode() {
        return  Objects.hash(studentIndex, amount, month, year);
    }
}
