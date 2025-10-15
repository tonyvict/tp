package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Attribute;
import seedu.address.model.person.AttributeContainsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FilterCommand.
 */
public class FilterCommandTest {

    private Model model = new ModelManager();

    @Test
    public void equals() {
        Map<String, Set<String>> firstPredicateKeywordMap = new HashMap<>();
        firstPredicateKeywordMap.put("subject", Set.of("math"));
        AttributeContainsPredicate firstPredicate = new AttributeContainsPredicate(firstPredicateKeywordMap);

        Map<String, Set<String>> secondPredicateKeywordMap = new HashMap<>();
        secondPredicateKeywordMap.put("subject", Set.of("science"));
        AttributeContainsPredicate secondPredicate = new AttributeContainsPredicate(secondPredicateKeywordMap);

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() throws CommandException {
        String expectedMessage = String.format(FilterCommand.MESSAGE_SUCCESS, 0);
        Map<String, Set<String>> predicateKeywordMap = new HashMap<>();
        predicateKeywordMap.put("subject", Set.of("nonexistent"));
        AttributeContainsPredicate predicate = new AttributeContainsPredicate(predicateKeywordMap);
        FilterCommand command = new FilterCommand(predicate);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws CommandException {
        Person alice = new PersonBuilder().withName("Alice").withAttributes(
                new Attribute("subject", Arrays.asList("math","science"))
        ).build();
        Person bob = new PersonBuilder().withName("Bob").withAttributes(
                new Attribute("subject", Arrays.asList("science"))
        ).build();
        Person charlie = new PersonBuilder().withName("Charlie").withAttributes(
                new Attribute("subject", Arrays.asList("math"))
        ).build();

        model.addPerson(alice);
        model.addPerson(bob);
        model.addPerson(charlie);

        Map<String, Set<String>> predicateKeywordMap = new HashMap<>();
        predicateKeywordMap.put("subject", Set.of("math"));
        AttributeContainsPredicate predicate = new AttributeContainsPredicate(predicateKeywordMap);
        FilterCommand command = new FilterCommand(predicate);
        CommandResult result = command.execute(model);
        assertEquals(String.format(FilterCommand.MESSAGE_SUCCESS, 2), result.getFeedbackToUser());
    }

    @Test
    public void toStringMethod() {
        Map<String, Set<String>> predicateKeywordMap = new HashMap<>();
        predicateKeywordMap.put("subject", Set.of("math"));
        AttributeContainsPredicate predicate = new AttributeContainsPredicate(predicateKeywordMap);
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }
}
