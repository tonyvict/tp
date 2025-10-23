package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new OpenCommand object
 */
public class OpenCommandParser implements Parser<OpenCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the OpenCommand
     * and returns an OpenCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new OpenCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE), pe);
        }
    }
}
