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
 * Marks the attendance of a student for a lesson on the current day.
 */
public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a student as present for a lesson scheduled today.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Attendance marked: %1$s -> Present.";
    public static final String MESSAGE_NO_LESSON_TODAY = "No classes found for %1$s today!";

    private final Index targetIndex;

    /**
     * Creates a MarkCommand to mark attendance for the specified {@code Person}.
     * @param targetIndex of the person in the filtered person list to mark attendance
     */
    public MarkCommand(Index targetIndex) {
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

        Person personToMark = lastShownList.get(targetIndex.getZeroBased());
        LessonList updatedLessonList = new LessonList();
        boolean lessonFound = false;

        for (Lesson lesson : personToMark.getLessonList().getLessons()) {
            if (lesson.date.equals(LocalDate.now()) && !lesson.isPresent) {
                Lesson markedLesson = new Lesson(lesson.start, lesson.end, lesson.date, lesson.sub, true);
                updatedLessonList = updatedLessonList.add(markedLesson);
                lessonFound = true;
            } else {
                updatedLessonList = updatedLessonList.add(lesson);
            }
        }

        if (!lessonFound) {
            throw new CommandException(String.format(MESSAGE_NO_LESSON_TODAY, personToMark.getName().fullName));
        }

        Person markedPerson = new Person(
                personToMark.getName(), personToMark.getPhone(), personToMark.getEmail(),
                personToMark.getAddress(), personToMark.getRemark(), personToMark.getTags(),
                personToMark.getAttributes(), updatedLessonList);

        model.setPerson(personToMark, markedPerson);

        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS, personToMark.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherMarkCommand = (MarkCommand) other;
        return targetIndex.equals(otherMarkCommand.targetIndex);
    }
}
