package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Grade;

/**
 * Jackson-friendly version of {@link Grade}.
 */
class JsonAdaptedGrade {

    private final String subject;
    private final String assessment;
    private final String score;

    /**
     * Constructs a {@code JsonAdaptedGrade} with the given subject, assessment, and score.
     */
    @JsonCreator
    public JsonAdaptedGrade(@JsonProperty("subject") String subject,
                           @JsonProperty("assessment") String assessment,
                           @JsonProperty("score") String score) {
        this.subject = subject;
        this.assessment = assessment;
        this.score = score;
    }

    /**
     * Converts a given {@code Grade} into this class for Jackson use.
     */
    public JsonAdaptedGrade(Grade source) {
        subject = source.getSubject();
        assessment = source.getAssessment();
        score = source.getScore();
    }

    public String getSubject() {
        return subject;
    }

    public String getAssessment() {
        return assessment;
    }

    public String getScore() {
        return score;
    }

    /**
     * Converts this Jackson-friendly adapted grade object into the model's {@code Grade} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted grade.
     */
    public Grade toModelType() throws IllegalValueException {
        if (!Grade.isValidSubject(subject)) {
            throw new IllegalValueException("Subject name is invalid.");
        }
        if (!Grade.isValidAssessment(assessment)) {
            throw new IllegalValueException("Assessment name is invalid.");
        }
        if (!Grade.isValidScore(score)) {
            throw new IllegalValueException("Score value is invalid.");
        }
        return new Grade(subject, assessment, score);
    }
}
