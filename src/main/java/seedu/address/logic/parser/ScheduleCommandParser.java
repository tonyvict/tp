package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.commands.ScheduleCommand;


public class ScheduleCommandParser {

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

        return new ScheduleCommand(index, start, end, date, sub);
    }

}
