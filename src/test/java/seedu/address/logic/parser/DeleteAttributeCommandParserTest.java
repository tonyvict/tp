package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAttributeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteAttributeCommandParserTest {

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
    public void parse_missingAttrPrefix_throwsParseException() {
        try {
            parser.parse("1 subject");
        } catch (ParseException e) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAttributeCommand.MESSAGE_USAGE),
                    e.getMessage());
        }
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        try {
            parser.parse("attr/subject");
        } catch (ParseException e) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAttributeCommand.MESSAGE_USAGE),
                    e.getMessage());
        }
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        try {
            parser.parse("");
        } catch (ParseException e) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAttributeCommand.MESSAGE_USAGE),
                    e.getMessage());
        }
    }
}
