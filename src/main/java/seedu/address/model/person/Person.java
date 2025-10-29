package seedu.address.model.person;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;


/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 *
 * This class now includes support for grades, attributes, and lessons.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Attribute> attributes = new HashSet<>();
    // UI-specific state, not part of core data model
    private final transient BooleanProperty isExpanded = new SimpleBooleanProperty(false);
    private final LessonList lessonList;
    private final GradeList gradeList;

    /**
     * Every field must be present and not null.
     *
     * @param name Person's name.
     * @param phone Person's phone number.
     * @param email Person's email address.
     * @param address Person's home address.
     * @param remark Additional remarks about the person.
     * @param tags Set of tags associated with the person.
     * @param attributes Set of attributes describing the person.
     * @param lessonList List of lessons associated with the person.
     * @param gradeList List of grades associated with the person.
     */
    public Person(Name name,
                  Phone phone,
                  Email email,
                  Address address,
                  Remark remark,
                  Set<Tag> tags,
                  Set<Attribute> attributes,
                  LessonList lessonList,
                  GradeList gradeList) {
        requireAllNonNull(name, phone, email, address, tags, attributes, lessonList, gradeList);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
        this.attributes.addAll(attributes);
        this.lessonList = lessonList;
        this.gradeList = gradeList;
    }

    /**
     * Returns the name of the person.
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the phone number of the person.
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns the email of the person.
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Returns the address of the person.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Returns the remark associated with the person.
     */
    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns the list of lessons associated with the person.
     */
    public LessonList getLessonList() {
        return lessonList;
    }

    /**
     * Returns if the person is expanded
     */

    public boolean isExpanded() {
        return isExpanded.get();
    }

    /**
     * Sets the expanded state of the person
     * @param expanded The new expanded state
     */
    public void setExpanded(boolean expanded) {
        isExpanded.set(expanded);
    }

    /**
     * Returns if the person is expanded as SimpleBooleanProperty
     */
    public BooleanProperty expandedProperty() {
        return isExpanded;
    }
    /**
     * Returns the list of grades associated with the person.
     */
    public GradeList getGradeList() {
        return gradeList;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     *
     * @return Unmodifiable view of the tag set.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable attribute set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     *
     * @return Unmodifiable view of the attribute set.
     */
    public Set<Attribute> getAttributes() {
        return Collections.unmodifiableSet(attributes);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     *
     * @param otherPerson The other person to compare to.
     * @return True if both have the same name, false otherwise.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     *
     * @param other The other object to compare to.
     * @return True if both persons are identical in all fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && attributes.equals(otherPerson.attributes)
                && lessonList.equals(otherPerson.lessonList)
                && gradeList.equals(otherPerson.gradeList);
    }

    /**
     * Returns a hash code value for this person.
     *
     * @return Hash code based on the person’s fields.
     */
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, attributes, lessonList, gradeList);
    }

    /**
     * Returns a string representation of the person, including all its details.
     *
     * @return String containing the person's details.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("remark", remark)
                .add("tags", tags)
                .add("attributes", attributes)
                .add("lesson list", lessonList)
                .add("grades", gradeList)
                .toString();
    }

}
