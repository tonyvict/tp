package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUB;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Grade;

/**
 * Parses input arguments and creates a new DeleteGradeCommand object.
 */
public class DeleteGradeCommandParser implements Parser<DeleteGradeCommand> {

    @Override
    public DeleteGradeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUB);

        String preamble = ParserUtil.requireSingleIndex(argMultimap.getPreamble(),
                DeleteGradeCommand.MESSAGE_USAGE);
        if (preamble.startsWith(PREFIX_SUB.getPrefix())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteGradeCommand.MESSAGE_USAGE));
        }

        Set<DeleteGradeCommand.GradeKey> gradeKeys = new HashSet<>();

        // Parse each subject/assessment pair
        for (String subString : argMultimap.getAllValues(PREFIX_SUB)) {
            String[] parts = subString.split("/", -1); // -1 to preserve trailing empty strings

            // Provide specific error messages based on what's missing
            if (parts.length < 2) {
                if (parts.length == 1) {
                    throw new ParseException("Assessment is missing. Use sub/SUBJECT/ASSESSMENT");
                } else {
                    throw new ParseException("Incorrect format. Use sub/SUBJECT/ASSESSMENT");
                }
            }

            // Handle too many parts - score should not be provided for deletion
            if (parts.length > 2) {
                throw new ParseException("Too many parts. Use sub/SUBJECT/ASSESSMENT (no score needed for deletion)");
            }

            String subject = parts[0].trim();
            String assessment = parts[1].trim();

            if (subject.isEmpty()) {
                throw new ParseException("Subject cannot be empty.");
            }

            if (assessment.isEmpty()) {
                throw new ParseException("Assessment cannot be empty.");
            }

            if (!Grade.isValidSubject(subject)) {
                throw new ParseException("Subject name is invalid.");
            }

            if (!Grade.isValidAssessment(assessment)) {
                throw new ParseException("Assessment name is invalid.");
            }

            gradeKeys.add(new DeleteGradeCommand.GradeKey(subject, assessment));
        }

        if (gradeKeys.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteGradeCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(preamble);

        return new DeleteGradeCommand(index, gradeKeys);
    }
}
