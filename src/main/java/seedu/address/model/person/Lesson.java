package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a Person's Lesson in the address book.
 * Guarantees: immutable; is always valid
 */
public class Lesson implements Comparable<Lesson> {

    private final LocalTime start;
    private final LocalTime end;
    private final LocalDate date;
    private final LocalDate endDate;
    private final String sub;
    private final boolean isPresent;


    /**
     * Constructs a {@code Lesson} with isPresent set to false.
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
        this.endDate = this.date;
        this.isPresent = false;
    }

    /**
     * Constructs a {@code Lesson} with isPresent set to false.
     *
     * @param start A valid time in HH:mm format
     * @param end A valid time after start in HH:mm format
     * @param date A valid date in YYYY-MM-DD format
     * @param sub A valid subject
     */
    public Lesson(LocalTime start, LocalTime end, LocalDate date, String sub) {
        requireAllNonNull(start, end, date, sub);
        this.start = start;
        this.end = end;
        this.date = date;
        this.sub = sub;
        this.endDate = date;
        this.isPresent = false;
    }

    /**
     * Constructs a {@code Lesson}.
     *
     * @param start A valid time in HH:mm format
     * @param end A valid time after start in HH:mm format
     * @param date A valid date in YYYY-MM-DD format
     * @param sub A valid subject
     * @param isPresent The attendance status
     */
    public Lesson(String start, String end, String date, String sub, boolean isPresent) {
        requireAllNonNull(start, end, date, sub);
        this.start = LocalTime.parse(start);
        this.end = LocalTime.parse(end);
        this.date = LocalDate.parse(date);
        this.sub = sub;
        this.endDate = LocalDate.parse(date);
        this.isPresent = isPresent;
    }

    /**
     * Constructs a {@code Lesson} with a specific attendance status.
     *
     * @param start A valid time
     * @param end A valid time after start
     * @param date A valid date
     * @param sub A valid subject
     * @param isPresent The attendance status
     */
    public Lesson(LocalTime start, LocalTime end, LocalDate date, String sub, boolean isPresent) {
        requireAllNonNull(start, end, date, sub);
        this.start = start;
        this.end = end;
        this.date = date;
        this.sub = sub;
        this.endDate = date;
        this.isPresent = isPresent;
    }

    /**
     * Constructs a {@code Lesson} with explicit start/end date strings.
     *
     * @param start A valid time in HH:mm format
     * @param end A valid time in HH:mm format
     * @param date A valid start date in YYYY-MM-DD format
     * @param endDate A valid end date in YYYY-MM-DD format
     * @param sub A valid subject
     * @param isPresent The attendance status
     */
    public Lesson(String start, String end, String date, String endDate, String sub, boolean isPresent) {
        this(LocalTime.parse(start), LocalTime.parse(end), LocalDate.parse(date), LocalDate.parse(endDate), sub,
                isPresent);
    }

    /**
     * Constructs a {@code Lesson} with explicit {@link LocalDate} boundaries.
     *
     * @param start A valid time
     * @param end A valid time after start
     * @param date The start date of the lesson
     * @param endDate The end date of the lesson
     * @param sub A valid subject
     * @param isPresent The attendance status
     */
    public Lesson(LocalTime start, LocalTime end, LocalDate date, LocalDate endDate, String sub, boolean isPresent) {
        requireAllNonNull(start, end, date, endDate, sub);
        this.start = start;
        this.end = end;
        this.date = date;
        this.endDate = endDate;
        this.sub = sub;
        this.isPresent = isPresent;
    }

    public LocalTime getStart() {
        return this.start;
    }

    public LocalTime getEnd() {
        return this.end;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getSub() {
        return this.sub;
    }

    public boolean isPresent() {
        return this.isPresent;
    }

    /**
     * Returns true if this lesson overlaps in time with {@code other} on the same date.
     * Lessons that end exactly when another begins are not considered overlapping.
     */
    public boolean overlapsWith(Lesson other) {
        requireAllNonNull(other);
        LocalDateTime startDateTime = date.atTime(start);
        LocalDateTime endDateTime = endDate.atTime(end);
        LocalDateTime otherStart = other.date.atTime(other.start);
        LocalDateTime otherEnd = other.endDate.atTime(other.end);
        return startDateTime.isBefore(otherEnd) && endDateTime.isAfter(otherStart);
    }

    /**
     * Returns a string with the lesson's details, excluding attendance.
     */
    public String getLessonDetails() {
        String endPart = endDate.equals(date) ? end.toString() : endDate + " " + end;
        return sub + " class on " + date + " from " + start + " to " + endPart;
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
        int dateComparison = this.date.atTime(start).compareTo(other.date.atTime(other.start));
        if (dateComparison != 0) {
            return dateComparison;
        }
        // If same date, compare by start time
        return this.endDate.atTime(end).compareTo(other.endDate.atTime(other.end));
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
                && endDate.equals(otherLesson.endDate)
                && sub.equals(otherLesson.sub)
                && isPresent == otherLesson.isPresent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, date, endDate, sub);
    }

    @Override
    public String toString() {
        String attendance = isPresent ? "[Present]" : "[Not Present]";
        return sub + ": " + date + " || " + start + " to " + endDate + " || " + end + attendance;
    }

}
