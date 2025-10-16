package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Attribute;

public class JsonAdaptedAttributeTest {

    private static final String VALID_KEY = "subject";
    private static final List<String> VALID_VALUE = Arrays.asList("math");
    private static final String INVALID_KEY = "";
    private static final List<String> INVALID_VALUE = new ArrayList<>();

    @Test
    public void toModelType_validAttributeDetails_returnsAttribute() throws Exception {
        JsonAdaptedAttribute attribute = new JsonAdaptedAttribute(VALID_KEY, VALID_VALUE);
        assertEquals(new Attribute(VALID_KEY, VALID_VALUE), attribute.toModelType());
    }

    @Test
    public void toModelType_validAttributeFromSource_returnsAttribute() throws Exception {
        Attribute source = new Attribute(VALID_KEY, VALID_VALUE);
        JsonAdaptedAttribute adaptedAttribute = new JsonAdaptedAttribute(source);
        assertEquals(source, adaptedAttribute.toModelType());
    }

    @Test
    public void toModelType_invalidKey_throwsIllegalValueException() {
        JsonAdaptedAttribute attribute = new JsonAdaptedAttribute(INVALID_KEY, VALID_VALUE);
        String expectedMessage = "Invalid attribute key: ";
        assertThrows(IllegalValueException.class, expectedMessage, attribute::toModelType);
    }

    @Test
    public void toModelType_invalidValue_throwsIllegalValueException() {
        JsonAdaptedAttribute attribute = new JsonAdaptedAttribute(VALID_KEY, INVALID_VALUE);
        String expectedMessage = "Invalid attribute value: []";
        assertThrows(IllegalValueException.class, expectedMessage, attribute::toModelType);
    }

    @Test
    public void toModelType_nullKey_throwsIllegalValueException() {
        JsonAdaptedAttribute attribute = new JsonAdaptedAttribute(null, VALID_VALUE);
        String expectedMessage = "Invalid attribute key: null";
        assertThrows(IllegalValueException.class, expectedMessage, attribute::toModelType);
    }

    @Test
    public void toModelType_nullValue_throwsIllegalValueException() {
        JsonAdaptedAttribute attribute = new JsonAdaptedAttribute(VALID_KEY, null);
        String expectedMessage = "Invalid attribute value: null";
        assertThrows(IllegalValueException.class, expectedMessage, attribute::toModelType);
    }

    @Test
    public void toModelType_whitespaceKey_throwsIllegalValueException() {
        JsonAdaptedAttribute attribute = new JsonAdaptedAttribute("   ", VALID_VALUE);
        String expectedMessage = "Invalid attribute key:    ";
        assertThrows(IllegalValueException.class, expectedMessage, attribute::toModelType);
    }

    @Test
    public void toModelType_whitespaceValue_throwsIllegalValueException() {
        JsonAdaptedAttribute attribute = new JsonAdaptedAttribute(VALID_KEY, Arrays.asList("   "));
        String expectedMessage = "Invalid attribute value: [   ]";
        assertThrows(IllegalValueException.class, expectedMessage, attribute::toModelType);
    }

    @Test
    public void toModelType_caseInsensitiveKey_convertsToLowercase() throws Exception {
        JsonAdaptedAttribute attribute = new JsonAdaptedAttribute("SUBJECT", VALID_VALUE);
        Attribute expected = new Attribute("subject", VALID_VALUE);
        assertEquals(expected, attribute.toModelType());
    }

    @Test
    public void toModelType_trimmedKeyAndValue_returnsTrimmedAttribute() throws Exception {
        JsonAdaptedAttribute attribute = new JsonAdaptedAttribute("  subject  ", Arrays.asList("  math  "));
        Attribute expected = new Attribute("subject", Arrays.asList("math"));
        assertEquals(expected, attribute.toModelType());
    }

    @Test
    public void constructor_withSourceAttribute_setsCorrectFields() throws Exception {
        Attribute source = new Attribute(VALID_KEY, VALID_VALUE);
        JsonAdaptedAttribute adapted = new JsonAdaptedAttribute(source);
        // Test that the adapted attribute can be converted back to the original
        assertEquals(source, adapted.toModelType());
    }

    @Test
    public void constructor_withJsonProperties_setsCorrectFields() throws Exception {
        JsonAdaptedAttribute adapted = new JsonAdaptedAttribute(VALID_KEY, VALID_VALUE);
        // Test that the adapted attribute can be converted to the expected model
        assertEquals(new Attribute(VALID_KEY, VALID_VALUE), adapted.toModelType());
    }
}
