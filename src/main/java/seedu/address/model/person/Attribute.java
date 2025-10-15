package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
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

    public String getKey() {
        return key;
    }

    public Set<String> getValues() {
        return values;
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

