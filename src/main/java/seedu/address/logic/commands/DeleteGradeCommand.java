package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.GradeList;
import seedu.address.model.person.Person;

/**
 * Deletes specified grades from an existing person in the address book.
 */
public class DeleteGradeCommand extends Command {

    public static final String COMMAND_WORD = "delgrade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the specified grade(s) of the person "
            + "identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "sub/SUBJECT/ASSESSMENT [sub/SUBJECT2/ASSESSMENT2]...\n"
            + "Example: " + COMMAND_WORD + " 1 sub/MATH/WA1 sub/SCIENCE/Quiz1";

    public static final String MESSAGE_DELETE_GRADE_SUCCESS = "Removed grade(s) from %1$s: %2$s";
    public static final String MESSAGE_NO_GRADES_REMOVED = "No matching grades found for %1$s.";

    private final Index index;
    private final Set<GradeKey> gradesToDelete;

    /**
     * Represents a subject/assessment key for a grade.
     */
    public static class GradeKey {
        private final String subject;
        private final String assessment;

        /**
         * Constructs a {@code GradeKey} with the given subject and assessment.
         *
         * @param subject The subject name.
         * @param assessment The assessment name.
         */
        public GradeKey(String subject, String assessment) {
            this.subject = subject.trim();
            this.assessment = assessment.trim();
        }

        /**
         * Returns the subject name.
         *
         * @return The subject name.
         */
        public String getSubject() {
            return subject;
        }

        /**
         * Returns the assessment name.
         *
         * @return The assessment name.
         */
        public String getAssessment() {
            return assessment;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof GradeKey)) {
                return false;
            }
            GradeKey otherKey = (GradeKey) other;
            return subject.equals(otherKey.subject) && assessment.equals(otherKey.assessment);
        }

        @Override
        public int hashCode() {
            return subject.hashCode() + assessment.hashCode();
        }

        @Override
        public String toString() {
            return subject + "/" + assessment;
        }
    }

    /**
     * Creates a {@code DeleteGradeCommand} to remove the specified grades from the given person.
     *
     * @param index Index of the person in the displayed person list whose grades are to be deleted.
     * @param gradesToDelete Set of grade keys (subject/assessment) to be removed from the person.
     */
    public DeleteGradeCommand(Index index, Set<GradeKey> gradesToDelete) {
        requireNonNull(index);
        requireNonNull(gradesToDelete);
        this.index = index;
        this.gradesToDelete = gradesToDelete;
    }

    /**
     * Executes the delete grade command.
     * Removes the specified grades from the target person in the model.
     *
     * @param model {@code Model} which contains the list of persons.
     * @return Command result indicating success or failure message.
     * @throws CommandException if the person index is invalid or no grades were removed.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Set<GradeKey> keysToRemove = new HashSet<>();
        for (GradeKey key : gradesToDelete) {
            if (personToEdit.getGradeList().hasGrade(key.getSubject(), key.getAssessment())) {
                keysToRemove.add(key);
            }
        }

        if (keysToRemove.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_GRADES_REMOVED, personToEdit.getName()));
        }

        Person editedPerson = createEditedPerson(personToEdit, keysToRemove);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String formattedKeys = keysToRemove.stream()
                .map(GradeKey::toString)
                .sorted()
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return new CommandResult(String.format(MESSAGE_DELETE_GRADE_SUCCESS,
                editedPerson.getName(), formattedKeys));
    }

    /**
     * Returns a new {@code Person} with the specified grades removed.
     */
    private static Person createEditedPerson(Person personToEdit, Set<GradeKey> keysToRemove) {
        GradeList updatedGradeList = personToEdit.getGradeList();

        for (GradeKey key : keysToRemove) {
            updatedGradeList = updatedGradeList.removeGrade(key.getSubject(), key.getAssessment());
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
                || (other instanceof DeleteGradeCommand
                && index.equals(((DeleteGradeCommand) other).index)
                && gradesToDelete.equals(((DeleteGradeCommand) other).gradesToDelete));
    }
}
