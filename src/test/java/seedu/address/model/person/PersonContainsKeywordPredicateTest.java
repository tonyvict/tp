package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for {@link PersonContainsKeywordPredicate}.
 * <p>
 * These tests verify case-insensitive and partial substring matching
 * for name, phone, and email fields.
 */
public class PersonContainsKeywordPredicateTest {

    // ===========================================================
    // equals()
    // ===========================================================
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Alice");
        List<String> secondPredicateKeywordList = Arrays.asList("Alice", "Bob");

        PersonContainsKeywordPredicate firstPredicate =
                new PersonContainsKeywordPredicate(firstPredicateKeywordList);
        PersonContainsKeywordPredicate secondPredicate =
                new PersonContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordPredicate firstPredicateCopy =
                new PersonContainsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    // ===========================================================
    // name field
    // ===========================================================
    @Test
    public void test_nameContainsKeyword_returnsTrue() {
        // Partial substring match
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("Izz"));
        assertTrue(predicate.test(new PersonBuilder().withName("Izzie Crowe").build()));

        // Full match
        predicate = new PersonContainsKeywordPredicate(Collections.singletonList("Izzie"));
        assertTrue(predicate.test(new PersonBuilder().withName("Izzie Crowe").build()));

        // Case-insensitive match
        predicate = new PersonContainsKeywordPredicate(Collections.singletonList("izzie"));
        assertTrue(predicate.test(new PersonBuilder().withName("Izzie Crowe").build()));

        // Mixed-case keyword
        predicate = new PersonContainsKeywordPredicate(Collections.singletonList("CrOwE"));
        assertTrue(predicate.test(new PersonBuilder().withName("Izzie Crowe").build()));

        // Multiple keywords (OR logic)
        predicate = new PersonContainsKeywordPredicate(Arrays.asList("Bob", "Izz"));
        assertTrue(predicate.test(new PersonBuilder().withName("Izzie Crowe").build()));
    }

    @Test
    public void test_nameDoesNotContainKeyword_returnsFalse() {
        // Non-matching partial
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("Ann"));
        assertFalse(predicate.test(new PersonBuilder().withName("Izzie Crowe").build()));
    }

    // ===========================================================
    // phone field
    // ===========================================================
    @Test
    public void test_phoneContainsKeyword_returnsTrue() {
        // Partial match
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("9876"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("98765432").build()));

        // Case-insensitive not applicable but should still work
        predicate = new PersonContainsKeywordPredicate(Collections.singletonList("432"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("98765432").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeyword_returnsFalse() {
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("123"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("98765432").build()));
    }

    // ===========================================================
    // email field
    // ===========================================================
    @Test
    public void test_emailContainsKeyword_returnsTrue() {
        // Partial email match
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("john@example.com").build()));

        // Case-insensitive match
        predicate = new PersonContainsKeywordPredicate(Collections.singletonList("EXAMPLE"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("john@example.com").build()));

        // Match domain part
        predicate = new PersonContainsKeywordPredicate(Collections.singletonList(".com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("john@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeyword_returnsFalse() {
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("gmail"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("john@example.com").build()));
    }

    // ===========================================================
    // multiple fields
    // ===========================================================
    @Test
    public void test_matchesAnyField_returnsTrue() {
        // Keyword matches phone
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("9876"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("98765432")
                .withEmail("alice@example.com").build()));

        // Keyword matches email
        predicate = new PersonContainsKeywordPredicate(Collections.singletonList("example"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Bob")
                .withPhone("12345678")
                .withEmail("bob@example.com").build()));

        // Keyword matches name
        predicate = new PersonContainsKeywordPredicate(Collections.singletonList("boB"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Bob Alice")
                .withPhone("12345678")
                .withEmail("someone@xyz.com").build()));
    }

    @Test
    public void test_doesNotMatchAnyField_returnsFalse() {
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("xyz"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("12345678")
                .withEmail("bob@example.com").build()));
    }

    // ===========================================================
    // empty keywords
    // ===========================================================
    @Test
    public void test_emptyKeywordList_returnsTrue() {
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }
}
