package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkCommand object
 */
public class UnmarkCommandParser implements Parser<UnmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkCommand
     * and returns an UnmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LESSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_LESSON) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LESSON);

        String personToken = ParserUtil.requireSingleIndex(argMultimap.getPreamble(), UnmarkCommand.MESSAGE_USAGE);
        Index personIndex = ParserUtil.parseIndex(personToken);

        String lessonIndexString = ParserUtil.requireSingleIndex(argMultimap.getValue(PREFIX_LESSON).get(),
                UnmarkCommand.MESSAGE_USAGE);
        Index lessonIndex;
        try {
            lessonIndex = ParserUtil.parseIndex(lessonIndexString);
        } catch (ParseException pe) {
            if (ParserUtil.MESSAGE_INVALID_INDEX.equals(pe.getMessage())) {
                throw new ParseException(ParserUtil.MESSAGE_INVALID_LESSON_INDEX);
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE), pe);
        }

        return new UnmarkCommand(personIndex, lessonIndex);
    }
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
