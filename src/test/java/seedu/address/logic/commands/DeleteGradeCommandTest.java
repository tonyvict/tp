package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Grade;
import seedu.address.model.person.GradeList;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteGradeCommand.
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

        Set<DeleteGradeCommand.GradeKey> gradesToDelete = Set.of(
                new DeleteGradeCommand.GradeKey("MATH", "WA1"));
        DeleteGradeCommand command = new DeleteGradeCommand(INDEX_FIRST_PERSON, gradesToDelete);
        Person expectedPerson = removeGrades(originalPerson, gradesToDelete);

        String expectedMessage = String.format(DeleteGradeCommand.MESSAGE_DELETE_GRADE_SUCCESS,
                expectedPerson.getName(), "MATH/WA1");

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(originalPerson, expectedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleGrades_success() {
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withGrades(new Grade("MATH", "WA1", "89"), new Grade("SCIENCE", "Quiz1", "95"),
                        new Grade("MATH", "Quiz2", "85"))
                .build();
        model.setPerson(model.getFilteredPersonList().get(0), originalPerson);

        Set<DeleteGradeCommand.GradeKey> gradesToDelete = Set.of(
                new DeleteGradeCommand.GradeKey("MATH", "WA1"),
                new DeleteGradeCommand.GradeKey("SCIENCE", "Quiz1"));
        DeleteGradeCommand command = new DeleteGradeCommand(INDEX_FIRST_PERSON, gradesToDelete);
        Person expectedPerson = removeGrades(originalPerson, gradesToDelete);

        String expectedMessage = String.format(DeleteGradeCommand.MESSAGE_DELETE_GRADE_SUCCESS,
                expectedPerson.getName(), "MATH/WA1, SCIENCE/Quiz1");

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(originalPerson, expectedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistingGrade_noChange() {
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withGrades(new Grade("MATH", "WA1", "89"))
                .build();
        model.setPerson(model.getFilteredPersonList().get(0), originalPerson);

        Set<DeleteGradeCommand.GradeKey> gradesToDelete = Set.of(
                new DeleteGradeCommand.GradeKey("SCIENCE", "Quiz1"));
        DeleteGradeCommand command = new DeleteGradeCommand(INDEX_FIRST_PERSON, gradesToDelete);

        String expectedMessage = String.format(DeleteGradeCommand.MESSAGE_NO_GRADES_REMOVED,
                originalPerson.getName());

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_multipleGradesIncludesInvalid_successOnlyValidGradesReported() {
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withGrades(new Grade("MATH", "WA1", "89"), new Grade("SCIENCE", "Quiz1", "95"))
                .build();
        model.setPerson(model.getFilteredPersonList().get(0), originalPerson);

        Set<DeleteGradeCommand.GradeKey> gradesToDelete = Set.of(
                new DeleteGradeCommand.GradeKey("MATH", "WA1"),
                new DeleteGradeCommand.GradeKey("PHYSICS", "Test1"));
        DeleteGradeCommand command = new DeleteGradeCommand(INDEX_FIRST_PERSON, gradesToDelete);
        Person expectedPerson = removeGrades(originalPerson, Set.of(
                new DeleteGradeCommand.GradeKey("MATH", "WA1")));

        String expectedMessage = String.format(DeleteGradeCommand.MESSAGE_DELETE_GRADE_SUCCESS,
                expectedPerson.getName(), "MATH/WA1");

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(originalPerson, expectedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundsIndex = model.getFilteredPersonList().size() + 1;
        Set<DeleteGradeCommand.GradeKey> grades = Set.of(
                new DeleteGradeCommand.GradeKey("MATH", "WA1"));

        DeleteGradeCommand command = new DeleteGradeCommand(Index.fromOneBased(outOfBoundsIndex), grades);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Set<DeleteGradeCommand.GradeKey> grades1 = Set.of(
                new DeleteGradeCommand.GradeKey("MATH", "WA1"));
        Set<DeleteGradeCommand.GradeKey> grades2 = Set.of(
                new DeleteGradeCommand.GradeKey("SCIENCE", "Quiz1"));

        DeleteGradeCommand firstCommand = new DeleteGradeCommand(INDEX_FIRST_PERSON, grades1);
        DeleteGradeCommand secondCommand = new DeleteGradeCommand(INDEX_SECOND_PERSON, grades1);
        DeleteGradeCommand thirdCommand = new DeleteGradeCommand(INDEX_FIRST_PERSON, grades2);

        // same object -> true
        assertEquals(firstCommand, firstCommand);

        // same values -> true
        DeleteGradeCommand firstCommandCopy = new DeleteGradeCommand(INDEX_FIRST_PERSON, grades1);
        assertEquals(firstCommand, firstCommandCopy);

        // different index -> false
        assertNotEquals(firstCommand, secondCommand);

        // different grades -> false
        assertNotEquals(firstCommand, thirdCommand);

        // null -> false
        assertNotEquals(null, firstCommand);

        // different type -> false
        assertNotEquals(new Object(), firstCommand);
    }

    private Person removeGrades(Person person, Set<DeleteGradeCommand.GradeKey> keysToRemove) {
        GradeList updatedGradeList = person.getGradeList();

        for (DeleteGradeCommand.GradeKey key : keysToRemove) {
            if (person.getGradeList().hasGrade(key.getSubject(), key.getAssessment())) {
                updatedGradeList = updatedGradeList.removeGrade(key.getSubject(), key.getAssessment());
            }
        }

        return new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                person.getRemark(),
                new HashSet<>(person.getTags()),
                person.getAttributes(),
                person.getLessonList(),
                updatedGradeList
        );
    }
}
