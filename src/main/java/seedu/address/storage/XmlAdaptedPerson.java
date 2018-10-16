package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.tuitionTiming.TuitionTiming;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String tuitionTiming;

    @XmlElement
    private List<XmlAdaptedSubject> subjects = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedPay> payments = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email,
                            String address, List<XmlAdaptedSubject> subjects, String tuitionTiming,
                            List<XmlAdaptedTag> tagged, List<XmlAdaptedPay> payments) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (subjects != null) {
            this.subjects = new ArrayList<>(subjects);
        }
        this.tuitionTiming = tuitionTiming;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }

        if (payments != null) {
            this.payments = new ArrayList<>(payments);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        subjects = source.getSubjects().stream()
                .map(XmlAdaptedSubject::new)
                .collect(Collectors.toList());;
        tuitionTiming = source.getTuitionTiming().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        payments = source.getPayments().stream()
                .map(XmlAdaptedPay::new)
                .collect(Collectors.toList());

    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        final List<Subject> personSubjects = new ArrayList<>();
        for (XmlAdaptedSubject subject : subjects) {
            personSubjects.add(subject.toModelType());
        }

        final List<Payment> personPayments = new ArrayList<>();
        for (XmlAdaptedPay payment : payments) {
            personPayments.add(payment.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (tuitionTiming == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TuitionTiming.class.getSimpleName()));
        }
        if (!TuitionTiming.isValidTiming(tuitionTiming)) {
            throw new IllegalValueException(TuitionTiming.MESSAGE_TUITION_TIMING_CONSTRAINTS);
        }
        final TuitionTiming modelTuitionTiming = new TuitionTiming(tuitionTiming);

        final Set<Subject> modelSubjects = new HashSet<>(personSubjects);
        final Set<Tag> modelTags = new HashSet<>(personTags);
        final List<Payment> modelPayments = new ArrayList<>(personPayments);

        return new Person(modelName, modelPhone, modelEmail, modelAddress,
                modelSubjects, modelTuitionTiming, modelTags, modelPayments);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && subjects.equals(otherPerson.subjects)
                && Objects.equals(tuitionTiming, otherPerson.tuitionTiming)
                && tagged.equals(otherPerson.tagged)
                && payments.equals(otherPerson.payments);
    }
}
