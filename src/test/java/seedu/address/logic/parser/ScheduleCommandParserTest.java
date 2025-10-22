package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.person.Lesson;

/**
 * Contains unit tests for {@link ScheduleCommandParser}.
 */
public class ScheduleCommandParserTest {

    private static final String VALID_ARGUMENTS =
            " start/10:00 end/12:00 date/2025-10-20 sub/Mathematics";

    private final ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + VALID_ARGUMENTS;
        Lesson lesson = new Lesson("10:00", "12:00", "2025-10-20", "Mathematics", false);
        ScheduleCommand expectedCommand = new ScheduleCommand(INDEX_FIRST_PERSON, lesson);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingLessonPrefix_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " start/10:00 end/12:00 date/2025-10-20";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPersonIndex_failure() {
        String userInput = "a" + VALID_ARGUMENTS;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStartTime_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased()
                + " start/aa:00 end/12:00 date/2025-10-20 sub/Mathematics";
        assertParseFailure(parser, userInput, "Invalid start time. Use hh:mm (e.g. 14:00).");
    }

    @Test
    public void parse_endTimeBeforeStartTime_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased()
                + " start/12:00 end/10:00 date/2025-10-20 sub/Mathematics";
        assertParseFailure(parser, userInput, "End time must be after start time");
    }
}
