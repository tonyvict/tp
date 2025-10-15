package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a Person's Lesson in the address book.
 * Guarantees: immutable; is always valid
 */
public class Lesson implements Comparable<Lesson> {

    public final LocalTime start;
    public final LocalTime end;
    public final LocalDate date;
    public final String sub;

    /**
     * Constructs an {@code Lesson}.
     *
     * @param start A valid time in HH:mm format
     * @param end A valid time after start in HH:mm format
     * @param date A valid date in YYYY-MM-DD format
     * @param sub A valid subject
     */
    public Lesson(String start, String end, String date, String sub) {
        requireAllNonNull(start, end, date, sub);
        this.start = LocalTime.parse(start);
        this.end = LocalTime.parse(end);
        this.date = LocalDate.parse(date);
        this.sub = sub;
    }

    /**
     * Compares this lesson with another lesson chronologically.
     * Lessons are ordered by date first, then by start time.
     *
     * @param other the lesson to compare to
     * @return negative if this lesson is before other, positive if after, 0 if same time
     */
    @Override
    public int compareTo(Lesson other) {
        // First compare by date
        int dateComparison = this.date.compareTo(other.date);
        if (dateComparison != 0) {
            return dateComparison;
        }
        // If same date, compare by start time
        return this.start.compareTo(other.start);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return start.equals(otherLesson.start)
                && end.equals(otherLesson.end)
                && date.equals(otherLesson.date)
                && sub.equals(otherLesson.sub);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, date, sub);
    }

    @Override
    public String toString() {
        return sub + " class: " + date.toString() + " from " + start.toString() + " to " + end.toString();
    }

}
