package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Attribute in the address book.
 * Guarantees: immutable; key and value are validated as described in {@link #isValidKey(String)} and
 * {@link #isValidValue(String)}
 */
public class Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Attribute key and value should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*[^\\s]";

    public final String key;
    public final String value;

    /**
     * Constructs a {@code Attribute}.
     *
     * @param key A valid attribute key.
     * @param value A valid attribute value.
     */
    public Attribute(String key, String value) {
        requireNonNull(key);
        requireNonNull(value);
        checkArgument(isValidKey(key), "Attribute key should not be blank");
        checkArgument(isValidValue(value), "Attribute value should not be blank");
        this.key = key.toLowerCase().trim();
        this.value = value.trim();
    }

    /**
     * Returns true if a given string is a valid attribute key.
     */
    public static boolean isValidKey(String test) {
        return test != null && !test.trim().isEmpty();
    }

    /**
     * Returns true if a given string is a valid attribute value.
     */
    public static boolean isValidValue(String test) {
        return test != null && !test.trim().isEmpty();
    }

    /**
     * Returns true if this attribute contains the given value (case-insensitive).
     */
    public boolean containsValue(String testValue) {
        return value.toLowerCase().contains(testValue.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Attribute)) {
            return false;
        }

        Attribute otherAttribute = (Attribute) other;
        return key.equals(otherAttribute.key) && value.equals(otherAttribute.value);
    }

    @Override
    public int hashCode() {
        return key.hashCode() + value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + key + '=' + value + ']';
    }

}
