package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUB;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Grade;

/**
 * Parses input arguments and creates a new GradeCommand object
 */
public class GradeCommandParser implements Parser<GradeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GradeCommand
     * and returns a GradeCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public GradeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUB);

        Set<Grade> gradesToAdd = new HashSet<>();
        Set<String> subjectAssessmentKeys = new HashSet<>();

        // Parse each subject/assessment/score triplet
        for (String subString : argMultimap.getAllValues(PREFIX_SUB)) {
            String[] parts = subString.split("/", -1); // -1 to preserve trailing empty strings

            // Provide specific error messages based on what's missing
            if (parts.length < 3) {
                if (parts.length == 1) {
                    throw new ParseException("Assessment and Score are missing. Use sub/SUBJECT/ASSESSMENT/SCORE");
                } else if (parts.length == 2) {
                    throw new ParseException("Score is missing. Use sub/SUBJECT/ASSESSMENT/SCORE");
                } else {
                    throw new ParseException("Incorrect format. Use sub/SUBJECT/ASSESSMENT/SCORE");
                }
            }

            // Handle too many parts
            if (parts.length > 3) {
                throw new ParseException("Too many parts. Use sub/SUBJECT/ASSESSMENT/SCORE");
            }

            String subject = parts[0].trim();
            String assessment = parts[1].trim();
            String score = parts[2].trim();

            if (subject.isEmpty()) {
                throw new ParseException("Subject cannot be empty.");
            }

            if (assessment.isEmpty()) {
                throw new ParseException("Assessment cannot be empty.");
            }

            if (score.isEmpty()) {
                throw new ParseException("Score cannot be empty.");
            }

            if (!Grade.isValidSubject(subject)) {
                throw new ParseException("Subject name is invalid.");
            }

            if (!Grade.isValidAssessment(assessment)) {
                throw new ParseException("Assessment name is invalid.");
            }

            if (!Grade.isValidScore(score)) {
                throw new ParseException("Score value is invalid.");
            }

            // Check for duplicate subject/assessment combinations
            String key = subject + "/" + assessment;
            if (subjectAssessmentKeys.contains(key)) {
                throw new ParseException(
                    String.format("Duplicate grade detected for %s. Each subject/assessment combination "
                        + "can only be specified once per command.", key));
            }
            subjectAssessmentKeys.add(key);

            Grade gradeObj = new Grade(subject, assessment, score);
            gradesToAdd.add(gradeObj);
        }

        if (gradesToAdd.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE));
        }

        String preamble = ParserUtil.requireSingleIndex(argMultimap.getPreamble(), GradeCommand.MESSAGE_USAGE);
        if (preamble.startsWith(PREFIX_SUB.getPrefix())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(preamble);

        return new GradeCommand(index, gradesToAdd);
    }
}
