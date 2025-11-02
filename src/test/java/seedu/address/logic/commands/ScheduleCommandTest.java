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
 * Contains integration tests (interaction with the Model) and unit tests for ScheduleCommand.
 */
public class ScheduleCommandTest {

    private static final String VALID_START_TIME = "14:00";
    private static final String VALID_END_TIME = "15:00";
    private static final String VALID_DATE = "2025-09-20";
    private static final String VALID_SUBJECT = "Mathematics";

    private static final String ANOTHER_START_TIME = "16:00";
    private static final String ANOTHER_END_TIME = "17:00";
    private static final String ANOTHER_DATE = "2025-09-21";
    private static final String ANOTHER_SUBJECT = "Science";

    private static final String OVERLAPPING_START_TIME = "14:30";
    private static final String OVERLAPPING_END_TIME = "15:30";
    private static final String OVERLAPPING_SUBJECT = "English";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLessonUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Lesson lessonToAdd = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DATE, VALID_SUBJECT);

        Person editedPerson = new PersonBuilder(firstPerson)
                .withLesson(lessonToAdd)
                .build();

        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, lessonToAdd);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_ADD_LESSON_SUCCESS, format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addCrossDayLesson_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Lesson lessonToAdd = new Lesson("22:00", "01:00", "2025-09-20", "2025-09-21", "Overnight Camp", false);

        Person editedPerson = new PersonBuilder(firstPerson)
                .withLesson(lessonToAdd)
                .build();

        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, lessonToAdd);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_ADD_LESSON_SUCCESS, format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addMultipleLessonsUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Lesson firstLesson = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DATE, VALID_SUBJECT);
        Lesson secondLesson = new Lesson(ANOTHER_START_TIME, ANOTHER_END_TIME, ANOTHER_DATE, ANOTHER_SUBJECT);

        // Add first lesson
        Person personWithFirstLesson = new PersonBuilder(firstPerson)
                .withLesson(firstLesson)
                .build();

        model.setPerson(firstPerson, personWithFirstLesson);

        // Add second lesson
        Person personWithBothLessons = new PersonBuilder(personWithFirstLesson)
                .withLesson(secondLesson)
                .build();

        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, secondLesson);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_ADD_LESSON_SUCCESS,
                format(personWithBothLessons));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithFirstLesson, personWithBothLessons);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateLesson_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Lesson lessonToAdd = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DATE, VALID_SUBJECT);

        // Add the lesson for the first time
        Person personWithLesson = new PersonBuilder(firstPerson)
                .withLesson(lessonToAdd)
                .build();
        model.setPerson(firstPerson, personWithLesson);

        // Try to add the same lesson again
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, lessonToAdd);

        assertCommandFailure(scheduleCommand, model, ScheduleCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_duplicateLessonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Lesson lessonToAdd = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DATE, VALID_SUBJECT);

        // Add the lesson for the first time
        Person personWithLesson = new PersonBuilder(firstPerson)
                .withLesson(lessonToAdd)
                .build();
        model.setPerson(firstPerson, personWithLesson);

        // Try to add the same lesson again in filtered list
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, lessonToAdd);

        assertCommandFailure(scheduleCommand, model, ScheduleCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_overlappingLesson_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Lesson existingLesson = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DATE, VALID_SUBJECT);

        Person personWithLesson = new PersonBuilder(firstPerson).withLesson(existingLesson).build();
        model.setPerson(firstPerson, personWithLesson);

        Lesson overlappingLesson =
                new Lesson(OVERLAPPING_START_TIME, OVERLAPPING_END_TIME, VALID_DATE, OVERLAPPING_SUBJECT);
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, overlappingLesson);

        assertCommandFailure(scheduleCommand, model, ScheduleCommand.MESSAGE_OVERLAPPING_LESSON);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Lesson lessonToAdd = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DATE, VALID_SUBJECT);

        Person editedPerson = new PersonBuilder(firstPerson)
                .withLesson(lessonToAdd)
                .build();

        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, lessonToAdd);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_ADD_LESSON_SUCCESS, format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Lesson lessonToAdd = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DATE, VALID_SUBJECT);
        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundIndex, lessonToAdd);

        assertCommandFailure(scheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Schedule lesson for filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Lesson lessonToAdd = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DATE, VALID_SUBJECT);
        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundIndex, lessonToAdd);

        assertCommandFailure(scheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Lesson firstLesson = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DATE, VALID_SUBJECT);
        Lesson secondLesson = new Lesson(ANOTHER_START_TIME, ANOTHER_END_TIME, ANOTHER_DATE, ANOTHER_SUBJECT);

        final ScheduleCommand standardCommand = new ScheduleCommand(INDEX_FIRST_PERSON, firstLesson);

        // same values -> returns true
        ScheduleCommand commandWithSameValues = new ScheduleCommand(INDEX_FIRST_PERSON, firstLesson);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ScheduleCommand(INDEX_SECOND_PERSON, firstLesson)));

        // different lesson -> returns false
        assertFalse(standardCommand.equals(new ScheduleCommand(INDEX_FIRST_PERSON, secondLesson)));
    }
}
