package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Lesson;
import seedu.address.model.person.LessonList;
import seedu.address.model.person.Person;

/**
 * Unmarks the attendance of a student for a lesson on the current day.
 */
public class UnmarkCommand extends Command {
    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks a student as absent for a lesson scheduled today.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_ATTENDANCE_SUCCESS = "Attendance unmarked: %1$s -> Not Present.";
    public static final String MESSAGE_NO_LESSON_TODAY_OR_ALREADY_UNMARKED = "No marked classes found for %1$s today!";

    private final Index targetIndex;

    /**
     * Creates an UnmarkCommand to unmark attendance for the specified {@code Person}.
     */
    public UnmarkCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnmark = lastShownList.get(targetIndex.getZeroBased());
        LessonList updatedLessonList = new LessonList();
        boolean lessonFoundAndMarked = false;

        for (Lesson lesson : personToUnmark.getLessonList().getLessons()) {
            if (lesson.getDate().equals(LocalDate.now()) && lesson.isPresent()) { // Only unmark if it's present
                Lesson unmarkedLesson = new Lesson(lesson.getStart(), lesson.getEnd(), lesson.getDate(),
                        lesson.getSub(), false);
                updatedLessonList = updatedLessonList.add(unmarkedLesson);
                lessonFoundAndMarked = true;
            } else {
                updatedLessonList = updatedLessonList.add(lesson);
            }
        }

        if (!lessonFoundAndMarked) {
            throw new CommandException(String.format(MESSAGE_NO_LESSON_TODAY_OR_ALREADY_UNMARKED,
                    personToUnmark.getName().fullName));
        }

        Person unmarkedPerson = new Person(
                personToUnmark.getName(), personToUnmark.getPhone(), personToUnmark.getEmail(),
                personToUnmark.getAddress(), personToUnmark.getRemark(), personToUnmark.getTags(),
                personToUnmark.getAttributes(), updatedLessonList);

        model.setPerson(personToUnmark, unmarkedPerson);

        return new CommandResult(String.format(MESSAGE_UNMARK_ATTENDANCE_SUCCESS, personToUnmark.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkCommand)) {
            return false;
        }

        UnmarkCommand otherUnmarkCommand = (UnmarkCommand) other;
        return targetIndex.equals(otherUnmarkCommand.targetIndex);
    }
}
