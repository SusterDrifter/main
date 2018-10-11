package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.*;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TUITION_TIMING = "Friday, 4:00pm";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Set<Subject> subjects;
    private TuitionTiming tuitionTiming;
    private List<Payment> payments;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        subjects = subjectBuilder();
        tuitionTiming = new TuitionTiming(DEFAULT_TUITION_TIMING);
        payments = new ArrayList<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        subjects = personToCopy.getSubjects();
        tuitionTiming = personToCopy.getTuitionTiming();
        payments = personToCopy.getPayments();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code TuitionTiming} of the {@code Person} that we are building.
     */
    public PersonBuilder withTuitionTiming(String tuitionTiming) {
        this.tuitionTiming = new TuitionTiming(tuitionTiming);
        return this;
    }

    /**
     * Sets the {@code payments} of the {@code Person} that we are building.
     */
    public PersonBuilder withPayments(Payment ... paymentArray) {
        for (Payment payment: paymentArray) {
            this.payments.add(payment);
        }
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Person} that we are building.
     */
    public PersonBuilder withSubjects(String ... subjectArray) {
        for (String subject: subjectArray) {
            this.subjects.add(new Subject(subject));
        }
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, subjects, tuitionTiming, tags);
    }

    /**
     * Builds a default subject set.
     * @return the set of subject.
     */
    private static Set<Subject> subjectBuilder() {
        Set<Subject> subjectSet = new HashSet<>();
        subjectSet.add(new Subject("Mathematics"));
        return subjectSet;
    }

}
