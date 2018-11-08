package seedu.address.testutil;

import static seedu.address.model.util.SampleDataUtil.getSyllabusList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Payment;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.model.subject.Syllabus;
import seedu.address.model.tag.Tag;
import seedu.address.model.tuitiontiming.TuitionTiming;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Alex Tan";
    public static final String DEFAULT_PHONE = "94351253";
    public static final String DEFAULT_EMAIL = "alice@example.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SUBJECT = "Chemistry";
    public static final String DEFAULT_TUITION_TIMING = "Monday 6:00pm";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Subject> subjects = new HashSet<>();
    private TuitionTiming tuitionTiming;
    private List<Payment> payments;
    private Set<Tag> tags;

    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        subjects.add(Subject.makeSubject(DEFAULT_SUBJECT));
        tuitionTiming = new TuitionTiming(DEFAULT_TUITION_TIMING);
        payments = new ArrayList<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        subjects = studentToCopy.getSubjects();
        tuitionTiming = studentToCopy.getTuitionTiming();
        payments = new ArrayList<>(studentToCopy.getPayments());
        tags = new HashSet<>(studentToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Student} that we are building.
     */
    public StudentBuilder withSubjects(String ... subjectArray) {
        this.subjects = SampleDataUtil.getSubjectSet(subjectArray);
        return this;
    }

    /**
     * Sets the {@code Syllabus} of the {@code Subject} for the {@code Student} that we are building.
     */
    public StudentBuilder withSyllabus(Index subjectIndex, String ... syllabusArray) {
        Subject selectedSubject = new ArrayList<>(subjects)
                .get(subjectIndex.getZeroBased())
                .append(getSyllabusList(syllabusArray));
        List<Subject> newSubjectsList = new ArrayList<>(subjects);
        newSubjectsList.set(subjectIndex.getZeroBased(), selectedSubject);
        this.subjects = new HashSet<>(newSubjectsList);
        return this;
    }

    /**
     * Sets the {@code TuitionTiming} of the {@code Student} that we are building.
     */
    public StudentBuilder withTuitionTiming(String tuitionTiming) {
        this.tuitionTiming = new TuitionTiming(tuitionTiming);
        return this;
    }

    /**
     * Sets the {@code Payment} of the {@code Student} that we are building.
     */
    public StudentBuilder withPayments(String ... paymentArray) {
        for (String payment: paymentArray) {
            String[] separatedPayment = payment.split("\\s");
            this.payments.add(new Payment(
                    Index.fromOneBased(Integer.valueOf(separatedPayment[0])),
                    Integer.valueOf(separatedPayment[1]),
                    Integer.valueOf(separatedPayment[2]),
                    Integer.valueOf(separatedPayment[3])
                    ));
        }
        return this;
    }

    /**
     * Sets the {@code Payment} of the {@code Student} that we are building.
     */
    public StudentBuilder withPayments(Payment ... paymentArray) {
        for (Payment payment: paymentArray) {
            this.payments.add(payment);
        }
        return this;
    }

    /**
     * Replaces the {@code Syllabus} of the {@code Subject} of the {@code Student} we are building.
     */
    public StudentBuilder replaceSyllabus(Index subjectIndex, Index syllabusIndex, String syllabus) {

        Syllabus selectedSubject = new ArrayList<>(subjects)
                .get(subjectIndex.getZeroBased())
                .getSubjectContent()
                .set(syllabusIndex.getZeroBased(), new Syllabus(syllabus, false));
        return this;
    }

    public Student build() {
        return new Student(name, phone, email, address, subjects, tuitionTiming, tags, payments);
    }

    /**
     * Builds a default subject set.
     * @return the set of subject.
     */
    private static Set<Subject> subjectBuilder() {
        Set<Subject> subjectSet = new HashSet<>();
        subjectSet.add(Subject.makeSubject("Mathematics"));
        return subjectSet;
    }



}
