package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEntries.TEST_ENTRY_DEFAULT;
import static seedu.address.testutil.TypicalEntries.TEST_ENTRY_DIFF_CONTACTS;
import static seedu.address.testutil.TypicalEntries.getTypicalEntries;
import static seedu.address.testutil.TypicalEntries.getTypicalJournal;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import seedu.address.model.journal.Entry;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.EntryBuilder;
import seedu.address.testutil.JournalBuilder;
import seedu.address.testutil.PersonBuilder;


public class JournalTest {

    private final Journal journal = new Journal();

    @Nested
    @DisplayName("constructor")
    class Constructor {
        @Test
        @DisplayName("Should create an empty entry list")
        public void constructor_create_emptyList() {
            assertEquals(Collections.emptyList(), new Journal().getEntryList());
        }

        @Test
        @DisplayName("Should create journal according to given entry")
        public void constructor_create_givenData() {
            Journal journal = getTypicalJournal();
            List<Entry> entries = getTypicalEntries();

            for (int i = 0; i < journal.getEntryList().size(); i++) {
                assertEquals(entries.get(i), journal.getEntryList().get(i));
            }
        }
    }

    @Nested
    @DisplayName("hasEntry method")
    class HasEntry {
        @Test
        @DisplayName("Should throw null pointer exception when pass null")
        public void hasEntry_throw_nullPointerException() {
            assertThrows(NullPointerException.class, () -> journal.hasEntry(null));
        }

        @Test
        @DisplayName("Should return false when entry is not in the journal")
        public void hasEntry_false_entryNotInList() {
            assertFalse(journal.hasEntry(TEST_ENTRY_DEFAULT));
        }

        @Test
        @DisplayName("Should return true when the entry is in the journal")
        public void hasEntry_true_entryInList() {
            assertTrue(getTypicalJournal().hasEntry(TEST_ENTRY_DEFAULT));
        }
    }

    @Nested
    @DisplayName("updateJournalContacts method")
    class UpdateJournalContacts {
        @Test
        @DisplayName("should throw NullPointerException if editedPerson is "
                + "null")
        public void updateJournalContacts_nullEditedPerson_throwsNullPointerException() {
            assertThrows(
                    NullPointerException.class, () ->
                            journal.updateJournalContacts(
                                    getTypicalPersons().get(0),
                                    null
                            )
            );
        }

        @Test
        @DisplayName("should throw NullPointerException if target is null")
        public void updateJournalContacts_nullTarget_throwsNullPointerException() {
            assertThrows(
                    NullPointerException.class, () ->
                            journal.updateJournalContacts(
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
            Journal originalJournal = new JournalBuilder()
                    .withEntry(entryOne)
                    .withEntry(entryTwo)
                    .build();
            Journal expectedJournal = new JournalBuilder()
                    .withEntry(newEntryOne)
                    .withEntry(newEntryTwo)
                    .build();

            originalJournal.updateJournalContacts(CARL, newPerson);
            assertEquals(expectedJournal, originalJournal);
        }
    }

    @Nested
    @DisplayName("getEntryList method")
    class GetEntryList {
        @Test
        @DisplayName("should throw UnsupportedOperationException")
        public void getEntryList_modifyList_throwsUnsupportedOperationException() {
            assertThrows(UnsupportedOperationException.class, () ->
                    journal.getEntryList().remove(0));
        }
    }

    @Nested
    @DisplayName("removeAssociateEntryContact method")
    class RemoveAssociateEntryContact {
        @Test
        @DisplayName("should throw NullPointerException if pass in a null")
        public void removeAssociateEntryContact_null_throwNullPointerException() {
            assertThrows(NullPointerException.class, () -> journal.removeAssociateEntryContact(null));
        }

        @Test
        @DisplayName("should remove the entry that associate with the person")
        public void removeAssociateEntryContact_success_removeTheAssociateEntry() {
            UniquePersonList contactList = new UniquePersonList();
            contactList.add(ALICE);
            Entry test = new EntryBuilder()
                    .withContacts(
                            contactList
                                    .asUnmodifiableObservableList()
                                    .toArray(new Person[0])
                    )
                    .build();
            journal.addEntry(test);
            journal.removeAssociateEntryContact(ALICE);
            assertFalse(test.isRelatedTo(ALICE));
        }
    }
}
