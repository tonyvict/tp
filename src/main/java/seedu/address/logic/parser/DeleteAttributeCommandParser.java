package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAttributeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAttributeCommand object.
 */
public class DeleteAttributeCommandParser implements Parser<DeleteAttributeCommand> {

    @Override
    public DeleteAttributeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ATTRIBUTE);

        if (argMultimap.getValue(PREFIX_ATTRIBUTE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAttributeCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            Set<String> keys = argMultimap.getAllValues(PREFIX_ATTRIBUTE)
                    .stream()
                    .map(String::trim)
                    .map(s -> s.toLowerCase())
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());

            if (keys.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteAttributeCommand.MESSAGE_USAGE));
            }

            return new DeleteAttributeCommand(index, keys);
        } catch (ParseException pe) {
            if (ParserUtil.MESSAGE_INVALID_INDEX.equals(pe.getMessage())) {
                throw pe;
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAttributeCommand.MESSAGE_USAGE), pe);
        }
    }
}
