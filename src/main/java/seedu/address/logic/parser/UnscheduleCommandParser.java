package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnscheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code UnscheduleCommand} object
 */
public class UnscheduleCommandParser implements Parser<UnscheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code UnscheduleCommand}
     * and returns a {@code UnscheduleCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnscheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LESSON);

        if (!argMultimap.getValue(PREFIX_LESSON).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnscheduleCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LESSON);

        String lessonIndexString = ParserUtil.requireSingleIndex(
                argMultimap.getValue(PREFIX_LESSON).get(), UnscheduleCommand.MESSAGE_USAGE);

        Index lessonIndex;
        try {
            lessonIndex = ParserUtil.parseIndex(lessonIndexString);
        } catch (ParseException pe) {
            if (ParserUtil.MESSAGE_INVALID_INDEX.equals(pe.getMessage())) {
                throw new ParseException(ParserUtil.MESSAGE_INVALID_LESSON_INDEX);
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnscheduleCommand.MESSAGE_USAGE), pe);
        }

        String preamble = ParserUtil.requireSingleIndex(argMultimap.getPreamble(),
                UnscheduleCommand.MESSAGE_USAGE);
        Index personIndex = ParserUtil.parseIndex(preamble);

        return new UnscheduleCommand(personIndex, lessonIndex);
    }
}
