package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUB;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Lesson;


/**
 * Parses input arguments and creates a new {@code ScheduleCommand} object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

        // Check for missing arguments
        if (!argMultimap.getValue(PREFIX_START).isPresent()
                || !argMultimap.getValue(PREFIX_END).isPresent()
                || !argMultimap.getValue(PREFIX_DATE).isPresent()
                || !argMultimap.getValue(PREFIX_SUB).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ScheduleCommand.MESSAGE_USAGE));
        }

        String start = argMultimap.getValue(PREFIX_START).get().trim();
        String end = argMultimap.getValue(PREFIX_END).get().trim();
        String date = argMultimap.getValue(PREFIX_DATE).get().trim();
        String sub = argMultimap.getValue(PREFIX_SUB).get().trim();

        // Validate time format for start time
        try {
            LocalTime.parse(start, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid start time. Use hh:mm (e.g. 14:00).");
        }

        // Validate time format for end time
        try {
            LocalTime.parse(end, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid end time. Use hh:mm (e.g. 14:00).");
        }

        // Validate date format
        try {
            LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date. Use YYYY-MM-DD (e.g. 2025-09-20)");
        }

        // Validate that end time is after start time
        LocalTime startTime = LocalTime.parse(start, TIME_FORMATTER);
        LocalTime endTime = LocalTime.parse(end, TIME_FORMATTER);
        if (!endTime.isAfter(startTime)) {
            throw new ParseException("End time must be after start time");
        }

        Lesson lesson = new Lesson(start, end, date, sub, false);
        return new ScheduleCommand(index, lesson);
    }
}