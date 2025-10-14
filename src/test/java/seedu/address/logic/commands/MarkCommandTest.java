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
 * Contains integration tests (interaction with the Model) and unit tests for MarkCommand.
 */
public class MarkCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_markPersonWithLessonToday_success() {
        // Setup: Create a person with a lesson scheduled for today
        Lesson lessonToday = new Lesson("15:00", "16:00", LocalDate.now().toString(), "Math", false);
        LessonList lessonList = new LessonList().add(lessonToday);
        Person personToMark = new PersonBuilder().withName("Markable Person").withLessonList(lessonList).build();
        model.addPerson(personToMark);

        // The new person is the last one in the list
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        MarkCommand markCommand = new MarkCommand(lastPersonIndex);

        Lesson markedLesson = new Lesson(lessonToday.start, lessonToday.end, lessonToday.date, lessonToday.sub, true);
        LessonList updatedLessonList = new LessonList().add(markedLesson);
        Person markedPerson = new PersonBuilder(personToMark).withLessonList(updatedLessonList).build();

        // Expected model after marking
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToMark, markedPerson);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS, markedPerson.getName().fullName);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noLessonToday_throwsCommandException() {
        // Person at this index (Benson) has no lessons scheduled
        MarkCommand markCommand = new MarkCommand(INDEX_SECOND_PERSON);
        Person personToMark = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        String expectedMessage = String.format(MarkCommand.MESSAGE_NO_LESSON_TODAY, personToMark.getName().fullName);

        assertCommandFailure(markCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkCommand markCommand = new MarkCommand(outOfBoundIndex);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyMarked_throwsCommandException() {
        // Setup: Create a person with a lesson today that is already marked present
        Lesson lessonToday = new Lesson("15:00", "16:00", LocalDate.now().toString(), "Math", true);
        LessonList lessonList = new LessonList().add(lessonToday);
        Person personToMark = new PersonBuilder().withName("Already Marked").withLessonList(lessonList).build();
        model.addPerson(personToMark);

        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        MarkCommand markCommand = new MarkCommand(lastPersonIndex);

        String expectedMessage = String.format(MarkCommand.MESSAGE_NO_LESSON_TODAY, personToMark.getName().fullName);

        // The command should fail because it only looks for lessons where isPresent is false
        assertCommandFailure(markCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        MarkCommand markFirstCommand = new MarkCommand(INDEX_FIRST_PERSON);
        MarkCommand markSecondCommand = new MarkCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkCommand markFirstCommandCopy = new MarkCommand(INDEX_FIRST_PERSON);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }
}
