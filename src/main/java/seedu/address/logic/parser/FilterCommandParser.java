package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AttributeContainsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ATTRIBUTE);

        Map<String, Set<String>> attributeFilters = new HashMap<>();

        // Parse each attribute filter
        for (String attrString : argMultimap.getAllValues(PREFIX_ATTRIBUTE)) {
            if (!attrString.contains("=")) {
                throw new ParseException("Incorrect format. Use attr/key=value[,value2]...");
            }

            String[] keyValue = attrString.split("=", 2); // limit to 2 parts only
            String key = keyValue[0].trim().toLowerCase();

            if (key.isEmpty()) {
                throw new ParseException("Attribute key cannot be empty.");
            }

            // Split values by comma, e.g. "math,science"
            Set<String> values = new HashSet<>();
            Arrays.stream(keyValue[1].split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .forEach(values::add);

            if (values.isEmpty()) {
                throw new ParseException("Attribute must have at least one value.");
            }

            // Special validation for age attribute
            if (key.equals("age")) {
                for (String value : values) {
                    try {
                        Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        throw new ParseException("Age must be a valid integer.");
                    }
                }
            }

            attributeFilters.put(key, values);
        }

        if (attributeFilters.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(new AttributeContainsPredicate(attributeFilters));
    }
}
