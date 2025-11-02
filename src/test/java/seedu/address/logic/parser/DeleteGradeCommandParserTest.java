package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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
        DeleteGradeCommand command = parser.parse("1 sub/MATH/WA1");
        Set<DeleteGradeCommand.GradeKey> expectedKeys = Set.of(
                new DeleteGradeCommand.GradeKey("MATH", "WA1"));
        assertEquals(new DeleteGradeCommand(Index.fromOneBased(1), expectedKeys), command);
    }

    @Test
    public void parse_validArgsMultipleGrades_returnsDeleteGradeCommand() throws Exception {
        DeleteGradeCommand command = parser.parse("1 sub/MATH/WA1 sub/SCIENCE/Quiz1");
        Set<DeleteGradeCommand.GradeKey> expectedKeys = Set.of(
                new DeleteGradeCommand.GradeKey("MATH", "WA1"),
                new DeleteGradeCommand.GradeKey("SCIENCE", "Quiz1"));
        assertEquals(new DeleteGradeCommand(Index.fromOneBased(1), expectedKeys), command);
    }

    @Test
    public void parse_extraWhitespace_returnsDeleteGradeCommand() throws Exception {
        DeleteGradeCommand command = parser.parse("   1   sub/ MATH / WA1   ");
        Set<DeleteGradeCommand.GradeKey> expectedKeys = Set.of(
                new DeleteGradeCommand.GradeKey("MATH", "WA1"));
        assertEquals(new DeleteGradeCommand(Index.fromOneBased(1), expectedKeys), command);
    }

    @Test
    public void parse_missingSubPrefix_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_FORMAT, () -> parser.parse("1 MATH/WA1"));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_FORMAT, () -> parser.parse("sub/MATH/WA1"));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> parser.parse("0 sub/MATH/WA1"));
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> parser.parse("a sub/MATH/WA1"));
    }

    @Test
    public void parse_indexWithWhitespace_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_FORMAT, () -> parser.parse("1 2 sub/MATH/WA1"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_FORMAT, () -> parser.parse(""));
    }

    @Test
    public void parse_missingAssessment_throwsParseException() {
        Assert.assertThrows(ParseException.class, "Assessment is missing. Use sub/SUBJECT/ASSESSMENT", () ->
                parser.parse("1 sub/MATH"));
    }

    @Test
    public void parse_emptySubject_throwsParseException() {
        Assert.assertThrows(ParseException.class, "Subject cannot be empty.", () -> parser.parse("1 sub//WA1"));
    }

    @Test
    public void parse_emptyAssessment_throwsParseException() {
        Assert.assertThrows(ParseException.class, "Assessment cannot be empty.", () ->
                parser.parse("1 sub/MATH/"));
    }

    @Test
    public void parse_hasScore_throwsParseException() {
        Assert.assertThrows(ParseException.class,
                "Too many parts. Use sub/SUBJECT/ASSESSMENT (no score needed for deletion)", () ->
                parser.parse("1 sub/MATH/WA1/89"));
    }

    @Test
    public void parse_negativeIndex_returnsInvalidPersonMessage() {
        Assert.assertThrows(ParseException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                parser.parse("-1 sub/MATH/WA1"));
    }
}
