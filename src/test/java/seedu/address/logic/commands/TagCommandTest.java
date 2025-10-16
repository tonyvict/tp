package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Attribute;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code TagCommand}.
 */
public class TagCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addNewAttribute_success() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(0);

        Attribute newAttribute = new Attribute("subject", "math");
        Set<Attribute> attributesToAdd = Set.of(newAttribute);

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, attributesToAdd);

        Person expectedEditedPerson = new PersonBuilder(personToEdit)
                .withAttributes(attributesToAdd) // use Set overload
                .build();

        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_ATTRIBUTE_SUCCESS,
                expectedEditedPerson.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, expectedEditedPerson);

        CommandResult result = tagCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }


    @Test
    public void execute_addMultipleAttributes_success() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(0);

        Attribute attr1 = new Attribute("subject", "science");
        Attribute attr2 = new Attribute("age", "16");
        Set<Attribute> attributesToAdd = new HashSet<>();
        attributesToAdd.add(attr1);
        attributesToAdd.add(attr2);

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, attributesToAdd);

        Person expectedEditedPerson = new PersonBuilder(personToEdit)
                .withAttributes(attributesToAdd)
                .build();

        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_ATTRIBUTE_SUCCESS,
                expectedEditedPerson.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, expectedEditedPerson);

        CommandResult result = tagCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundIndex = model.getFilteredPersonList().size() + 1;
        TagCommand tagCommand = new TagCommand(Index.fromOneBased(outOfBoundIndex),
                Set.of(new Attribute("subject", "math")));

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> tagCommand.execute(model));
    }

    @Test
    public void equals() {
        Set<Attribute> firstAttributes = Set.of(new Attribute("subject", "math"));
        Set<Attribute> secondAttributes = Set.of(new Attribute("subject", "english"));

        TagCommand tagFirstCommand = new TagCommand(INDEX_FIRST_PERSON, firstAttributes);
        TagCommand tagSecondCommand = new TagCommand(INDEX_FIRST_PERSON, secondAttributes);

        // same object → true
        assertTrue(tagFirstCommand.equals(tagFirstCommand));

        // same values → true
        TagCommand tagFirstCommandCopy = new TagCommand(INDEX_FIRST_PERSON, firstAttributes);
        assertTrue(tagFirstCommand.equals(tagFirstCommandCopy));

        // different types → false
        assertFalse(tagFirstCommand.equals(1));

        // null → false
        assertFalse(tagFirstCommand.equals(null));

        // different attributes → false
        assertFalse(tagFirstCommand.equals(tagSecondCommand));
    }
}
