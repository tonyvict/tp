package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private final LessonList lessonList;
    private final GradeList gradeList;

    /**
     * Every field must be present and not null.
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

    public Remark getRemark() {
        return remark;
    }

    public LessonList getLessonList() {
        return lessonList;
    }

    public GradeList getGradeList() {
        return gradeList;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable attribute set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Attribute> getAttributes() {
        return Collections.unmodifiableSet(attributes);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
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

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, attributes, lessonList, gradeList);
    }

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

    /**
     * Returns a new {@code Person} with attributes whose keys match the given set removed.
     * If none of the specified keys exist, returns the same {@code Person} instance.
     *
     * @param keysToDelete Set of attribute keys to be removed from this person.
     * @return A new {@code Person} without the specified attributes, or the same person if no attributes were removed.
     */
    public Person removeAttributesByKey(Set<String> keysToDelete) {
        requireNonNull(keysToDelete);

        Set<Attribute> updatedAttributes = new HashSet<>();
        for (Attribute attr : this.attributes) {
            if (!keysToDelete.contains(attr.getKey())) {
                updatedAttributes.add(attr);
            }
        }

        // No change -> return same person
        if (updatedAttributes.equals(this.attributes)) {
            return this;
        }

        return new Person(name, phone, email, address, remark, tags, updatedAttributes, lessonList, gradeList);
    }
}
