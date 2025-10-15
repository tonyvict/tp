package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Lesson;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    private final String start;
    private final String end;
    private final String date;
    private final String sub;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("start") String start,
                             @JsonProperty("end") String end,
                             @JsonProperty("date") String date,
                             @JsonProperty("sub") String sub) {
        this.start = start;
        this.end = end;
        this.date = date;
        this.sub = sub;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        start = source.start.toString();
        end = source.end.toString();
        date = source.date.toString();
        sub = source.sub;
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Lesson} object.
     */
    public Lesson toModelType() throws IllegalValueException {
        if (start == null || end == null || date == null || sub == null) {
            throw new IllegalValueException("Lesson fields cannot be null!");
        }
        return new Lesson(start, end, date, sub);
    }
}
