package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only, fails because it starts with a space
        assertFalse(Phone.isValidPhone("    ")); // spaces only, fails because it starts with a space

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
        assertTrue(Phone.isValidPhone("91")); // less than 3 numbers is now valid
        assertTrue(Phone.isValidPhone("phone")); // non-numeric is now valid
        assertTrue(Phone.isValidPhone("9011p041")); // alphabets within digits is now valid
        assertTrue(Phone.isValidPhone("9312 1534")); // spaces within digits is now valid
        assertTrue(Phone.isValidPhone("(home)9312 1534 (office)1234-1234")); // spaces within brackets is now valid
        assertTrue(Phone.isValidPhone("+65 01203405302")); // plus signs are now valid
    }

    @Test
    public void hasDigits() {
        // No digits -> returns false
        assertFalse(Phone.hasDigits("phone"));
        assertFalse(Phone.hasDigits("()+- "));
        assertFalse(Phone.hasDigits("abc-def"));

        // Contains digits -> returns true
        assertTrue(Phone.hasDigits("911"));
        assertTrue(Phone.hasDigits("phone911"));
        assertTrue(Phone.hasDigits("911phone"));
        assertTrue(Phone.hasDigits("ph1one"));
    }

    @Test
    public void containsNonStandardChars() {
        // Only digits -> returns false
        assertFalse(Phone.containsNonStandardChars("911"));
        assertFalse(Phone.containsNonStandardChars("1234567890"));

        // Contains non-digit characters -> returns true
        assertTrue(Phone.containsNonStandardChars("9312 1534")); // space
        assertTrue(Phone.containsNonStandardChars("+6512345678")); // +
        assertTrue(Phone.containsNonStandardChars("(911)")); // ()
        assertTrue(Phone.containsNonStandardChars("phone")); // alphabets
        assertTrue(Phone.containsNonStandardChars("9011p041")); // alphabet within digits
        assertTrue(Phone.containsNonStandardChars("911#")); // special character #
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }
}
