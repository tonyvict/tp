package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteAttributeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

public class DeleteAttributeCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteAttributeCommand.MESSAGE_USAGE);

    private final DeleteAttributeCommandParser parser = new DeleteAttributeCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAttributeCommand() throws Exception {
        DeleteAttributeCommand command = parser.parse("1 attr/subject attr/age");
        assertEquals(new DeleteAttributeCommand(Index.fromOneBased(1), Set.of("subject", "age")), command);
    }

    @Test
    public void parse_extraWhitespace_returnsDeleteAttributeCommand() throws Exception {
        DeleteAttributeCommand command = parser.parse("   1   attr/subject   attr/cca   ");
        assertEquals(new DeleteAttributeCommand(Index.fromOneBased(1), Set.of("subject", "cca")), command);
    }

    @Test
    public void parse_caseInsensitiveKeys_returnsLowerCasedKeys() throws Exception {
        DeleteAttributeCommand command = parser.parse("2 attr/Subject attr/CCA");
        assertEquals(new DeleteAttributeCommand(Index.fromOneBased(2), Set.of("subject", "cca")), command);
    }

    @Test
    public void parse_duplicateKeys_collapsesToSingleEntry() throws Exception {
        DeleteAttributeCommand command = parser.parse("3 attr/subject attr/subject attr/Subject ");
        assertEquals(new DeleteAttributeCommand(Index.fromOneBased(3), Set.of("subject")), command);
    }

    @Test
    public void parse_missingAttrPrefix_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_FORMAT, () -> parser.parse("1 subject"));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_FORMAT, () -> parser.parse("attr/subject"));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> parser.parse("0 attr/subject"));
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> parser.parse("a attr/subject"));
    }

    @Test
    public void parse_indexWithWhitespace_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_FORMAT, () -> parser.parse("1 2 attr/subject"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_FORMAT, () -> parser.parse(""));
    }

    @Test
    public void parse_attrPrefixWithoutKey_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_FORMAT, () -> parser.parse("4 attr/"));
    }

    @Test
    public void parse_attrPrefixWithWhitespaceOnly_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_FORMAT, () -> parser.parse("5 attr/   "));
    }

    @Test
    public void parse_negativeIndex_returnsInvalidPersonMessage() {
        Assert.assertThrows(ParseException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                parser.parse("-1 attr/subject"));
    }
}
