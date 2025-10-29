package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new {@code SearchCommand}.
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    @Override
    public SearchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            // Keep consistency with other parsers: show "Invalid command format!" + usage.
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        return new SearchCommand(new PersonContainsKeywordPredicate(Arrays.asList(keywords)));
    }
}

