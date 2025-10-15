package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class LessonListTest {

    private final Lesson lesson1 = new Lesson("10:00", "11:00", "2023-01-01", "Math");
    private final Lesson lesson2 = new Lesson("12:00", "13:00", "2023-01-01", "Science");
    private final Lesson lesson3 = new Lesson("09:00", "10:00", "2023-01-02", "Physics");

    @Test
    public void constructor_fromArrayList_sortsLessons() {
        ArrayList<Lesson> unsortedList = new ArrayList<>(Arrays.asList(lesson2, lesson3, lesson1));
        LessonList lessonList = new LessonList(unsortedList);

        // Lessons should be sorted chronologically: lesson1, lesson2, lesson3
        ArrayList<Lesson> expectedList = new ArrayList<>(Arrays.asList(lesson1, lesson2, lesson3));
        assertEquals(expectedList, lessonList.getLessons());
    }

    @Test
    public void add_maintainsChronologicalOrderAndImmutability() {
        LessonList initialList = new LessonList();
        // Add lesson2 first, then lesson1
        LessonList listWithLesson2 = initialList.add(lesson2);
        LessonList finalList = listWithLesson2.add(lesson1);

        // Test immutability
        assertTrue(initialList.isEmpty()); // Original list is unchanged
        assertEquals(1, listWithLesson2.size()); // Intermediate list is unchanged

        // Test chronological order
        ArrayList<Lesson> expectedOrder = new ArrayList<>(Arrays.asList(lesson1, lesson2));
        assertEquals(expectedOrder, finalList.getLessons());
    }

    @Test
    public void getLessons_returnsDefensiveCopy() {
        LessonList lessonList = new LessonList().add(lesson1);
        ArrayList<Lesson> lessonsCopy = lessonList.getLessons();

        // Modify the copy
        lessonsCopy.add(lesson2);

        // Original lesson list should not be affected
        assertNotEquals(lessonsCopy, lessonList.getLessons());
        assertEquals(1, lessonList.size());
    }

    @Test
    public void equals() {
        LessonList lessonList = new LessonList(new ArrayList<>(Arrays.asList(lesson1, lesson2)));

        // same values -> returns true
        LessonList lessonListCopy = new LessonList(new ArrayList<>(Arrays.asList(lesson1, lesson2)));
        assertTrue(lessonList.equals(lessonListCopy));

        // same object -> returns true
        assertTrue(lessonList.equals(lessonList));

        // null -> returns false
        assertFalse(lessonList.equals(null));

        // different types -> returns false
        assertFalse(lessonList.equals(5));

        // different lessons -> returns false
        LessonList differentLessonList = new LessonList(new ArrayList<>(Arrays.asList(lesson1, lesson3)));
        assertFalse(lessonList.equals(differentLessonList));
    }
}
