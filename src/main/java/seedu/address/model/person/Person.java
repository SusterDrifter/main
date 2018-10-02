package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.Payment;
import seedu.address.model.syllabusbook.SyllabusBook;

import seedu.address.model.tag.Tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final List<Payment> payments = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();
    private final SyllabusBook syllabusBook;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this(name, phone, email, address, tags, new SyllabusBook());
    }

    /**
     * Every field must be present and not null. Include paymentList. Without syllabusBook so we create a new
     * empty syllabusBook.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, ArrayList<Payment> paymentList) {
        requireAllNonNull(name, phone, email, address, tags, paymentList);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.tags.addAll(tags);
        this.address = address;
        this.payments.addAll(paymentList);
        this.syllabusBook = new SyllabusBook();
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, SyllabusBook syllabusBook) {
        requireAllNonNull(name, phone, email, address, tags, syllabusBook);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.syllabusBook = syllabusBook;
    }

    /**
     * Every field must be present and not null. A constructor to create a person with payments.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, SyllabusBook syllabusBook,
                  List<Payment> paymentList) {
        requireAllNonNull(name, phone, email, address, tags, syllabusBook, paymentList);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.syllabusBook = syllabusBook;
        this.payments.addAll(paymentList);
    }

    /**
     * Update payment for this person and returns a new instance of this person.
     * @return the same person but updated with payment.
     */
    public Person updatePayment(Payment newPayment) {
        this.payments.add(newPayment);
        return new Person(this.name, this.phone, this.email, this.address, this.tags, this.syllabusBook, this.payments);
    }

    /*
     * Restore payments for this person.
     */
    public void restorePayments(ArrayList<Payment> original) {
        this.payments.addAll(original);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }


    public List<Payment> getPayments() {
        return payments;
    }

    public SyllabusBook getSyllabusBook () {
        return syllabusBook;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags () {
        return Collections.unmodifiableSet(tags);
    }


    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson (Person otherPerson){
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals (Object other){
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getPayments().equals(getPayments())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getSyllabusBook().equals(getSyllabusBook());
        }

        @Override
        public int hashCode () {
            // use this method for custom fields hashing instead of implementing your own
            return Objects.hash(name, phone, email, address, payments, tags, syllabusBook);
        }

        @Override
        public String toString () {
            final StringBuilder builder = new StringBuilder();
            builder.append(getName())
                    .append(" Phone: ")
                    .append(getPhone())
                    .append(" Email: ")
                    .append(getEmail())
                    .append(" Address: ")
                    .append(getAddress())
                    .append(" Tags: ");
            getTags().forEach(builder::append);

            builder.append(" Payments: ");
            getPayments().forEach(builder::append);

            builder.append(" \nSyllabus: ")
                    .append(getSyllabusBook());

            return builder.toString();
        }
    }

