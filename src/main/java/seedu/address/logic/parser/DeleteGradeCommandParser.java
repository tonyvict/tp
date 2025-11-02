package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SUB;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteGradeCommand object.
 */
public class DeleteGradeCommandParser implements Parser<DeleteGradeCommand> {

    @Override
    public DeleteGradeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUB);
        String preamble = ParserUtil.requireSingleIndex(argMultimap.getPreamble(),
                DeleteGradeCommand.MESSAGE_USAGE);

        if (!argMultimap.getValue(PREFIX_SUB).isPresent()) {
            throw new ParseException(DeleteGradeCommand.MESSAGE_USAGE);
        }

        String[] parts = argMultimap.getValue(PREFIX_SUB).get().split("/", -1);
        if (parts.length != 2) {
            throw new ParseException("Use sub/SUBJECT/ASSESSMENT");
        }

        String subject = parts[0].trim();
        String assessment = parts[1].trim();

        if (subject.isEmpty() || assessment.isEmpty()) {
            throw new ParseException("Subject and assessment cannot be empty");
        }

        Index index = ParserUtil.parseIndex(preamble);
        return new DeleteGradeCommand(index, subject, assessment);
    }
}
