package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonContainsKeywordPredicate;
import seedu.address.testutil.AddressBookBuilder;

/**
 * Unit tests for {@link ModelManager}.
 */
public class ModelManagerTest {

    private ModelManager modelManager;

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor() {
        ModelManager freshManager = new ModelManager();
        assertEquals(new UserPrefs(), freshManager.getUserPrefs());
        assertEquals(new GuiSettings(), freshManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(freshManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        ModelManager emptyManager = new ModelManager();
        assertFalse(emptyManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        ModelManager singleManager =
                new ModelManager(new AddressBookBuilder().withPerson(ALICE).build(), new UserPrefs());
        assertTrue(singleManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    // ===========================================================
    // Quick Search integration tests
    // ===========================================================
    @Test
    public void updateFilteredPersonList_namePartialMatch_returnsExpectedPersons() {
        // "Meier" should match BENSON and DANIEL by name (order preserved)
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("Meier"));
        modelManager.updateFilteredPersonList(predicate);
        assertEquals(Arrays.asList(BENSON, DANIEL), modelManager.getFilteredPersonList());
    }

    @Test
    public void updateFilteredPersonList_emailOrPhonePartialMatch_returnsExpectedPersons() {
        // "example" should match everyone with an email containing "example"
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("example"));
        modelManager.updateFilteredPersonList(predicate);
        assertTrue(modelManager.getFilteredPersonList().size() > 0);

        // "9482" matches ELLE, FIONA, GEORGE (in that order from TypicalPersons)
        predicate = new PersonContainsKeywordPredicate(Collections.singletonList("9482"));
        modelManager.updateFilteredPersonList(predicate);
        assertEquals(Arrays.asList(ELLE, FIONA, GEORGE), modelManager.getFilteredPersonList());
    }

    @Test
    public void updateFilteredPersonList_emptyInput_showsAllPersons() {
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.emptyList());
        modelManager.updateFilteredPersonList(predicate);
        assertEquals(getTypicalAddressBook().getPersonList(), modelManager.getFilteredPersonList());
    }

    // ===========================================================
    // Negative tests for Quick Search
    // ===========================================================
    @Test
    public void updateFilteredPersonList_nonexistentName_returnsEmptyList() {
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("Zelda"));
        modelManager.updateFilteredPersonList(predicate);
        assertTrue(modelManager.getFilteredPersonList().isEmpty());
    }

    @Test
    public void updateFilteredPersonList_nonexistentPhone_returnsEmptyList() {
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("9999"));
        modelManager.updateFilteredPersonList(predicate);
        assertTrue(modelManager.getFilteredPersonList().isEmpty());
    }

    @Test
    public void updateFilteredPersonList_nonexistentEmail_returnsEmptyList() {
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Collections.singletonList("abcdef@xyz"));
        modelManager.updateFilteredPersonList(predicate);
        assertTrue(modelManager.getFilteredPersonList().isEmpty());
    }

    @Test
    public void updateFilteredPersonList_multipleNonMatchingKeywords_returnsEmptyList() {
        PersonContainsKeywordPredicate predicate =
                new PersonContainsKeywordPredicate(Arrays.asList("Zelda", "9999"));
        modelManager.updateFilteredPersonList(predicate);
        assertTrue(modelManager.getFilteredPersonList().isEmpty());
    }

    // ===========================================================
    // equals()
    // ===========================================================
    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}

