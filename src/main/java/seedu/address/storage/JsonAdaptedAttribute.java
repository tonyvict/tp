package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Attribute;

/**
 * Jackson-friendly version of {@link Attribute}.
 */
class JsonAdaptedAttribute {

    private final String key;
    private final String value;

    /**
     * Constructs a {@code JsonAdaptedAttribute} with the given attribute details.
     */
    @JsonCreator
    public JsonAdaptedAttribute(@JsonProperty("key") String key, @JsonProperty("value") String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Converts a given {@code Attribute} into this class for Jackson use.
     */
    public JsonAdaptedAttribute(Attribute source) {
        key = source.key;
        value = source.value;
    }

    /**
     * Converts this Jackson-friendly adapted attribute object into the model's {@code Attribute} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attribute.
     */
    public Attribute toModelType() throws IllegalValueException {
        if (!Attribute.isValidKey(key)) {
            throw new IllegalValueException("Invalid attribute key: " + key);
        }
        if (!Attribute.isValidValue(value)) {
            throw new IllegalValueException("Invalid attribute value: " + value);
        }
        return new Attribute(key, value);
    }

}

