package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class GradeListTest {

    @Test
    public void constructor_defaultConstructor_success() {
        GradeList gradeList = new GradeList();
        assertTrue(gradeList.isEmpty());
        assertEquals(0, gradeList.size());
    }

    @Test
    public void constructor_withGrades_success() {
        Map<String, Grade> grades = new HashMap<>();
        Grade grade1 = new Grade("MATH", "WA1", "89");
        Grade grade2 = new Grade("SCIENCE", "Quiz1", "95");
        grades.put("MATH/WA1", grade1);
        grades.put("SCIENCE/Quiz1", grade2);

        GradeList gradeList = new GradeList(grades);
        assertFalse(gradeList.isEmpty());
        assertEquals(2, gradeList.size());
    }

    @Test
    public void constructor_nullGrades_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GradeList(null));
    }

    @Test
    public void addGrade_nullGrade_throwsNullPointerException() {
        GradeList gradeList = new GradeList();
        assertThrows(NullPointerException.class, () -> gradeList.addGrade(null));
    }

    @Test
    public void addGrade_validGrade_success() {
        GradeList gradeList = new GradeList();
        Grade grade = new Grade("MATH", "WA1", "89");
        
        GradeList updatedGradeList = gradeList.addGrade(grade);
        
        assertFalse(updatedGradeList.isEmpty());
        assertEquals(1, updatedGradeList.size());
        assertTrue(updatedGradeList.hasGrade("MATH", "WA1"));
        assertEquals(grade, updatedGradeList.getGrade("MATH", "WA1"));
    }

    @Test
    public void addGrade_overwriteExistingGrade_success() {
        GradeList gradeList = new GradeList();
        Grade grade1 = new Grade("MATH", "WA1", "89");
        Grade grade2 = new Grade("MATH", "WA1", "95");
        
        GradeList updatedGradeList = gradeList.addGrade(grade1);
        updatedGradeList = updatedGradeList.addGrade(grade2);
        
        assertEquals(1, updatedGradeList.size());
        assertEquals(grade2, updatedGradeList.getGrade("MATH", "WA1"));
    }

    @Test
    public void addGrade_multipleGrades_success() {
        GradeList gradeList = new GradeList();
        Grade grade1 = new Grade("MATH", "WA1", "89");
        Grade grade2 = new Grade("SCIENCE", "Quiz1", "95");
        Grade grade3 = new Grade("MATH", "Quiz1", "92");
        
        GradeList updatedGradeList = gradeList.addGrade(grade1);
        updatedGradeList = updatedGradeList.addGrade(grade2);
        updatedGradeList = updatedGradeList.addGrade(grade3);
        
        assertEquals(3, updatedGradeList.size());
        assertTrue(updatedGradeList.hasGrade("MATH", "WA1"));
        assertTrue(updatedGradeList.hasGrade("SCIENCE", "Quiz1"));
        assertTrue(updatedGradeList.hasGrade("MATH", "Quiz1"));
    }

    @Test
    public void getGrade_existingGrade_returnsGrade() {
        GradeList gradeList = new GradeList();
        Grade grade = new Grade("MATH", "WA1", "89");
        GradeList updatedGradeList = gradeList.addGrade(grade);
        
        assertEquals(grade, updatedGradeList.getGrade("MATH", "WA1"));
    }

    @Test
    public void getGrade_nonExistentGrade_returnsNull() {
        GradeList gradeList = new GradeList();
        assertTrue(gradeList.getGrade("MATH", "WA1") == null);
    }

    @Test
    public void hasGrade_existingGrade_returnsTrue() {
        GradeList gradeList = new GradeList();
        Grade grade = new Grade("MATH", "WA1", "89");
        GradeList updatedGradeList = gradeList.addGrade(grade);
        
        assertTrue(updatedGradeList.hasGrade("MATH", "WA1"));
    }

    @Test
    public void hasGrade_nonExistentGrade_returnsFalse() {
        GradeList gradeList = new GradeList();
        assertFalse(gradeList.hasGrade("MATH", "WA1"));
    }

    @Test
    public void getGrades_returnsCopyOfGrades() {
        GradeList gradeList = new GradeList();
        Grade grade = new Grade("MATH", "WA1", "89");
        GradeList updatedGradeList = gradeList.addGrade(grade);
        
        Map<String, Grade> grades = updatedGradeList.getGrades();
        assertEquals(1, grades.size());
        
        // Modifying the returned map should not affect the original
        grades.clear();
        assertEquals(1, updatedGradeList.size());
    }

    @Test
    public void isEmpty_emptyList_returnsTrue() {
        GradeList gradeList = new GradeList();
        assertTrue(gradeList.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptyList_returnsFalse() {
        GradeList gradeList = new GradeList();
        Grade grade = new Grade("MATH", "WA1", "89");
        GradeList updatedGradeList = gradeList.addGrade(grade);
        
        assertFalse(updatedGradeList.isEmpty());
    }

    @Test
    public void size_emptyList_returnsZero() {
        GradeList gradeList = new GradeList();
        assertEquals(0, gradeList.size());
    }

    @Test
    public void size_nonEmptyList_returnsCorrectSize() {
        GradeList gradeList = new GradeList();
        Grade grade1 = new Grade("MATH", "WA1", "89");
        Grade grade2 = new Grade("SCIENCE", "Quiz1", "95");
        
        GradeList updatedGradeList = gradeList.addGrade(grade1);
        updatedGradeList = updatedGradeList.addGrade(grade2);
        
        assertEquals(2, updatedGradeList.size());
    }

    @Test
    public void toString_emptyList_returnsNoGradesMessage() {
        GradeList gradeList = new GradeList();
        assertEquals("No grades recorded", gradeList.toString());
    }

    @Test
    public void toString_nonEmptyList_returnsGradesString() {
        GradeList gradeList = new GradeList();
        Grade grade = new Grade("MATH", "WA1", "89");
        GradeList updatedGradeList = gradeList.addGrade(grade);
        
        String result = updatedGradeList.toString();
        assertTrue(result.contains("MATH/WA1/89"));
    }

    @Test
    public void equals() {
        GradeList gradeList1 = new GradeList();
        GradeList gradeList2 = new GradeList();
        GradeList gradeList3 = new GradeList();
        
        Grade grade = new Grade("MATH", "WA1", "89");
        gradeList1 = gradeList1.addGrade(grade);
        gradeList2 = gradeList2.addGrade(grade);
        gradeList3 = gradeList3.addGrade(new Grade("SCIENCE", "Quiz1", "95"));

        // same values -> returns true
        assertTrue(gradeList1.equals(gradeList2));

        // same object -> returns true
        assertTrue(gradeList1.equals(gradeList1));

        // null -> returns false
        assertFalse(gradeList1.equals(null));

        // different type -> returns false
        assertFalse(gradeList1.equals(5));

        // different grades -> returns false
        assertFalse(gradeList1.equals(gradeList3));
    }

    @Test
    public void hashCode_test() {
        GradeList gradeList1 = new GradeList();
        GradeList gradeList2 = new GradeList();
        GradeList gradeList3 = new GradeList();
        
        Grade grade = new Grade("MATH", "WA1", "89");
        gradeList1 = gradeList1.addGrade(grade);
        gradeList2 = gradeList2.addGrade(grade);
        gradeList3 = gradeList3.addGrade(new Grade("SCIENCE", "Quiz1", "95"));

        // same values -> same hashcode
        assertEquals(gradeList1.hashCode(), gradeList2.hashCode());

        // different values -> different hashcode
        assertFalse(gradeList1.hashCode() == gradeList3.hashCode());
    }
}
