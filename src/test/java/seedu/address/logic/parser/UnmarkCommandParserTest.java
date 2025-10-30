package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_LESSON_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkCommand;

public class UnmarkCommandParserTest {

    private final UnmarkCommandParser parser = new UnmarkCommandParser();

    @Test
    public void parse_validArgs_returnsUnmarkCommand() {
        Index lessonIndex = Index.fromOneBased(1);
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LESSON + "1";
        assertParseSuccess(parser, userInput, new UnmarkCommand(INDEX_FIRST_PERSON, lessonIndex));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no person index
        assertParseFailure(parser, PREFIX_LESSON + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        // no lesson prefix
        assertParseFailure(parser, "1 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        // no lesson index
        assertParseFailure(parser, "1 " + PREFIX_LESSON,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        // invalid free-form arg
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        // invalid person index
        assertParseFailure(parser, "0 " + PREFIX_LESSON + "1", MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "-1 " + PREFIX_LESSON + "1", MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "a " + PREFIX_LESSON + "1", MESSAGE_INVALID_INDEX);
        // invalid lesson index
        assertParseFailure(parser, "1 " + PREFIX_LESSON + "0", MESSAGE_INVALID_LESSON_INDEX);
        assertParseFailure(parser, "1 " + PREFIX_LESSON + "a", MESSAGE_INVALID_LESSON_INDEX);
    }
}
