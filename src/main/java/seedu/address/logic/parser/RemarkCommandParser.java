package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        String preamble = ParserUtil.requireSingleIndex(argMultimap.getPreamble(),
                RemarkCommand.MESSAGE_USAGE);
        if (preamble.startsWith(PREFIX_REMARK.getPrefix())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        // Check if PREFIX_REMARK is present
        if (argMultimap.getValue(PREFIX_REMARK).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        // Get all remark values and join them with commas
        List<String> remarkSegments = argMultimap.getAllValues(PREFIX_REMARK);
        List<String> cleanedSegments = remarkSegments.stream()
                .map(String::trim)
                .filter(segment -> !segment.isEmpty())
                .collect(Collectors.toList());

        String remark = cleanedSegments.isEmpty() ? "" : String.join(", ", cleanedSegments);

        Index index = ParserUtil.parseIndex(preamble);

        return new RemarkCommand(index, new Remark(remark));
    }
}
