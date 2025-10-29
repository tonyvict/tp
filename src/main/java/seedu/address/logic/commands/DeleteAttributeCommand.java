package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes specified attributes from an existing person in the address book.
 */
public class DeleteAttributeCommand extends Command {

    public static final String COMMAND_WORD = "deltag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the specified attribute(s) of the person "
            + "identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ATTRIBUTE + "KEY [more " + PREFIX_ATTRIBUTE + "KEY]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ATTRIBUTE + "subject";

    public static final String MESSAGE_DELETE_ATTRIBUTE_SUCCESS = "Removed attribute(s) from %1$s: %2$s";
    public static final String MESSAGE_NO_ATTRIBUTES_REMOVED = "No matching attributes found for %1$s.";

    private final Index index;
    private final Set<String> attributeKeysToDelete;

    /**
     * Creates a {@code DeleteAttributeCommand} to remove the specified attributes from the given person.
     *
     * @param index Index of the person in the displayed person list whose attributes are to be deleted.
     * @param attributeKeysToDelete Set of attribute keys to be removed from the person.
     */
    public DeleteAttributeCommand(Index index, Set<String> attributeKeysToDelete) {
        requireNonNull(index);
        requireNonNull(attributeKeysToDelete);
        this.index = index;
        this.attributeKeysToDelete = attributeKeysToDelete.stream()
                .map(key -> key == null ? null : key.trim().toLowerCase())
                .filter(key -> key != null && !key.isEmpty())
                .collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Executes the delete attribute command.
     * Removes the specified attributes from the target person in the model.
     *
     * @param model {@code Model} which contains the list of persons.
     * @return Command result indicating success or failure message.
     * @throws CommandException if the person index is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        var lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = personToEdit.removeAttributesByKey(attributeKeysToDelete);

        if (personToEdit.equals(editedPerson)) {
            // Nothing removed
            throw new CommandException(String.format(MESSAGE_NO_ATTRIBUTES_REMOVED, personToEdit.getName()));
        }

        model.setPerson(personToEdit, editedPerson);
        String formattedKeys = attributeKeysToDelete.stream()
                .sorted()
                .collect(Collectors.toList())
                .toString();
        return new CommandResult(String.format(MESSAGE_DELETE_ATTRIBUTE_SUCCESS,
                editedPerson.getName(), formattedKeys));
    }

    /**
     * Returns true if both {@code DeleteAttributeCommand} objects are equal
     * (i.e. have the same index and attribute keys to delete).
     *
     * @param other The other object to compare to.
     * @return True if both commands are equivalent, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteAttributeCommand
                && index.equals(((DeleteAttributeCommand) other).index)
                && attributeKeysToDelete.equals(((DeleteAttributeCommand) other).attributeKeysToDelete));
    }
}
