package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.AttributeContainsPredicate;

/**
 * Filters and lists all persons in address book whose attributes match the specified criteria.
 * Attribute matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose attributes match "
            + "the specified criteria (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: attr/KEY=VALUE [attr/KEY2=VALUE2]...\n"
            + "Example: " + COMMAND_WORD + " attr/subject=science attr/age=19";

    public static final String MESSAGE_SUCCESS = "Filtered %1$d person(s)";

    private final AttributeContainsPredicate predicate;

    public FilterCommand(AttributeContainsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
