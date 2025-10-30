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

public class OpenCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToOpen = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personToOpen.setExpanded(false); // Ensure it's closed initially
        OpenCommand openCommand = new OpenCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(OpenCommand.MESSAGE_OPEN_PERSON_SUCCESS, personToOpen.getName());

        // The model is not changed in terms of data, only UI state, so expectedModel is the same.
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(openCommand, model, expectedMessage, expectedModel);
        assertTrue(personToOpen.isExpanded());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        OpenCommand openCommand = new OpenCommand(outOfBoundIndex);

        assertCommandFailure(openCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_cardAlreadyOpen_throwsCommandException() {
        // Get the first person and manually set their card to be open
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        firstPerson.setExpanded(true);

        // Attempt to open the same card again
        OpenCommand openCommand = new OpenCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(openCommand, model, OpenCommand.MESSAGE_CARD_ALREADY_OPEN);
    }

    @Test
    public void equals() {
        OpenCommand openFirstCommand = new OpenCommand(INDEX_FIRST_PERSON);
        OpenCommand openSecondCommand = new OpenCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(openFirstCommand.equals(openFirstCommand));

        // same values -> returns true
        OpenCommand openFirstCommandCopy = new OpenCommand(INDEX_FIRST_PERSON);
        assertTrue(openFirstCommand.equals(openFirstCommandCopy));

        // different types -> returns false
        assertFalse(openFirstCommand.equals(1));

        // null -> returns false
        assertFalse(openFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(openFirstCommand.equals(openSecondCommand));
    }
}
