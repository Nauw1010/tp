package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEntries.TEST_ENTRY_DIFF_CONTACTS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.journal.Entry;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.EntryBuilder;
import seedu.address.testutil.JournalBuilder;
import seedu.address.testutil.PersonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Nested
    @DisplayName("constructor")
    class Constructor {
        @Test
        public void constructor() {
            assertEquals(new UserPrefs(), modelManager.getUserPrefs());
            assertEquals(new GuiSettings(), modelManager.getGuiSettings());
            assertEquals(
                    new AddressBook(),
                    new AddressBook(modelManager.getAddressBook())
            );
        }
    }

    @Nested
    @DisplayName("setUserPrefs method")
    class SetUserPrefs {
        @Test
        @DisplayName("should throw NullPointerException if user prefs is null")
        public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    modelManager.setUserPrefs(null));
        }

        @Test
        @DisplayName("should copy user prefs if user prefs is valid")
        public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
            UserPrefs userPrefs = new UserPrefs();
            userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
            userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
            modelManager.setUserPrefs(userPrefs);
            assertEquals(userPrefs, modelManager.getUserPrefs());

            // Modifying userPrefs should not modify modelManager's userPrefs
            UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
            userPrefs.setAddressBookFilePath(Paths.get(
                    "new/address/book/file/path"));
            assertEquals(oldUserPrefs, modelManager.getUserPrefs());
        }
    }

    @Nested
    @DisplayName("setGuiSettings method")
    class SetGuiSettings {
        @Test
        @DisplayName("should throw NullPointerException if gui settings is "
                + "null")
        public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    modelManager.setGuiSettings(null));
        }

        @Test
        @DisplayName("should set gui settings if gui settings is valid")
        public void setGuiSettings_validGuiSettings_setsGuiSettings() {
            GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
            modelManager.setGuiSettings(guiSettings);
            assertEquals(guiSettings, modelManager.getGuiSettings());
        }
    }

    @Nested
    @DisplayName("setAddressBookFilePath method")
    class SetAddressBookFilePath {
        @Test
        @DisplayName("should throw NullPointerException if path is null")
        public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    modelManager.setAddressBookFilePath(null));
        }

        @Test
        @DisplayName("should set file path if path is valid")
        public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
            Path path = Paths.get("address/book/file/path");
            modelManager.setAddressBookFilePath(path);
            assertEquals(path, modelManager.getAddressBookFilePath());
        }
    }

    @Nested
    @DisplayName("hasPerson method")
    class HasPerson {
        @Test
        @DisplayName("should throw NullPointerException if person is null")
        public void hasPerson_nullPerson_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    modelManager.hasPerson(null));
        }

        @Test
        @DisplayName("should return false if person not in address book")
        public void hasPerson_personNotInAddressBook_returnsFalse() {
            assertFalse(modelManager.hasPerson(ALICE));
        }

        @Test
        @DisplayName("should return true if person in address book")
        public void hasPerson_personInAddressBook_returnsTrue() {
            modelManager.addPerson(ALICE);
            assertTrue(modelManager.hasPerson(ALICE));
        }
    }

    @Nested
    @DisplayName("updateJournalContacts method")
    class UpdateJournalContacts {
        @Test
        @DisplayName("should throw NullPointerException if editedPerson is "
                + "null")
        public void updateJournalContacts_nullEditedPerson_throwsNullPointerException() {
            Assertions.assertThrows(
                    NullPointerException.class, () ->
                            modelManager.updateJournalContacts(
                                    getTypicalPersons().get(0),
                                    null
                            )
            );
        }

        @Test
        @DisplayName("should throw NullPointerException if target is null")
        public void updateJournalContacts_nullTarget_throwsNullPointerException() {
            Assertions.assertThrows(
                    NullPointerException.class, () ->
                            modelManager.updateJournalContacts(
                                    null,
                                    new PersonBuilder().build()
                            )
            );
        }

        @Test
        @DisplayName("should update journal involving contacts the updated "
                + "contact")
        public void updateJournalContacts_validPersons_successful() {
            Person newPerson = new PersonBuilder(CARL)
                    .withName("Peter")
                    .withPhone("12345")
                    .build();
            Entry entryOne = new EntryBuilder(TEST_ENTRY_DIFF_CONTACTS).build();
            Entry entryTwo = new EntryBuilder().withContacts(CARL).build();
            Entry newEntryOne = new EntryBuilder(entryOne).build();
            Entry newEntryTwo = new EntryBuilder(entryTwo).build();
            newEntryOne.setContact(CARL, newPerson);
            newEntryTwo.setContact(CARL, newPerson);
            ModelManager original = new ModelManager(
                    new AddressBook(),
                    new JournalBuilder()
                            .withEntry(entryOne)
                            .withEntry(entryTwo)
                            .build(),
                    new UserPrefs(),
                    new AliasMap()
            );
            ModelManager expected = new ModelManager(
                    new AddressBook(),
                    new JournalBuilder()
                            .withEntry(newEntryOne)
                            .withEntry(newEntryTwo)
                            .build(),
                    new UserPrefs(),
                    new AliasMap()
            );

            original.updateJournalContacts(CARL, newPerson);
            assertEquals(expected, original);
        }
    }


    @Nested
    @DisplayName("getFilteredPersonList method")
    class GetFilteredPersonList {
        @Test
        @DisplayName("should throw UnsupportedOperationException if list is "
                + "modified from getFilteredPersonList")
        public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
            assertThrows(UnsupportedOperationException.class, () ->
                    modelManager.getFilteredPersonList().remove(0));
        }
    }

    @Nested
    @DisplayName("getFilteredEntryList method")
    class GetFilteredEntryList {
        @Test
        @DisplayName("Should throw UnsupportedOperationException when try to modify the list")
        public void getFilteredEntryList_modifyList_throwUnsupportedOperationException() {
            assertThrows(UnsupportedOperationException.class, () ->
                    modelManager.getFilteredEntryList().remove(0));
        }
    }

    @Nested
    @DisplayName("equals method")
    class Equals {
        private final AddressBook addressBook = new AddressBookBuilder()
                .withPerson(ALICE)
                .withPerson(BENSON)
                .build();
        private final AddressBook differentAddressBook = new AddressBook();
        private final UserPrefs userPrefs = new UserPrefs();

        @BeforeEach
        public void beforeEach() {
            modelManager = new ModelManager(
                    addressBook,
                    new Journal(),
                    userPrefs,
                    new AliasMap()
            );
        }

        @Test
        @DisplayName("should return true if same value")
        public void equals_sameValues_true() {
            ModelManager modelManagerCopy =
                    new ModelManager(addressBook, new Journal(), userPrefs, new AliasMap());
            assertEquals(modelManagerCopy, modelManager);
        }

        @Test
        @DisplayName("should return true if same object")
        public void equals_sameObject_true() {
            assertEquals(modelManager, modelManager);
        }

        @Test
        @DisplayName("should return false if null")
        public void equals_null_false() {
            assertNotEquals(modelManager, null);
        }

        @Test
        @DisplayName("should return false if different type")
        public void equals_differentTypes_false() {
            assertNotEquals(modelManager, 5);
        }

        @Test
        @DisplayName("should return false if different address book")
        public void equals_differentAddressBook_false() {
            System.out.println(modelManager);
            System.out.println(new ModelManager(differentAddressBook,
                    new Journal(), userPrefs, new AliasMap()));
            assertNotEquals(new ModelManager(
                    differentAddressBook,
                    new Journal(),
                    userPrefs,
                    new AliasMap()
            ), modelManager);
        }

        @Test
        @DisplayName("should return false if different filtered list")
        public void equals_differentFilteredList_false() {
            String[] keywords = ALICE.getName().fullName.split("\\s+");
            modelManager.updateFilteredPersonList(
                    new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
            assertNotEquals(new ModelManager(
                    addressBook,
                    new Journal(),
                    userPrefs,
                    new AliasMap()
            ), modelManager);
        }


        @Test
        @DisplayName("should return false if different user prefs")
        public void equals_differentUserPrefs_false() {
            // resets modelManager to initial state for upcoming tests
            modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            // different userPrefs -> returns false
            UserPrefs differentUserPrefs = new UserPrefs();
            differentUserPrefs.setAddressBookFilePath(Paths.get(
                    "differentFilePath"));
            assertNotEquals(new ModelManager(
                    addressBook,
                    new Journal(),
                    differentUserPrefs,
                    new AliasMap()
            ), modelManager);
        }
    }

    @Nested
    @DisplayName("DeleteEntry method")
    class DeleteEntry {
        @Test
        @DisplayName("Should delete entry in the list")
        public void deleteEntry_success_removeEntryInModel() {
            Entry testEntry = new EntryBuilder().build();
            modelManager.addEntry(testEntry);
            assertTrue(modelManager.hasEntry(testEntry));
            modelManager.deleteEntry(testEntry);
            assertFalse(modelManager.hasEntry(testEntry));
        }
    }
}
