package seedu.address.model.journal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEntries.TEST_ENTRY_DEFAULT;
import static seedu.address.testutil.TypicalEntries.TEST_ENTRY_DIFF_CONTACTS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import seedu.address.model.journal.exceptions.ContactNotInListException;
import seedu.address.model.person.Person;
import seedu.address.testutil.EntryBuilder;
import seedu.address.testutil.PersonBuilder;

public class EntryTest {

    @Nested
    @DisplayName("miscellaneous operations")
    class Misc {
        @Test
        @DisplayName("should throw UnsupportedOperationException if tags are "
                + "attempted to be removed")
        public void asObservableList_modifyList_throwsUnsupportedOperationException() {
            Entry entry = new EntryBuilder().build();
            assertThrows(UnsupportedOperationException.class, () ->
                    entry.getContactList().remove(0));
        }
    }

    @Nested
    @DisplayName("isSameEntry")
    class IsSameEntry {
        @Test
        @DisplayName("Should be true if the same entry")
        public void isSameEntry_sameEntry_true() {
            assertTrue(TEST_ENTRY_DEFAULT.isSameEntry(TEST_ENTRY_DEFAULT));
        }

        @Test
        @DisplayName("Should return false if the entry is null")
        public void isSameEntry_null_false() {
            assertFalse(TEST_ENTRY_DEFAULT.isSameEntry(null));
        }
    }

    @Nested
    @DisplayName("setContact method")
    class SetContact {
        private final Entry entry =
                new EntryBuilder(TEST_ENTRY_DIFF_CONTACTS).build();

        @Test
        @DisplayName("should throw NullPointerException if editedPerson is "
                + "null")
        public void setContact_nullEditedPerson_throwsNullPointerException() {
            Assertions.assertThrows(
                    NullPointerException.class, () ->
                            entry.setContact(
                                    getTypicalPersons().get(0),
                                    null
                            )
            );
        }

        @Test
        @DisplayName("should throw NullPointerException if target is null")
        public void setContact_nullTarget_throwsNullPointerException() {
            Assertions.assertThrows(
                    NullPointerException.class, () ->
                            entry.setContact(
                                    null,
                                    new PersonBuilder().build()
                            )
            );
        }

        @Test
        @DisplayName("should update journal involving contacts the updated "
                + "contact")
        public void setContact_validPersons_successful() {
            List<Person> newContactList = entry.getContactList()
                    .stream()
                    .filter(person -> !person.equals(CARL))
                    .collect(Collectors.toList());
            Person newPerson = new PersonBuilder(CARL)
                    .withName("Peter")
                    .withAddress("abc road")
                    .build(CARL.getUuid());
            newContactList.add(newPerson);
            Entry newEntry = new EntryBuilder(entry)
                    .withContacts(newContactList.toArray(new Person[0]))
                    .build();

            entry.setContact(CARL, newPerson);
            assertEquals(entry, newEntry);
        }
    }

    @Nested
    @DisplayName("equals method")
    class Equals {
        @Test
        @DisplayName("Should return true if the same instance of entry")
        public void equals_sameInstance_true() {
            assertTrue(TEST_ENTRY_DEFAULT.equals(TEST_ENTRY_DEFAULT));
        }

        @Test
        @DisplayName("Should return true if the Entry content is the same")
        public void equals_sameContent_true() {
            Entry testEntry = new EntryBuilder().build();
            assertEquals(TEST_ENTRY_DEFAULT, testEntry);
        }

        @Test
        @DisplayName("Should return false if the content is differnet")
        public void equals_diffContent_false() {
            Entry testEntry = new EntryBuilder().withTitle("Decide the product").build();
            assertNotEquals(TEST_ENTRY_DEFAULT, testEntry);
        }
    }

    @Nested
    @DisplayName("removeContact test")
    class RemoveContact {
        @Test
        @DisplayName("Should throw ContactNotInListException")
        public void removeContact_notInList_throwContactNotInListException() {
            Entry testEntry = new EntryBuilder().build();
            assertThrows(ContactNotInListException.class, () -> testEntry.removeContact(ALICE));
        }

        @Test
        @DisplayName("Should remove the contact inside the entry")
        public void removeContact_success_removeCotactInList() {
            Entry testEntry = new EntryBuilder().withContacts(ALICE).build();
            assertTrue(testEntry.isRelatedTo(ALICE));
            testEntry.removeContact(ALICE);
            assertFalse(testEntry.isRelatedTo(ALICE));
        }
    }

}
