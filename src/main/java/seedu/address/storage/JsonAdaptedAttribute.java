package seedu.address.storage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Attribute;

/**
 * Jackson-friendly version of {@link Attribute}.
 */
public class JsonAdaptedAttribute {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Attribute's %s field is missing!";

    private final String key;
    private final List<String> values;

    /**
     * Constructs a {@code JsonAdaptedAttribute} with the given details.
     */
    @JsonCreator
    public JsonAdaptedAttribute(@JsonProperty("key") String key,
                                @JsonProperty("values") List<String> values) {
        this.key = key;
        this.values = values;
    }

    /**
     * Converts a given {@code Attribute} into this class for Jackson use.
     */
    public JsonAdaptedAttribute(Attribute source) {
        this.key = source.getKey();
        this.values = source.getValues().stream().collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted attribute object into the model's {@code Attribute} object.
     */
    public Attribute toModelType() throws IllegalValueException {
        if (key == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "key"));
        }

        if (values == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "values"));
        }

        Set<String> valueSet = new HashSet<>(values);
        return new Attribute(key, valueSet);
    }

}
