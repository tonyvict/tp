package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Attribute;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteAttributeCommand.
 */
public class DeleteAttributeCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validKey_success() {
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withAttributes(new Attribute("subject", "Math"), new Attribute("age", "16"))
                .build();
        model.setPerson(model.getFilteredPersonList().get(0), originalPerson);

        Set<String> keysToDelete = Set.of("subject");
        DeleteAttributeCommand command = new DeleteAttributeCommand(INDEX_FIRST_PERSON, keysToDelete);
        Person expectedPerson = originalPerson.removeAttributesByKey(keysToDelete);

        String expectedMessage = String.format(DeleteAttributeCommand.MESSAGE_DELETE_ATTRIBUTE_SUCCESS,
                expectedPerson.getName(), keysToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(originalPerson, expectedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleKeys_success() {
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withAttributes(
                        new Attribute("subject", "Math"),
                        new Attribute("age", "16"),
                        new Attribute("cca", "Football"))
                .build();
        model.setPerson(model.getFilteredPersonList().get(0), originalPerson);

        Set<String> keysToDelete = Set.of("subject", "cca");
        DeleteAttributeCommand command = new DeleteAttributeCommand(INDEX_FIRST_PERSON, keysToDelete);
        Person expectedPerson = originalPerson.removeAttributesByKey(keysToDelete);

        String expectedMessage = String.format(DeleteAttributeCommand.MESSAGE_DELETE_ATTRIBUTE_SUCCESS,
                expectedPerson.getName(), keysToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(originalPerson, expectedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistingKey_noChange() throws Exception {
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withAttributes(new Attribute("subject", "Math"))
                .build();
        model.setPerson(model.getFilteredPersonList().get(0), originalPerson);

        Set<String> keysToDelete = Set.of("age");
        DeleteAttributeCommand command = new DeleteAttributeCommand(INDEX_FIRST_PERSON, keysToDelete);

        String expectedMessage = String.format(DeleteAttributeCommand.MESSAGE_NO_ATTRIBUTES_REMOVED,
                originalPerson.getName());
        String actualMessage = command.execute(model).getFeedbackToUser();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(originalPerson, model.getFilteredPersonList().get(0));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundsIndex = model.getFilteredPersonList().size() + 1;
        Set<String> keys = Set.of("subject");

        DeleteAttributeCommand command = new DeleteAttributeCommand(Index.fromOneBased(outOfBoundsIndex), keys);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Set<String> keys1 = Set.of("subject");
        Set<String> keys2 = Set.of("age");

        DeleteAttributeCommand firstCommand = new DeleteAttributeCommand(INDEX_FIRST_PERSON, keys1);
        DeleteAttributeCommand secondCommand = new DeleteAttributeCommand(INDEX_SECOND_PERSON, keys1);
        DeleteAttributeCommand thirdCommand = new DeleteAttributeCommand(INDEX_FIRST_PERSON, keys2);

        // same object -> true
        assertEquals(firstCommand, firstCommand);

        // same values -> true
        DeleteAttributeCommand firstCommandCopy = new DeleteAttributeCommand(INDEX_FIRST_PERSON, keys1);
        assertEquals(firstCommand, firstCommandCopy);

        // different index -> false
        assertNotEquals(firstCommand, secondCommand);

        // different keys -> false
        assertNotEquals(firstCommand, thirdCommand);

        // null -> false
        assertNotEquals(null, firstCommand);

        // different type -> false
        assertNotEquals(new Object(), firstCommand);
    }
}
