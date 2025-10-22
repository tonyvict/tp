package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.format;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Lesson;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnscheduleCommand.
 */
public class UnscheduleCommandTest {

    private static final Lesson FIRST_LESSON =
            new Lesson("09:00", "10:00", "2025-01-01", "Mathematics", false);
    private static final Lesson SECOND_LESSON =
            new Lesson("11:00", "12:00", "2025-01-02", "Science", false);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removeLessonUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithLessons = new PersonBuilder(firstPerson)
                .withLesson(FIRST_LESSON)
                .withLesson(SECOND_LESSON)
                .build();
        model.setPerson(firstPerson, personWithLessons);

        Person expectedPerson = new PersonBuilder(personWithLessons)
                .withLessonList(personWithLessons.getLessonList().remove(FIRST_LESSON))
                .build();

        UnscheduleCommand unscheduleCommand = new UnscheduleCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        String expectedMessage = String.format(UnscheduleCommand.MESSAGE_REMOVE_LESSON_SUCCESS,
                FIRST_LESSON, format(expectedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithLessons, expectedPerson);

        assertCommandSuccess(unscheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnscheduleCommand unscheduleCommand = new UnscheduleCommand(outOfBoundIndex, Index.fromOneBased(1));

        assertCommandFailure(unscheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithLesson = new PersonBuilder(firstPerson)
                .withLesson(FIRST_LESSON)
                .build();
        model.setPerson(firstPerson, personWithLesson);

        UnscheduleCommand unscheduleCommand = new UnscheduleCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2));

        assertCommandFailure(unscheduleCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noLessons_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnscheduleCommand unscheduleCommand = new UnscheduleCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));

        assertCommandFailure(unscheduleCommand, model, UnscheduleCommand.MESSAGE_NO_LESSONS);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnscheduleCommand unscheduleCommand = new UnscheduleCommand(outOfBoundIndex, Index.fromOneBased(1));

        assertCommandFailure(unscheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnscheduleCommand firstCommand = new UnscheduleCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        UnscheduleCommand secondCommand = new UnscheduleCommand(INDEX_SECOND_PERSON, Index.fromOneBased(2));

        // same values -> returns true
        UnscheduleCommand firstCommandCopy = new UnscheduleCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        assertTrue(firstCommand.equals(firstCommandCopy));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different types -> returns false
        assertFalse(firstCommand.equals(new ClearCommand()));

        // different person index -> returns false
        assertFalse(firstCommand.equals(secondCommand));

        // different lesson index -> returns false
        UnscheduleCommand differentLessonIndex =
                new UnscheduleCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2));
        assertFalse(firstCommand.equals(differentLessonIndex));
    }
}
