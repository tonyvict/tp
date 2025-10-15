package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class AttributeTest {

    @Test
    public void constructor_validKeyAndValue_success() {
        Attribute attribute = new Attribute("subject", Arrays.asList("math"));
        assertEquals("subject", attribute.key);
        assertEquals(Collections.singleton("math"), attribute.values);
    }

    @Test
    public void constructor_keyWithWhitespace_trimsKey() {
        Attribute attribute = new Attribute("  subject  ", Arrays.asList("math"));
        assertEquals("subject", attribute.key);
    }

    @Test
    public void constructor_valueWithWhitespace_trimsValue() {
        Attribute attribute = new Attribute("subject", "  math  ");
        assertEquals(Collections.singleton("math"), attribute.values);
    }

    @Test
    public void constructor_keyCaseInsensitive_convertsToLowercase() {
        Attribute attribute = new Attribute("SUBJECT", Arrays.asList("math"));
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
    }

    @Test
    public void containsValue_exactMatch_returnsTrue() {
        Attribute attribute = new Attribute("subject", Arrays.asList("math"));
        assertTrue(attribute.containsValue("math"));
    }

    @Test
    public void containsValue_partialMatch_returnsTrue() {
        Attribute attribute = new Attribute("subject", Arrays.asList("math", "science"));
        assertTrue(attribute.containsValue("math"));
        assertTrue(attribute.containsValue("science"));
    }

    @Test
    public void containsValue_caseInsensitive_returnsTrue() {
        Attribute attribute = new Attribute("subject", Arrays.asList("Math"));
        assertTrue(attribute.containsValue("math"));
        assertTrue(attribute.containsValue("MATH"));
    }

    @Test
    public void containsValue_noMatch_returnsFalse() {
        Attribute attribute = new Attribute("subject", Arrays.asList("math"));
        assertFalse(attribute.containsValue("science"));
        assertFalse(attribute.containsValue("physics"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Attribute attribute = new Attribute("subject", Arrays.asList("math"));
        assertTrue(attribute.equals(attribute));
    }

    @Test
    public void equals_sameKeyAndValue_returnsTrue() {
        Attribute attribute1 = new Attribute("subject", Arrays.asList("math"));
        Attribute attribute2 = new Attribute("subject", Arrays.asList("math"));
        assertTrue(attribute1.equals(attribute2));
    }

    @Test
    public void equals_differentKey_returnsFalse() {
        Attribute attribute1 = new Attribute("subject", Arrays.asList("math"));
        Attribute attribute2 = new Attribute("age", Arrays.asList("math"));
        assertFalse(attribute1.equals(attribute2));
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Attribute attribute1 = new Attribute("subject", Arrays.asList("math"));
        Attribute attribute2 = new Attribute("subject", Arrays.asList("science"));
        assertFalse(attribute1.equals(attribute2));
    }

    @Test
    public void equals_null_returnsFalse() {
        Attribute attribute = new Attribute("subject", Arrays.asList("math"));
        assertFalse(attribute.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Attribute attribute = new Attribute("subject", Arrays.asList("math"));
        assertFalse(attribute.equals("not an attribute"));
    }

    @Test
    public void hashCode_sameKeyAndValue_returnsSameHashCode() {
        Attribute attribute1 = new Attribute("subject", Arrays.asList("math"));
        Attribute attribute2 = new Attribute("subject", Arrays.asList("math"));
        assertEquals(attribute1.hashCode(), attribute2.hashCode());
    }

    @Test
    public void hashCode_differentKey_returnsDifferentHashCode() {
        Attribute attribute1 = new Attribute("subject", Arrays.asList("math"));
        Attribute attribute2 = new Attribute("age", Arrays.asList("math"));
        assertNotEquals(attribute1.hashCode(), attribute2.hashCode());
    }

    @Test
    public void hashCode_differentValue_returnsDifferentHashCode() {
        Attribute attribute1 = new Attribute("subject", Arrays.asList("math"));
        Attribute attribute2 = new Attribute("subject", Arrays.asList("science"));
        assertNotEquals(attribute1.hashCode(), attribute2.hashCode());
    }

    @Test
    public void toString_returnsCorrectFormat() {
        Attribute attribute = new Attribute("subject", Arrays.asList("math"));
        String expected = "subject = math";
        assertEquals(expected, attribute.toString());
    }

    @Test
    public void toString_withSpecialCharacters_returnsCorrectFormat() {
        Attribute attribute = new Attribute("subject", Arrays.asList("math", "science"));
        String expected = "subject = science, math";
        assertEquals(expected, attribute.toString());
    }
}
