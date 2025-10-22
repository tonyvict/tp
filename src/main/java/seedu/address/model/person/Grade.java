package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a Person's grade for a specific subject and assessment in the address book.
 * Guarantees: immutable; is always valid
 */
public class Grade {
    public final String subject;
    public final String assessment;
    public final String score;

    /**
     * Constructs a {@code Grade}.
     *
     * @param subject A valid subject name.
     * @param assessment A valid assessment name.
     * @param score A valid score/grade value.
     */
    public Grade(String subject, String assessment, String score) {
        requireNonNull(subject);
        requireNonNull(assessment);
        requireNonNull(score);
        this.subject = subject.trim();
        this.assessment = assessment.trim();
        this.score = score.trim();
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
     * Returns true if a given string is a valid subject name.
     */
    public static boolean isValidSubject(String test) {
        return test != null && !test.trim().isEmpty();
    }

    /**
     * Returns true if a given string is a valid assessment name.
     */
    public static boolean isValidAssessment(String test) {
        return test != null && !test.trim().isEmpty();
    }

    /**
     * Returns true if a given string is a valid score value.
     */
    public static boolean isValidScore(String test) {
        return test != null && !test.trim().isEmpty();
    }

    @Override
    public String toString() {
        return subject + "/" + assessment + "/" + score;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Grade)) {
            return false;
        }
        Grade otherGrade = (Grade) other;
        return subject.equals(otherGrade.subject)
                && assessment.equals(otherGrade.assessment)
                && score.equals(otherGrade.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, assessment, score);
    }
}
