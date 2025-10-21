package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Represents a Person's list of lessons in the address book.
 * Guarantees: immutable; is always valid
 */
public class LessonList {

    private final ArrayList<Lesson> list;

    /**
     * Constructs an empty {@code LessonList}.
     */
    public LessonList() {
        this.list = new ArrayList<>();
    }

    /**
     * Constructs a {@code LessonList} with given ArrayList.
     */
    public LessonList(ArrayList<Lesson> list) {
        this.list = new ArrayList<>(list);
        sortLessons();
    }

    /**
     * Checks for duplicates of the lesson in the list
     */
    public boolean hasDuplicates(Lesson lesson) {
        for (Lesson l: list) {
            if (lesson.equals(l)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a new LessonList with the lesson added in chronological order.
     * Does not modify the original LessonList.
     */
    public LessonList add(Lesson lesson) {
        ArrayList<Lesson> newList = new ArrayList<>(list);
        int insertIndex = findInsertionIndex(lesson, newList);
        newList.add(insertIndex, lesson);
        return new LessonList(newList);
    }

    /**
     * Finds the correct index to insert a lesson while maintaining chronological order.
     */
    private int findInsertionIndex(Lesson newLesson, ArrayList<Lesson> targetList) {
        for (int i = 0; i < targetList.size(); i++) {
            if (targetList.get(i).compareTo(newLesson) > 0) {
                return i;
            }
        }
        return targetList.size();
    }

    /**
     * Sorts the existing list chronologically.
     */
    private void sortLessons() {
        Collections.sort(list);
    }

    /**
     * Removes a lesson from the list.
     */
    public LessonList remove(Lesson lesson) {
        ArrayList<Lesson> newList = new ArrayList<>(list);
        newList.remove(lesson);
        return new LessonList(newList);
    }

    /**
     * Returns the lesson at the specified index.
     */
    public Lesson get(int index) {
        return list.get(index);
    }

    /**
     * Returns the number of lessons in the list.
     */
    public int size() {
        return list.size();
    }

    /**
     * Returns the number of attended lessons in the list.
     */
    public long getAttendedLessonCount() {
        return list.stream().filter(lesson -> lesson.isPresent()).count();
    }

    /**
     * Returns an unmodifiable view of the lesson list.
     */
    public ArrayList<Lesson> getLessons() {
        return new ArrayList<>(list);
    }

    /**
     * Checks if the list is empty.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonList)) {
            return false;
        }

        LessonList otherLessonList = (LessonList) other;
        return list.equals(otherLessonList.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}
