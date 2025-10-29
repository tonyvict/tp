package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsKeywordPredicate;

/**
 * Searches for persons whose name, phone, or email contains any of the specified keywords.
 * Matching is case-insensitive and allows partial matches.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose "
            + "name, phone, or email contain any of the specified keywords (case-insensitive) "
            + "and displays them as a list.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice john";

    public static final String MESSAGE_SEARCH_SUCCESS = "Found %1$d matching contact(s)!";
    public static final String MESSAGE_SEARCH_EMPTY = "No matching contacts found.";

    private final PersonContainsKeywordPredicate predicate;

    public SearchCommand(PersonContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        int size = model.getFilteredPersonList().size();
        String message = (size == 0)
                ? MESSAGE_SEARCH_EMPTY
                : String.format(MESSAGE_SEARCH_SUCCESS, size);

        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SearchCommand
                && predicate.equals(((SearchCommand) other).predicate));
    }
}

