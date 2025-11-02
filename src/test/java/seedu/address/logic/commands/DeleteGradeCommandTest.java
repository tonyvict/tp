package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests and unit tests for DeleteGradeCommand.
 */
public class DeleteGradeCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validGrade_success() {
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withGrades(new Grade("MATH", "WA1", "89"), new Grade("SCIENCE", "Quiz1", "95"))
                .build();
        model.setPerson(model.getFilteredPersonList().get(0), originalPerson);

        DeleteGradeCommand command = new DeleteGradeCommand(INDEX_FIRST_PERSON, "MATH", "WA1");

        Person expectedPerson = new PersonBuilder(originalPerson)
                .build();
        expectedPerson = new Person(
                expectedPerson.getName(),
                expectedPerson.getPhone(),
                expectedPerson.getEmail(),
                expectedPerson.getAddress(),
                expectedPerson.getRemark(),
                expectedPerson.getTags(),
                expectedPerson.getAttributes(),
                expectedPerson.getLessonList(),
                expectedPerson.getGradeList().removeGrade("MATH", "WA1"));

        String expectedMessage = String.format(DeleteGradeCommand.MESSAGE_SUCCESS,
                expectedPerson.getName(), "MATH/WA1");

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(originalPerson, expectedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_gradeNotFound_throwsCommandException() {
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withGrades(new Grade("MATH", "WA1", "89"))
                .build();
        model.setPerson(model.getFilteredPersonList().get(0), originalPerson);

        DeleteGradeCommand command = new DeleteGradeCommand(INDEX_FIRST_PERSON, "SCIENCE", "Quiz1");

        String expectedMessage = String.format(DeleteGradeCommand.MESSAGE_GRADE_NOT_FOUND, "SCIENCE/Quiz1");

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundsIndex = model.getFilteredPersonList().size() + 1;
        DeleteGradeCommand command = new DeleteGradeCommand(
                Index.fromOneBased(outOfBoundsIndex), "MATH", "WA1");

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteGradeCommand firstCommand = new DeleteGradeCommand(INDEX_FIRST_PERSON, "MATH", "WA1");
        DeleteGradeCommand secondCommand = new DeleteGradeCommand(INDEX_SECOND_PERSON, "MATH", "WA1");
        DeleteGradeCommand thirdCommand = new DeleteGradeCommand(INDEX_FIRST_PERSON, "SCIENCE", "Quiz1");

        // same object -> returns true
        assertEquals(firstCommand, firstCommand);

        // same values -> returns true
        DeleteGradeCommand firstCommandCopy = new DeleteGradeCommand(INDEX_FIRST_PERSON, "MATH", "WA1");
        assertEquals(firstCommand, firstCommandCopy);

        // different types -> returns false
        assertNotEquals(firstCommand, 1);

        // null -> returns false
        assertNotEquals(null, firstCommand);

        // different index -> returns false
        assertNotEquals(firstCommand, secondCommand);

        // different subject/assessment -> returns false
        assertNotEquals(firstCommand, thirdCommand);
    }
}
