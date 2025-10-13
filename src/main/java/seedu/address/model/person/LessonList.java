package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;

public class LessonList {

    private final ArrayList<Lesson> list;

    public LessonList() {
        this.list = new ArrayList<>();
    }

    public LessonList(ArrayList<Lesson> list) {
        this.list = new ArrayList<>(list);
        sortLessons();
    }

    /**
     * Adds a lesson to the list in chronological order.
     */
    public void addLesson(Lesson lesson) {
        int insertIndex = findInsertionIndex(lesson);
        list.add(insertIndex, lesson);
    }

    /**
     * Finds the correct index to insert a lesson while maintaining chronological order.
     */
    private int findInsertionIndex(Lesson newLesson) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).compareTo(newLesson) > 0) {
                return i;
            }
        }
        return list.size();
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
    public boolean removeLesson(Lesson lesson) {
        return list.remove(lesson);
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
}