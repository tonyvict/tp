package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonContainsKeywordPredicate;

/**
 * Integration tests for {@code SearchCommand}.
 */
public class SearchCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        PersonContainsKeywordPredicate firstPredicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("first"));
        PersonContainsKeywordPredicate secondPredicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("second"));

        SearchCommand firstCommand = new SearchCommand(firstPredicate);
        SearchCommand secondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertEquals(firstCommand, firstCommand);

        // same values -> returns true
        SearchCommand firstCommandCopy = new SearchCommand(firstPredicate);
        assertEquals(firstCommand, firstCommandCopy);

        // different predicate -> returns false
        assertNotEquals(firstCommand, secondCommand);
    }

    @Test
    public void execute_noMatchingKeywords_showsEmptyMessage() {
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("nonexistent"));
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = SearchCommand.MESSAGE_SEARCH_EMPTY;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(0, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_matchingKeywords_multiplePersonsFound() {
        // "Kurz" -> CARL, "Meier" -> BENSON & DANIEL, "Elle" -> ELLE
        String expectedMessage = String.format(SearchCommand.MESSAGE_SEARCH_SUCCESS, 4);

        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Arrays.asList("Kurz", "Meier", "Elle"));
        SearchCommand command = new SearchCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Arrays.asList(BENSON, CARL, DANIEL, ELLE), model.getFilteredPersonList());
    }
}

