package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUB;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Lesson;

/**
 * Parses input arguments and creates a new {@code ScheduleCommand} object
 */
public class ScheduleCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code ScheduleCommand}
     * and returns a {@code ScheduleCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_START, PREFIX_END, PREFIX_DATE, PREFIX_SUB);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ScheduleCommand.MESSAGE_USAGE), ive);
        }

        String start = argMultimap.getValue(PREFIX_START).orElse("");
        String end = argMultimap.getValue(PREFIX_END).orElse("");
        String date = argMultimap.getValue(PREFIX_DATE).orElse("");
        String sub = argMultimap.getValue(PREFIX_SUB).orElse("");

        Lesson lesson = new Lesson(start, end, date, sub);
        return new ScheduleCommand(index, lesson);
    }

}
