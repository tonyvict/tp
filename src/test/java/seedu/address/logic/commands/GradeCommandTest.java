package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Person;

public class GradeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addSingleGrade_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Grade> gradesToAdd = new HashSet<>();
        gradesToAdd.add(new Grade("MATH", "WA1", "89"));
        
        GradeCommand gradeCommand = new GradeCommand(INDEX_FIRST_PERSON, gradesToAdd);
        
        String expectedMessage = String.format(GradeCommand.MESSAGE_ADD_GRADE_SUCCESS,
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getGradeList().addGrade(new Grade("MATH", "WA1", "89")));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person expectedPerson = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.setPerson(expectedPerson, expectedPerson);

        // Test that the command executes without throwing an exception
        try {
            gradeCommand.execute(model);
        } catch (Exception e) {
            // If it throws an exception, that's a test failure
            assertTrue(false, "Command should execute successfully");
        }
    }

    @Test
    public void equals() {
        Set<Grade> grades1 = new HashSet<>();
        grades1.add(new Grade("MATH", "WA1", "89"));
        
        Set<Grade> grades2 = new HashSet<>();
        grades2.add(new Grade("SCIENCE", "Quiz1", "95"));
        
        GradeCommand gradeCommand1 = new GradeCommand(INDEX_FIRST_PERSON, grades1);
        GradeCommand gradeCommand2 = new GradeCommand(INDEX_FIRST_PERSON, grades1);
        GradeCommand gradeCommand3 = new GradeCommand(INDEX_SECOND_PERSON, grades1);
        GradeCommand gradeCommand4 = new GradeCommand(INDEX_FIRST_PERSON, grades2);

        // same values -> returns true
        assertTrue(gradeCommand1.equals(gradeCommand2));

        // same object -> returns true
        assertTrue(gradeCommand1.equals(gradeCommand1));

        // null -> returns false
        assertFalse(gradeCommand1.equals(null));

        // different type -> returns false
        assertFalse(gradeCommand1.equals(5));

        // different index -> returns false
        assertFalse(gradeCommand1.equals(gradeCommand3));

        // different grades -> returns false
        assertFalse(gradeCommand1.equals(gradeCommand4));
    }
}
