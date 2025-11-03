package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_newPersonWithNoDigitsInPhone_successWithNoDigitsWarning() {
        Person personWithNoDigitsInPhone = new PersonBuilder().withPhone("no-digits-here").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(personWithNoDigitsInPhone);

        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(personWithNoDigitsInPhone))
                + "\n" + Phone.MESSAGE_WARNING_NO_DIGITS;

        assertCommandSuccess(new AddCommand(personWithNoDigitsInPhone), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_newPersonWithNonStandardCharsInPhone_successWithInvalidCharsWarning() {
        Person personWithInvalidCharsInPhone = new PersonBuilder().withPhone("911-is-a-joke").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(personWithInvalidCharsInPhone);

        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS,
                Messages.format(personWithInvalidCharsInPhone)) + "\n" + Phone.MESSAGE_WARNING_INVALID_CHARACTERS;

        assertCommandSuccess(new AddCommand(personWithInvalidCharsInPhone), model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonNameDifferentCaseAndWhitespace_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        String nameWithDifferentCaseAndWhitespace = personInList.getName().fullName
                .replaceAll("\\s+", "").toLowerCase();
        Person duplicatePerson = new PersonBuilder(personInList).withName(nameWithDifferentCaseAndWhitespace).build();
        assertCommandFailure(new AddCommand(duplicatePerson), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
