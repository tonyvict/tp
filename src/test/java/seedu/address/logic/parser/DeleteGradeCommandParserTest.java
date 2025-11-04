package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

public class DeleteGradeCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteGradeCommand.MESSAGE_USAGE);

    private final DeleteGradeCommandParser parser = new DeleteGradeCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteGradeCommand() throws Exception {
        DeleteGradeCommand expectedCommand = new DeleteGradeCommand(INDEX_FIRST_PERSON, "MATH", "WA1");
        assertParseSuccess(parser, "1 sub/MATH/WA1", expectedCommand);
    }

    @Test
    public void parse_missingSubPrefix_throwsParseException() {
        assertParseFailure(parser, "1 MATH/WA1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingSubPrefixOnlyIndex_throwsParseException() {
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "sub/MATH/WA1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "0 sub/MATH/WA1", MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "a sub/MATH/WA1", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingAssessment_throwsParseException() {
        assertParseFailure(parser, "1 sub/MATH", "Use sub/SUBJECT/ASSESSMENT");
    }

    @Test
    public void parse_tooManyParts_throwsParseException() {
        assertParseFailure(parser, "1 sub/MATH/WA1/89", "Use sub/SUBJECT/ASSESSMENT");
    }

    @Test
    public void parse_emptySubject_throwsParseException() {
        assertParseFailure(parser, "1 sub//WA1", "Subject and assessment cannot be empty");
    }

    @Test
    public void parse_emptyAssessment_throwsParseException() {
        assertParseFailure(parser, "1 sub/MATH/", "Subject and assessment cannot be empty");
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        Assert.assertThrows(ParseException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                parser.parse("-1 sub/MATH/WA1"));
    }
}
