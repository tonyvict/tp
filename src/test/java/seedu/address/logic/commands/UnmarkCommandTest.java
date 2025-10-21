package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Lesson;
import seedu.address.model.person.LessonList;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnmarkCommand.
 */
public class UnmarkCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unmarkPersonWithLessonToday_success() {
        // Setup: Create a person with a lesson scheduled for today and marked present
        Lesson lessonToday = new Lesson("15:00", "16:00", LocalDate.now().toString(), "Math", true);
        Person personToUnmark = new PersonBuilder().withName("Unmarkable Person")
                .withLessonList(new LessonList().add(lessonToday)).build();
        model.addPerson(personToUnmark);

        Lesson unmarkedLesson = new Lesson(lessonToday.getStart(), lessonToday.getEnd(), lessonToday.getDate(), lessonToday.getSub(), false);
        Person unmarkedPerson = new PersonBuilder(personToUnmark)
                .withLessonList(new LessonList().add(unmarkedLesson)).build();

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_ATTENDANCE_SUCCESS,
                unmarkedPerson.getName().fullName);

        // Expected model after unmarking
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPerson(personToUnmark); // Add original person to expected model
        expectedModel.setPerson(personToUnmark, unmarkedPerson);

        // Actual model setup
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        UnmarkCommand unmarkCommand = new UnmarkCommand(lastPersonIndex);

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noMarkedLessonToday_throwsCommandException() {
        // Setup: Create a person with an unmarked lesson today
        Lesson lessonToday = new Lesson("15:00", "16:00", LocalDate.now().toString(), "Math", false);
        Person personToUnmark = new PersonBuilder().withName("Unmarkable Person")
                .withLessonList(new LessonList().add(lessonToday)).build();
        model.addPerson(personToUnmark);

        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        UnmarkCommand unmarkCommand = new UnmarkCommand(lastPersonIndex);

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_NO_LESSON_TODAY_OR_ALREADY_UNMARKED,
                personToUnmark.getName().fullName);

        assertCommandFailure(unmarkCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnmarkCommand unmarkCommand = new UnmarkCommand(outOfBoundIndex);

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnmarkCommand unmarkFirstCommand = new UnmarkCommand(INDEX_FIRST_PERSON);
        UnmarkCommand unmarkSecondCommand = new UnmarkCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkCommand unmarkFirstCommandCopy = new UnmarkCommand(INDEX_FIRST_PERSON);
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }
}