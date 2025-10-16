package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Attribute;
import seedu.address.model.person.Person;


/**
 * Adds or updates attribute tags for a specified person in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_ADD_ATTRIBUTE_SUCCESS = "Added attribute to Person: %1$s";
    public static final String MESSAGE_DELETE_ATTRIBUTE_SUCCESS = "Removed attribute from Person: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "The student index provided is invalid.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or updates attribute tags for the specified "
            + "student identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ATTRIBUTE + "KEY=VALUE[,VALUE2]... "
            + "[" + PREFIX_ATTRIBUTE + "KEY2=VALUE2]...\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + "attr/subject=math,science "
            + "attr/age=16";


    private final Index index;
    private final Set<Attribute> attributesToAdd;

    /**
     * Creates a TagCommand to add the specified {@code Attribute}s to a person.
     */
    public TagCommand(Index index, Set<Attribute> attributesToAdd) {
        requireNonNull(index);
        requireNonNull(attributesToAdd);
        this.index = index;
        this.attributesToAdd = attributesToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createTaggedPerson(personToEdit, attributesToAdd);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_ATTRIBUTE_SUCCESS, editedPerson.getName()));
    }

    /**
     * Returns a new {@code Person} with the added or overridden attributes.
     */
    private static Person createTaggedPerson(Person personToEdit, Set<Attribute> attributesToAdd) {
        Set<Attribute> updatedAttributes = new HashSet<>(personToEdit.getAttributes());

        // Remove attributes that share the same key (to "override")
        for (Attribute newAttr : attributesToAdd) {
            updatedAttributes.removeIf(existing -> existing.getKey().equals(newAttr.getKey()));
            updatedAttributes.add(newAttr);
        }

        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getRemark(),
                personToEdit.getTags(),
                updatedAttributes,
                personToEdit.getLessonList()
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TagCommand
                && index.equals(((TagCommand) other).index)
                && attributesToAdd.equals(((TagCommand) other).attributesToAdd));
    }
}
