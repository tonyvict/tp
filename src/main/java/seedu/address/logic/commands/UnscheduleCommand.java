package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Lesson;
import seedu.address.model.person.LessonList;
import seedu.address.model.person.Person;

/**
 * Removes a lesson from an existing person in the address book.
 */
public class UnscheduleCommand extends Command {

    public static final String COMMAND_WORD = "unschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the lesson identified by the provided lesson index from the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) lesson/LESSON_INDEX "
            + "(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 lesson/1";

    public static final String MESSAGE_REMOVE_LESSON_SUCCESS = "Removed lesson %1$s from Person: %2$s";
    public static final String MESSAGE_NO_LESSONS = "The selected person has no lessons scheduled.";

    private static final Logger logger = LogsCenter.getLogger(UnscheduleCommand.class);

    private final Index personIndex;
    private final Index lessonIndex;

    /**
     * @param personIndex of the person in the filtered person list
     * @param lessonIndex of the lesson in the person's lesson list
     */
    public UnscheduleCommand(Index personIndex, Index lessonIndex) {
        requireAllNonNull(personIndex, lessonIndex);
        this.personIndex = personIndex;
        this.lessonIndex = lessonIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, personIndex, lessonIndex);
        logger.fine(() -> String.format("Executing UnscheduleCommand for person index %s, lesson index %s",
                personIndex.getOneBased(), lessonIndex.getOneBased()));

        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            logger.fine("UnscheduleCommand failed due to invalid person index.");
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());
        assert personToEdit != null : "Person to edit should not be null";
        LessonList lessonList = personToEdit.getLessonList();

        if (lessonList.isEmpty()) {
            logger.fine("UnscheduleCommand found no lessons to remove.");
            throw new CommandException(MESSAGE_NO_LESSONS);
        }

        if (lessonIndex.getZeroBased() >= lessonList.size()) {
            logger.fine("UnscheduleCommand failed due to invalid lesson index.");
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson lessonToRemove = lessonList.get(lessonIndex.getZeroBased());
        LessonList updatedLessonList = lessonList.remove(lessonToRemove);
        assert updatedLessonList.size() == lessonList.size() - 1
                : "Lesson list should decrease by exactly one lesson";

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getTags(),
                personToEdit.getAttributes(), updatedLessonList);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        logger.fine(() -> String.format("UnscheduleCommand removed lesson %s from person %s",
                lessonIndex.getOneBased(), personIndex.getOneBased()));

        return new CommandResult(String.format(MESSAGE_REMOVE_LESSON_SUCCESS,
                lessonToRemove, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnscheduleCommand)) {
            return false;
        }

        UnscheduleCommand otherCommand = (UnscheduleCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && lessonIndex.equals(otherCommand.lessonIndex);
    }
}
