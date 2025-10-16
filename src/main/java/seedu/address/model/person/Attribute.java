package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Person's attribute in the address book.
 * Guarantees: immutable; is always valid
 */
public class Attribute {
    public final String key;
    public final Set<String> values;

    /**
     * Constructs a {@code Attribute}.
     */
    public Attribute(String key, Collection<String> values) {
        requireNonNull(key);
        requireNonNull(values);
        this.key = key.trim().toLowerCase();
        this.values = new HashSet<>();
        for (String value : values) {
            this.values.add(value.trim().toLowerCase());
        }
    }

    /**
     * Constructs a {@code Attribute}.
     */
    public Attribute(String key, String value) {
        requireNonNull(key);
        requireNonNull(value);
        this.key = key.trim().toLowerCase();
        this.values = new HashSet<>();
        this.values.add(value.trim().toLowerCase());
    }

    public String getKey() {
        return key;
    }

    public Set<String> getValues() {
        return values;
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
     * Returns true if a given string is a valid attribute value.
     */
    public static boolean isValidValue(List<String> test) {
        if (test == null || test.isEmpty()) {
            return false;
        }
        for (String value : test) {
            if (value == null || value.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean containsValue(String value) {
        return values.contains(value.trim().toLowerCase());
    }


    @Override
    public String toString() {
        return key + " = " + String.join(", ", values);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Attribute)) {
            return false;
        }
        Attribute otherAttribute = (Attribute) other;
        return key.equals(otherAttribute.key) && values.equals(otherAttribute.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, values);
    }

}

