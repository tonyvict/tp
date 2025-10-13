package seedu.address.model.person;

import java.time.LocalDate;
import java.time.LocalTime;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Lesson {

    public static final String MESSAGE_CONSTRAINTS = "Lesson should";

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
        //checkArgument(isValidLesson(new Lesson(start, end, date, sub)), MESSAGE_CONSTRAINTS);
        this.start = LocalTime.parse(start);
        this.end = LocalTime.parse(end);
        this.date = LocalDate.parse(date);
        this.sub = sub;
    }

    //not implemented yet
    /**
     * Returns if a given lesson is a valid lesson.
     */
    public static boolean isValidLesson(Lesson test) {
        return true;
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
}
