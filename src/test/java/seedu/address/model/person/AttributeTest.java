package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AttributeTest {

    @Test
    public void constructor_validKeyAndValue_success() {
        Attribute attribute = new Attribute("subject", "math");
        assertEquals("subject", attribute.key);
        assertEquals("math", attribute.value);
    }

    @Test
    public void constructor_keyWithWhitespace_trimsKey() {
        Attribute attribute = new Attribute("  subject  ", "math");
        assertEquals("subject", attribute.key);
    }

    @Test
    public void constructor_valueWithWhitespace_trimsValue() {
        Attribute attribute = new Attribute("subject", "  math  ");
        assertEquals("math", attribute.value);
    }

    @Test
    public void constructor_keyCaseInsensitive_convertsToLowercase() {
        Attribute attribute = new Attribute("SUBJECT", "math");
        assertEquals("subject", attribute.key);
    }

    @Test
    public void isValidKey_validKey_returnsTrue() {
        assertTrue(Attribute.isValidKey("subject"));
        assertTrue(Attribute.isValidKey("age"));
        assertTrue(Attribute.isValidKey("cca"));
    }

    @Test
    public void isValidKey_invalidKey_returnsFalse() {
        assertFalse(Attribute.isValidKey(""));
        assertFalse(Attribute.isValidKey("   "));
        assertFalse(Attribute.isValidKey(null));
    }

    @Test
    public void isValidValue_validValue_returnsTrue() {
        assertTrue(Attribute.isValidValue("math"));
        assertTrue(Attribute.isValidValue("19"));
        assertTrue(Attribute.isValidValue("football"));
    }

    @Test
    public void isValidValue_invalidValue_returnsFalse() {
        assertFalse(Attribute.isValidValue(""));
        assertFalse(Attribute.isValidValue("   "));
        assertFalse(Attribute.isValidValue(null));
    }

    @Test
    public void containsValue_exactMatch_returnsTrue() {
        Attribute attribute = new Attribute("subject", "math");
        assertTrue(attribute.containsValue("math"));
    }

    @Test
    public void containsValue_partialMatch_returnsTrue() {
        Attribute attribute = new Attribute("subject", "math,science");
        assertTrue(attribute.containsValue("math"));
        assertTrue(attribute.containsValue("science"));
    }

    @Test
    public void containsValue_caseInsensitive_returnsTrue() {
        Attribute attribute = new Attribute("subject", "Math");
        assertTrue(attribute.containsValue("math"));
        assertTrue(attribute.containsValue("MATH"));
    }

    @Test
    public void containsValue_noMatch_returnsFalse() {
        Attribute attribute = new Attribute("subject", "math");
        assertFalse(attribute.containsValue("science"));
        assertFalse(attribute.containsValue("physics"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Attribute attribute = new Attribute("subject", "math");
        assertTrue(attribute.equals(attribute));
    }

    @Test
    public void equals_sameKeyAndValue_returnsTrue() {
        Attribute attribute1 = new Attribute("subject", "math");
        Attribute attribute2 = new Attribute("subject", "math");
        assertTrue(attribute1.equals(attribute2));
    }

    @Test
    public void equals_differentKey_returnsFalse() {
        Attribute attribute1 = new Attribute("subject", "math");
        Attribute attribute2 = new Attribute("age", "math");
        assertFalse(attribute1.equals(attribute2));
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Attribute attribute1 = new Attribute("subject", "math");
        Attribute attribute2 = new Attribute("subject", "science");
        assertFalse(attribute1.equals(attribute2));
    }

    @Test
    public void equals_null_returnsFalse() {
        Attribute attribute = new Attribute("subject", "math");
        assertFalse(attribute.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Attribute attribute = new Attribute("subject", "math");
        assertFalse(attribute.equals("not an attribute"));
    }

    @Test
    public void hashCode_sameKeyAndValue_returnsSameHashCode() {
        Attribute attribute1 = new Attribute("subject", "math");
        Attribute attribute2 = new Attribute("subject", "math");
        assertEquals(attribute1.hashCode(), attribute2.hashCode());
    }

    @Test
    public void hashCode_differentKey_returnsDifferentHashCode() {
        Attribute attribute1 = new Attribute("subject", "math");
        Attribute attribute2 = new Attribute("age", "math");
        assertNotEquals(attribute1.hashCode(), attribute2.hashCode());
    }

    @Test
    public void hashCode_differentValue_returnsDifferentHashCode() {
        Attribute attribute1 = new Attribute("subject", "math");
        Attribute attribute2 = new Attribute("subject", "science");
        assertNotEquals(attribute1.hashCode(), attribute2.hashCode());
    }

    @Test
    public void toString_returnsCorrectFormat() {
        Attribute attribute = new Attribute("subject", "math");
        String expected = "[subject=math]";
        assertEquals(expected, attribute.toString());
    }

    @Test
    public void toString_withSpecialCharacters_returnsCorrectFormat() {
        Attribute attribute = new Attribute("subject", "math,science");
        String expected = "[subject=math,science]";
        assertEquals(expected, attribute.toString());
    }
}
