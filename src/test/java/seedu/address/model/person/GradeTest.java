package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GradeTest {

    @Test
    public void constructor_nullSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Grade(null, "WA1", "89"));
    }

    @Test
    public void constructor_nullAssessment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Grade("MATH", null, "89"));
    }

    @Test
    public void constructor_nullScore_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Grade("MATH", "WA1", null));
    }

    @Test
    public void constructor_validParameters_success() {
        Grade grade = new Grade("MATH", "WA1", "89");
        assertEquals("MATH", grade.getSubject());
        assertEquals("WA1", grade.getAssessment());
        assertEquals("89", grade.getScore());
    }

    @Test
    public void constructor_trimsWhitespace_success() {
        Grade grade = new Grade("  MATH  ", "  WA1  ", "  89  ");
        assertEquals("MATH", grade.getSubject());
        assertEquals("WA1", grade.getAssessment());
        assertEquals("89", grade.getScore());
    }

    @Test
    public void isValidSubject() {
        // null subject
        assertFalse(Grade.isValidSubject(null));

        // empty subject
        assertFalse(Grade.isValidSubject(""));

        // blank subject
        assertFalse(Grade.isValidSubject("   "));

        // valid subjects
        assertTrue(Grade.isValidSubject("MATH"));
        assertTrue(Grade.isValidSubject("SCIENCE"));
        assertTrue(Grade.isValidSubject("ENGLISH"));
        assertTrue(Grade.isValidSubject("Mathematics"));
    }

    @Test
    public void isValidAssessment() {
        // null assessment
        assertFalse(Grade.isValidAssessment(null));

        // empty assessment
        assertFalse(Grade.isValidAssessment(""));

        // blank assessment
        assertFalse(Grade.isValidAssessment("   "));

        // valid assessments
        assertTrue(Grade.isValidAssessment("WA1"));
        assertTrue(Grade.isValidAssessment("Quiz1"));
        assertTrue(Grade.isValidAssessment("Final"));
        assertTrue(Grade.isValidAssessment("Midterm"));
    }

    @Test
    public void isValidScore() {
        // null score
        assertFalse(Grade.isValidScore(null));

        // empty score
        assertFalse(Grade.isValidScore(""));

        // blank score
        assertFalse(Grade.isValidScore("   "));

        // valid scores
        assertTrue(Grade.isValidScore("89"));
        assertTrue(Grade.isValidScore("A"));
        assertTrue(Grade.isValidScore("B+"));
        assertTrue(Grade.isValidScore("95.5"));
    }

    @Test
    public void equals() {
        Grade grade = new Grade("MATH", "WA1", "89");

        // same values -> returns true
        assertTrue(grade.equals(new Grade("MATH", "WA1", "89")));

        // same object -> returns true
        assertTrue(grade.equals(grade));

        // null -> returns false
        assertFalse(grade.equals(null));

        // different type -> returns false
        assertFalse(grade.equals(5));

        // different subject -> returns false
        assertFalse(grade.equals(new Grade("SCIENCE", "WA1", "89")));

        // different assessment -> returns false
        assertFalse(grade.equals(new Grade("MATH", "Quiz1", "89")));

        // different score -> returns false
        assertFalse(grade.equals(new Grade("MATH", "WA1", "95")));
    }

    @Test
    public void hashCode_test() {
        Grade grade1 = new Grade("MATH", "WA1", "89");
        Grade grade2 = new Grade("MATH", "WA1", "89");
        Grade grade3 = new Grade("SCIENCE", "WA1", "89");

        // same values -> same hashcode
        assertEquals(grade1.hashCode(), grade2.hashCode());

        // different values -> different hashcode
        assertFalse(grade1.hashCode() == grade3.hashCode());
    }

    @Test
    public void toString_test() {
        Grade grade = new Grade("MATH", "WA1", "89");
        String expected = "MATH/WA1/89";
        assertEquals(expected, grade.toString());
    }
}
