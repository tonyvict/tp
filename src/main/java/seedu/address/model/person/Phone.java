package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS = "Phone numbers can take any values, but they should not be blank";
    public static final String MESSAGE_WARNING_NO_DIGITS = "Warning: Phone number does not contain any digits.";
    public static final String MESSAGE_WARNING_INVALID_CHARACTERS = "Warning: Phone number might not be valid. "
            + "Typical phone numbers only have numbers";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string contains at least one digit.
     */
    public static boolean hasDigits(String test) {
        return test.matches(".*\\d.*");
    }

    /**
     * Returns true if the given string contains characters other than digits, spaces, and standard phone symbols.
     */
    public static boolean containsNonStandardChars(String test) {
        return test.matches(".*[^0-9].*");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
