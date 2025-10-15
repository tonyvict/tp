package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Alice");
        List<String> secondPredicateKeywordList = Arrays.asList("Alice", "Bob");

        PersonContainsKeywordPredicate firstPredicate = new PersonContainsKeywordPredicate(firstPredicateKeywordList);
        PersonContainsKeywordPredicate secondPredicate = new PersonContainsKeywordPredicate(secondPredicateKeywordList);

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

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordPredicate(Arrays.asList("Carol", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // Partial phone match
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("9876"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("98765432").build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // Partial email match
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("john@example.com").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone/email but not name when empty fields
        predicate = new PersonContainsKeywordPredicate(Arrays.asList("12345"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("67890").build()));
    }
}

