package seedu.address.testutil;

import seedu.address.model.person.Grade;

/**
 * A utility class containing a list of {@code Grade} objects to be used in tests.
 */
public class TypicalGrades {
    public static final Grade MATH_WA1_89 = new Grade("MATH", "WA1", "89");
    public static final Grade MATH_QUIZ1_95 = new Grade("MATH", "Quiz1", "95");
    public static final Grade SCIENCE_LAB1_87 = new Grade("SCIENCE", "Lab1", "87");
    public static final Grade SCIENCE_QUIZ1_92 = new Grade("SCIENCE", "Quiz1", "92");
    public static final Grade ENGLISH_ESSAY1_88 = new Grade("ENGLISH", "Essay1", "88");
    public static final Grade ENGLISH_PRESENTATION_92 = new Grade("ENGLISH", "Presentation", "92");
    public static final Grade HISTORY_MIDTERM_85 = new Grade("HISTORY", "Midterm", "85");
    public static final Grade HISTORY_FINAL_90 = new Grade("HISTORY", "Final", "90");

    private TypicalGrades() {} // prevents instantiation

    /**
     * Returns an array containing all the typical grades.
     */
    public static Grade[] getTypicalGrades() {
        return new Grade[] {
            MATH_WA1_89, MATH_QUIZ1_95, SCIENCE_LAB1_87, SCIENCE_QUIZ1_92,
            ENGLISH_ESSAY1_88, ENGLISH_PRESENTATION_92, HISTORY_MIDTERM_85, HISTORY_FINAL_90
        };
    }
}
