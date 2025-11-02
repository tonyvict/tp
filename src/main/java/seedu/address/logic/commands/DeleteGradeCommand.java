package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a grade from a person.
 */
public class DeleteGradeCommand extends Command {

    public static final String COMMAND_WORD = "delgrade";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a grade from the person identified by index.\n"
            + "Parameters: INDEX sub/SUBJECT/ASSESSMENT\n"
            + "Example: " + COMMAND_WORD + " 1 sub/MATH/WA1";
    public static final String MESSAGE_SUCCESS = "Deleted grade from %1$s: %2$s";
    public static final String MESSAGE_GRADE_NOT_FOUND = "Grade not found for %1$s";

    private final Index index;
    private final String subject;
    private final String assessment;

    /**
     * Creates a DeleteGradeCommand to remove the specified grade.
     */
    public DeleteGradeCommand(Index index, String subject, String assessment) {
        this.index = index;
        this.subject = subject;
        this.assessment = assessment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        if (!personToEdit.getGradeList().hasGrade(subject, assessment)) {
            throw new CommandException(String.format(MESSAGE_GRADE_NOT_FOUND, subject + "/" + assessment));
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getRemark(),
                personToEdit.getTags(),
                personToEdit.getAttributes(),
                personToEdit.getLessonList(),
                personToEdit.getGradeList().removeGrade(subject, assessment));

        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson.getName(), subject + "/" + assessment));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteGradeCommand)) {
            return false;
        }
        DeleteGradeCommand otherCommand = (DeleteGradeCommand) other;
        return index.equals(otherCommand.index)
                && subject.equals(otherCommand.subject)
                && assessment.equals(otherCommand.assessment);
    }
}
