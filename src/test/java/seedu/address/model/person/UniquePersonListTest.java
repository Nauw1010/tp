package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniquePersonListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Nested
    @DisplayName("contains method")
    class Contains {
        @Test
        @DisplayName("should throw NullPointerException")
        public void contains_nullPerson_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    uniquePersonList.contains(null));
        }

        @Test
        @DisplayName("should return false if person not in list")
        public void contains_personNotInList_returnsFalse() {
            assertFalse(uniquePersonList.contains(ALICE));
        }

        @Test
        @DisplayName("should return true if person in list")
        public void contains_personInList_returnsTrue() {
            uniquePersonList.add(ALICE);
            assertTrue(uniquePersonList.contains(ALICE));
        }

        @Test
        @DisplayName("should return true if person is same identity")
        public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
            uniquePersonList.add(ALICE);
            Person editedAlice =
                    new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                            .withTags(VALID_TAG_HUSBAND)
                            .build();
            assertTrue(uniquePersonList.contains(editedAlice));
        }
    }

    @Nested
    @DisplayName("containsName method")
    class ContainsName {
        @Test
        @DisplayName("should throw NullPointerException")
        public void containsName_nullPerson_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    uniquePersonList.containsName(null));
        }

        @Test
        @DisplayName("should return false if name not in list")
        public void contains_personNotInList_returnsFalse() {
            // sample contact with all fields
            assertFalse(uniquePersonList.containsName(ALICE));

            // sample contact with only name
            assertFalse(uniquePersonList.containsName(
                    new PersonBuilder()
                            .setBlankFields()
                            .build()
            ));
        }

        @Test
        @DisplayName("should return true if name in list")
        public void contains_personInList_returnsTrue() {
            uniquePersonList.add(ALICE);
            assertTrue(uniquePersonList.containsName(ALICE));
        }
    }

    @Nested
    @DisplayName("add method")
    class Add {
        @Test
        @DisplayName("should throw NullPointerException if person is null")
        public void add_nullPerson_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    uniquePersonList.add(null));
        }

        @Test
        @DisplayName("should throw DuplicatePersonException if person is "
                + "already in the list")
        public void add_duplicatePerson_throwsDuplicatePersonException() {
            uniquePersonList.add(ALICE);
            assertThrows(DuplicatePersonException.class, () ->
                    uniquePersonList.add(ALICE));
        }

        @Test
        @DisplayName("should throw DuplicatePersonException if name is "
                + "already in the list")
        public void add_duplicateName_throwsDuplicatePersonException() {
            uniquePersonList.add(ALICE);
            assertThrows(DuplicatePersonException.class, () ->
                    uniquePersonList.add(
                            new PersonBuilder()
                                    .setBlankFields()
                                    .build()));
        }

    }

    @Nested
    @DisplayName("setPerson method")
    class SetPerson {
        @Test
        @DisplayName("should throw NullPointerException if target is null")
        public void setPerson_nullTargetPerson_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    uniquePersonList.setPerson(null, ALICE));
        }

        @Test
        @DisplayName("should throw NullPointerException if edited person is "
                + "null")
        public void setPerson_nullEditedPerson_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    uniquePersonList.setPerson(ALICE, null));
        }

        @Test
        @DisplayName("should throw PersonNotFoundException if person does not"
                + " exist in the list")
        public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
            assertThrows(PersonNotFoundException.class, () ->
                    uniquePersonList.setPerson(ALICE, ALICE));
        }

        @Test
        @DisplayName("should successfully edit person if the edited person is"
                + " the same person")
        public void setPerson_editedPersonIsSamePerson_success() {
            uniquePersonList.add(ALICE);
            uniquePersonList.setPerson(ALICE, ALICE);
            UniquePersonList expectedUniquePersonList = new UniquePersonList();
            expectedUniquePersonList.add(ALICE);
            assertEquals(expectedUniquePersonList, uniquePersonList);
        }

        @Test
        @DisplayName("should successfully edit person if edited person has "
                + "the same identity")
        public void setPerson_editedPersonHasSameIdentity_success() {
            uniquePersonList.add(ALICE);
            Person editedAlice =
                    new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                            .withTags(VALID_TAG_HUSBAND)
                            .build();
            uniquePersonList.setPerson(ALICE, editedAlice);
            UniquePersonList expectedUniquePersonList = new UniquePersonList();
            expectedUniquePersonList.add(editedAlice);
            assertEquals(expectedUniquePersonList, uniquePersonList);
        }

        @Test
        @DisplayName("should successfully edit person if edited person has "
                + "different identity")
        public void setPerson_editedPersonHasDifferentIdentity_success() {
            uniquePersonList.add(ALICE);
            uniquePersonList.setPerson(ALICE, BOB);
            UniquePersonList expectedUniquePersonList = new UniquePersonList();
            expectedUniquePersonList.add(BOB);
            assertEquals(expectedUniquePersonList, uniquePersonList);
        }

        @Test
        @DisplayName("should throw DuplicatePersonException if edited person "
                + "has non unique identity")
        public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
            uniquePersonList.add(ALICE);
            uniquePersonList.add(BOB);
            assertThrows(DuplicatePersonException.class, () ->
                    uniquePersonList.setPerson(ALICE, BOB));
        }
    }

    @Nested
    @DisplayName("remove method")
    class Remove {
        @Test
        @DisplayName("should throw NullPointerException if person is null")
        public void remove_nullPerson_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    uniquePersonList.remove(null));
        }

        @Test
        @DisplayName("should throw PersonNotFoundException if person does not"
                + " exist in the list")
        public void remove_personDoesNotExist_throwsPersonNotFoundException() {
            assertThrows(PersonNotFoundException.class, () ->
                    uniquePersonList.remove(ALICE));
        }

        @Test
        @DisplayName("should successfully remove person if person exists in "
                + "the list")
        public void remove_existingPerson_removesPerson() {
            uniquePersonList.add(ALICE);
            uniquePersonList.remove(ALICE);
            UniquePersonList expectedUniquePersonList = new UniquePersonList();
            assertEquals(expectedUniquePersonList, uniquePersonList);
        }

        @Test
        @DisplayName("should throw NullPointerException if list is null")
        public void setPersons_nullUniquePersonList_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    uniquePersonList.setPersons((UniquePersonList) null));
        }
    }

    @Nested
    @DisplayName("setPersons method")
    class SetPersons {
        @Test
        @DisplayName("should replace list with provided UniquePersonList")
        public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
            uniquePersonList.add(ALICE);
            UniquePersonList expectedUniquePersonList = new UniquePersonList();
            expectedUniquePersonList.add(BOB);
            uniquePersonList.setPersons(expectedUniquePersonList);
            assertEquals(expectedUniquePersonList, uniquePersonList);
        }

        @Test
        @DisplayName("should throw NullPointerException if the provided list "
                + "is null")
        public void setPersons_nullList_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    uniquePersonList.setPersons((List<Person>) null));
        }

        @Test
        @DisplayName("should replace list with provided list")
        public void setPersons_list_replacesOwnListWithProvidedList() {
            uniquePersonList.add(ALICE);
            List<Person> personList = Collections.singletonList(BOB);
            uniquePersonList.setPersons(personList);
            UniquePersonList expectedUniquePersonList = new UniquePersonList();
            expectedUniquePersonList.add(BOB);
            assertEquals(expectedUniquePersonList, uniquePersonList);
        }

        @Test
        @DisplayName("should throw DuplicatePersonException if provided list "
                + "has duplicate persons")
        public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
            List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
            assertThrows(DuplicatePersonException.class, () ->
                    uniquePersonList.setPersons(listWithDuplicatePersons));
        }
    }

    @Nested
    @DisplayName("miscellaneous operations")
    class Misc {
        @Test
        @DisplayName("should throw UnsupportedOperationException if "
                + "uniquePersonList is modified")
        public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
            assertThrows(UnsupportedOperationException.class, () ->
                    uniquePersonList.asUnmodifiableObservableList()
                            .remove(0));
        }
    }
}
