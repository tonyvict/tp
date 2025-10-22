package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Grade;
import seedu.address.model.person.GradeList;
import seedu.address.model.person.Person;

/**
 * Adds or updates grades for a specified person in the address book.
 */
public class GradeCommand extends Command {

    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or updates grades for the specified "
            + "student identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) sub/SUBJECT/ASSESSMENT/SCORE "
            + "[sub/SUBJECT2/ASSESSMENT2/SCORE2]...\n"
            + "Example: " + COMMAND_WORD + " 2 sub/MATH/WA1/89 sub/SCIENCE/Quiz1/95";

    public static final String MESSAGE_ADD_GRADE_SUCCESS = "Grades Updated: %1$s; Phone: %2$s; Email: %3$s; "
            + "Address: %4$s; Grades: %5$s";

    private final Index index;
    private final Set<Grade> gradesToAdd;

    /**
     * Creates a GradeCommand to add the specified {@code Grade}s to a person.
     */
    public GradeCommand(Index index, Set<Grade> gradesToAdd) {
        requireNonNull(index);
        requireNonNull(gradesToAdd);
        this.index = index;
        this.gradesToAdd = gradesToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createGradedPerson(personToEdit, gradesToAdd);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ADD_GRADE_SUCCESS,
                editedPerson.getName(),
                editedPerson.getPhone(),
                editedPerson.getEmail(),
                editedPerson.getAddress(),
                editedPerson.getGradeList()));
    }

    /**
     * Returns a new {@code Person} with the added or updated grades.
     */
    private static Person createGradedPerson(Person personToEdit, Set<Grade> gradesToAdd) {
        GradeList updatedGradeList = personToEdit.getGradeList();

        // Add each grade to the grade list
        for (Grade newGrade : gradesToAdd) {
            updatedGradeList = updatedGradeList.addGrade(newGrade);
        }

        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getRemark(),
                personToEdit.getTags(),
                personToEdit.getAttributes(),
                personToEdit.getLessonList(),
                updatedGradeList
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof GradeCommand
                && index.equals(((GradeCommand) other).index)
                && gradesToAdd.equals(((GradeCommand) other).gradesToAdd));
    }
}
