package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AttributeContainsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("  "));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() throws Exception {
        // no leading and trailing whitespaces
        Map<String, Set<String>> expectedPredicateKeywordMap = new HashMap<>();
        expectedPredicateKeywordMap.put("subject", Set.of("math"));
        FilterCommand expectedFilterCommand = new FilterCommand(new AttributeContainsPredicate(expectedPredicateKeywordMap));
        FilterCommand actualCommand = parser.parse(" attr/subject=math");
        assertEquals(expectedFilterCommand, actualCommand);

        // multiple whitespaces between keywords
        actualCommand = parser.parse(" \n attr/subject=math \n \t");
        assertEquals(expectedFilterCommand, actualCommand);
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("subject=math"));
        assertThrows(ParseException.class, () -> parser.parse(" attr/=math"));
        assertThrows(ParseException.class, () -> parser.parse(" attr/subject="));
    }

    @Test
    public void parse_multipleAttributes_returnsFilterCommand() throws Exception {
        Map<String, Set<String>> expectedPredicateKeywordMap = new HashMap<>();
        expectedPredicateKeywordMap.put("subject", Set.of("math"));
        expectedPredicateKeywordMap.put("age", Set.of("19"));
        
        FilterCommand expectedFilterCommand = new FilterCommand(new AttributeContainsPredicate(expectedPredicateKeywordMap));
        FilterCommand actualCommand = parser.parse(" attr/subject=math attr/age=19");
        assertEquals(expectedFilterCommand, actualCommand);
    }

    @Test
    public void parse_multipleValues_returnsFilterCommand() throws Exception {
        Map<String, Set<String>> expectedPredicateKeywordMap = new HashMap<>();
        expectedPredicateKeywordMap.put("subject", Set.of("math", "science"));
        
        FilterCommand expectedFilterCommand = new FilterCommand(new AttributeContainsPredicate(expectedPredicateKeywordMap));
        FilterCommand actualCommand = parser.parse(" attr/subject=math,science");
        assertEquals(expectedFilterCommand, actualCommand);
    }

    @Test
    public void parse_invalidAge_throwsParseException() {
        assertThrows(ParseException.class, "Age must be a valid integer.", () -> 
                parser.parse(" attr/age=notanumber"));
    }

    @Test
    public void parse_validAge_returnsFilterCommand() throws Exception {
        Map<String, Set<String>> expectedPredicateKeywordMap = new HashMap<>();
        expectedPredicateKeywordMap.put("age", Set.of("19"));
        
        FilterCommand expectedFilterCommand = new FilterCommand(new AttributeContainsPredicate(expectedPredicateKeywordMap));
        FilterCommand actualCommand = parser.parse(" attr/age=19");
        assertEquals(expectedFilterCommand, actualCommand);
    }
}
