package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Attribute;
import seedu.address.testutil.Assert;

/**
 * Tests for {@link TagCommandParser}.
 */
public class TagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            TagCommand.MESSAGE_USAGE);

    private final TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_singleAttribute_success() throws Exception {
        Attribute attribute = new Attribute("subject", Set.of("math"));
        TagCommand expectedCommand = new TagCommand(Index.fromOneBased(1), Set.of(attribute));

        TagCommand actualCommand = parser.parse("1 " + PREFIX_ATTRIBUTE + "subject=math");
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_multipleAttributes_success() throws Exception {
        Attribute subjectAttribute = new Attribute("subject", Set.of("math", "science"));
        Attribute ccaAttribute = new Attribute("cca", Set.of("football"));

        TagCommand expectedCommand = new TagCommand(Index.fromOneBased(2), Set.of(subjectAttribute, ccaAttribute));

        String userInput = "  2  "
                + PREFIX_ATTRIBUTE + "subject=math, science "
                + PREFIX_ATTRIBUTE + "cca=Football  ";

        TagCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidIndex_failure() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () ->
                parser.parse("zero " + PREFIX_ATTRIBUTE + "subject=math"));
    }

    @Test
    public void parse_missingAttribute_failure() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_FORMAT, () ->
                parser.parse("1"));
    }

    @Test
    public void parse_attributeWithoutEquals_failure() {
        Assert.assertThrows(ParseException.class, "Incorrect format. Use attr/key=value[,value2]...", () ->
                parser.parse("1 " + PREFIX_ATTRIBUTE + "subject"));
    }

    @Test
    public void parse_attributeWithoutValues_failure() {
        Assert.assertThrows(ParseException.class, "Attribute must have at least one value.", () ->
                parser.parse("1 " + PREFIX_ATTRIBUTE + "subject="));
    }

    @Test
    public void parse_attributeWithEmptyKey_failure() {
        Assert.assertThrows(ParseException.class, "Attribute key cannot be empty.", () ->
                parser.parse("1 " + PREFIX_ATTRIBUTE + "=math"));
    }

}
