package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LessonTest {

    private final Lesson lesson = new Lesson("10:00", "11:00", "2023-01-01", "Math", false);

    @Test
    public void equals() {
        // same values -> returns true
        Lesson lessonCopy = new Lesson("10:00", "11:00", "2023-01-01", "Math", false);
        assertTrue(lesson.equals(lessonCopy));

        // same object -> returns true
        assertTrue(lesson.equals(lesson));

        // null -> returns false
        assertFalse(lesson.equals(null));

        // different type -> returns false
        assertFalse(lesson.equals(5));

        // different start time -> returns false
        Lesson editedLesson = new Lesson("10:01", "11:00", "2023-01-01", "Math", false);
        assertFalse(lesson.equals(editedLesson));

        // different date -> returns false
        editedLesson = new Lesson("10:00", "11:00", "2023-01-02", "Math", false);
        assertFalse(lesson.equals(editedLesson));

        // different subject -> returns false
        editedLesson = new Lesson("10:00", "11:00", "2023-01-01", "Science", false);
        assertFalse(lesson.equals(editedLesson));

        // different isPresent status -> returns false
        editedLesson = new Lesson("10:00", "11:00", "2023-01-01", "Math", true);
        assertFalse(lesson.equals(editedLesson));
    }

    @Test
    public void toString_formatsCorrectly() {
        // Lesson is not present
        Lesson notPresentLesson = new Lesson("14:00", "15:00", "2023-01-02", "Science", false);
        String expectedNotPresentString = "Science class: 2023-01-02 from 14:00 to 15:00[Not Present]";
        assertEquals(expectedNotPresentString, notPresentLesson.toString());

        // Lesson is present
        Lesson presentLesson = new Lesson("10:00", "11:00", "2023-01-01", "Math", true);
        String expectedPresentString = "Math class: 2023-01-01 from 10:00 to 11:00[Present]";
        assertEquals(expectedPresentString, presentLesson.toString());
    }
}