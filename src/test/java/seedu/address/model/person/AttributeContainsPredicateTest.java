package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AttributeContainsPredicateTest {

    @Test
    public void equals() {
        Map<String, Set<String>> firstPredicateKeywordMap = new HashMap<>();
        firstPredicateKeywordMap.put("subject", Set.of("math"));
        AttributeContainsPredicate firstPredicate = new AttributeContainsPredicate(firstPredicateKeywordMap);

        Map<String, Set<String>> secondPredicateKeywordMap = new HashMap<>();
        secondPredicateKeywordMap.put("subject", Set.of("science"));
        AttributeContainsPredicate secondPredicate = new AttributeContainsPredicate(secondPredicateKeywordMap);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AttributeContainsPredicate firstPredicateCopy = new AttributeContainsPredicate(firstPredicateKeywordMap);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personWithMatchingAttributes_returnsTrue() {
        Map<String, Set<String>> predicateKeywordMap = new HashMap<>();
        predicateKeywordMap.put("subject", Set.of("math"));
        AttributeContainsPredicate predicate = new AttributeContainsPredicate(predicateKeywordMap);

        Person person = new PersonBuilder().withAttributes(
                new Attribute("subject", Set.of("math", "science"))
        ).build();

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_personWithNonMatchingAttributes_returnsFalse() {
        Map<String, Set<String>> predicateKeywordMap = new HashMap<>();
        predicateKeywordMap.put("subject", Set.of("history"));
        AttributeContainsPredicate predicate = new AttributeContainsPredicate(predicateKeywordMap);

        Person person = new PersonBuilder().withAttributes(
                new Attribute("subject", Set.of("math", "science"))
        ).build();

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_personWithNoAttributes_returnsFalse() {
        Map<String, Set<String>> predicateKeywordMap = new HashMap<>();
        predicateKeywordMap.put("subject", Set.of("math"));
        AttributeContainsPredicate predicate = new AttributeContainsPredicate(predicateKeywordMap);

        Person person = new PersonBuilder().build();

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_emptyPredicate_returnsTrue() {
        Map<String, Set<String>> emptyPredicateKeywordMap = new HashMap<>();
        AttributeContainsPredicate predicate = new AttributeContainsPredicate(emptyPredicateKeywordMap);

        Person person = new PersonBuilder().build();

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_multipleAttributeFilters_allMustMatch() {
        Map<String, Set<String>> predicateKeywordMap = new HashMap<>();
        predicateKeywordMap.put("subject", Set.of("math"));
        predicateKeywordMap.put("age", Set.of("19"));
        AttributeContainsPredicate predicate = new AttributeContainsPredicate(predicateKeywordMap);

        // Person with both attributes - should match
        Person personWithBoth = new PersonBuilder().withAttributes(
                new Attribute("subject", Set.of("math")),
                new Attribute("age", Set.of("19"))
        ).build();
        assertTrue(predicate.test(personWithBoth));

        // Person with only one attribute - should not match
        Person personWithOne = new PersonBuilder().withAttributes(
                new Attribute("subject", Set.of("math"))
        ).build();
        assertFalse(predicate.test(personWithOne));
    }
}
