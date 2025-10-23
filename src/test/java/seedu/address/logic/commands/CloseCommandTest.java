package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class CloseCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToClose = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personToClose.setExpanded(true); // Ensure it's open initially
        CloseCommand closeCommand = new CloseCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(CloseCommand.MESSAGE_CLOSE_PERSON_SUCCESS, personToClose.getName());

        // The model is not changed in terms of data, only UI state, so expectedModel is the same.
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(closeCommand, model, expectedMessage, expectedModel);
        assertFalse(personToClose.isExpanded());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CloseCommand closeCommand = new CloseCommand(outOfBoundIndex);

        assertCommandFailure(closeCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CloseCommand closeFirstCommand = new CloseCommand(INDEX_FIRST_PERSON);
        CloseCommand closeSecondCommand = new CloseCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(closeFirstCommand.equals(closeFirstCommand));

        // same values -> returns true
        CloseCommand closeFirstCommandCopy = new CloseCommand(INDEX_FIRST_PERSON);
        assertTrue(closeFirstCommand.equals(closeFirstCommandCopy));

        // different types -> returns false
        assertFalse(closeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(closeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(closeFirstCommand.equals(closeSecondCommand));
    }
}