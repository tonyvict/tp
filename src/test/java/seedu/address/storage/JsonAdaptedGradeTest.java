package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGrades.MATH_WA1_89;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Grade;

public class JsonAdaptedGradeTest {
    private static final String INVALID_SUBJECT = "";
    private static final String INVALID_ASSESSMENT = "";
    private static final String INVALID_SCORE = "";

    private static final String VALID_SUBJECT = MATH_WA1_89.getSubject();
    private static final String VALID_ASSESSMENT = MATH_WA1_89.getAssessment();
    private static final String VALID_SCORE = MATH_WA1_89.getScore();

    @Test
    public void toModelType_validGradeDetails_returnsGrade() throws Exception {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(MATH_WA1_89);
        assertEquals(MATH_WA1_89, grade.toModelType());
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(INVALID_SUBJECT, VALID_ASSESSMENT, VALID_SCORE);
        String expectedMessage = "Subject name is invalid.";
        assertThrows(IllegalValueException.class, expectedMessage, grade::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(null, VALID_ASSESSMENT, VALID_SCORE);
        String expectedMessage = "Subject name is invalid.";
        assertThrows(IllegalValueException.class, expectedMessage, grade::toModelType);
    }

    @Test
    public void toModelType_invalidAssessment_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_SUBJECT, INVALID_ASSESSMENT, VALID_SCORE);
        String expectedMessage = "Assessment name is invalid.";
        assertThrows(IllegalValueException.class, expectedMessage, grade::toModelType);
    }

    @Test
    public void toModelType_nullAssessment_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_SUBJECT, null, VALID_SCORE);
        String expectedMessage = "Assessment name is invalid.";
        assertThrows(IllegalValueException.class, expectedMessage, grade::toModelType);
    }

    @Test
    public void toModelType_invalidScore_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_SUBJECT, VALID_ASSESSMENT, INVALID_SCORE);
        String expectedMessage = "Score value is invalid.";
        assertThrows(IllegalValueException.class, expectedMessage, grade::toModelType);
    }

    @Test
    public void toModelType_nullScore_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_SUBJECT, VALID_ASSESSMENT, null);
        String expectedMessage = "Score value is invalid.";
        assertThrows(IllegalValueException.class, expectedMessage, grade::toModelType);
    }

    @Test
    public void getSubject_returnsCorrectSubject() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_SUBJECT, VALID_ASSESSMENT, VALID_SCORE);
        assertEquals(VALID_SUBJECT, grade.getSubject());
    }

    @Test
    public void getAssessment_returnsCorrectAssessment() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_SUBJECT, VALID_ASSESSMENT, VALID_SCORE);
        assertEquals(VALID_ASSESSMENT, grade.getAssessment());
    }

    @Test
    public void getScore_returnsCorrectScore() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_SUBJECT, VALID_ASSESSMENT, VALID_SCORE);
        assertEquals(VALID_SCORE, grade.getScore());
    }
}
