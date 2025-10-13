package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Lesson;
import seedu.address.model.person.Person;

/**
 * Changes the lesson of an existing person in the address book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the lesson of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing lesson will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "start/ [START TIME] end/ [END TIME} "
            + "date/ [DATE] sub/ [SUBJECT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "start/ 09:30 end/ 11:30 "
            + "date/ 2025-09-20 sub/ Maths";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Start: %2$s, End: %3$s, Date: %4$s, Sub: %5$s";
    public static final String MESSAGE_ADD_LESSON_SUCCESS = "Scheduled Lesson to Person: %1$s";

    private final Index index;
    private final Lesson lesson;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param lesson of the person to be updated to
     */
    public ScheduleCommand(Index index, Lesson lesson) {
        requireAllNonNull(index, lesson);

        this.index = index;
        this.lesson = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(),
                personToEdit.getTags(), personToEdit.getLessonList().add(lesson));

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = MESSAGE_ADD_LESSON_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCommand)) {
            return false;
        }

        ScheduleCommand e = (ScheduleCommand) other;
        return index.equals(e.index)
                && lesson.equals(e.lesson);
    }
}
