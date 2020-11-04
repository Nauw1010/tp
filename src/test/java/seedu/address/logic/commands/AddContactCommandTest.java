package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ValidCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAliasMap;
import seedu.address.model.ReadOnlyJournal;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.journal.Entry;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddContactCommandTest {

    @Nested
    @DisplayName("constructor")
    class Constructor {
        @Test
        @DisplayName("should throw NullPointerException if null person is "
                + "passed into constructor")
        public void constructor_nullPerson_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    new AddContactCommand(null));
        }
    }

    @Nested
    @DisplayName("execute method")
    class Execute {
        @Test
        @DisplayName("should add person successfully if person is valid")
        public void execute_personAcceptedByModel_addSuccessful()
                throws Exception {
            ModelStubAcceptingPersonAdded modelStub =
                    new ModelStubAcceptingPersonAdded();
            Person validPerson = new PersonBuilder().build();

            CommandResult commandResult =
                    new AddContactCommand(validPerson).execute(modelStub);

            assertEquals(
                    String.format(AddContactCommand.MESSAGE_SUCCESS, validPerson),
                    commandResult.getFeedbackToUser()
            );
            assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        }

        @Test
        @DisplayName("should throw CommandException if person is already in "
                + "the list")
        public void execute_duplicatePerson_throwsCommandException() {
            Person validPerson = new PersonBuilder().build();
            AddContactCommand addCommand = new AddContactCommand(validPerson);
            ModelStub modelStub = new ModelStubWithPerson(validPerson);

            assertThrows(
                    CommandException.class,
                    AddContactCommand.MESSAGE_DUPLICATE_PERSON, () ->
                            addCommand.execute(modelStub)
            );
        }
    }

    @Nested
    @DisplayName("equals method")
    class Equals {
        private Person alice = new PersonBuilder().withName("Alice").build();
        private Person bob = new PersonBuilder().withName("Bob").build();
        private AddContactCommand addAliceCommand = new AddContactCommand(alice);
        private AddContactCommand addBobCommand = new AddContactCommand(bob);

        @Test
        @DisplayName("should return true if same object")
        public void equals_sameObject_true() {
            assertTrue(addAliceCommand.equals(addAliceCommand));
        }

        @Test
        @DisplayName("should return true if same values")
        public void equals_sameValues_true() {
            AddContactCommand addAliceCommandCopy = new AddContactCommand(alice);
            assertTrue(addAliceCommand.equals(addAliceCommandCopy));
        }

        @Test
        @DisplayName("should return false if different values")
        public void equals_differentValues_false() {
            assertFalse(addAliceCommand.equals(1));
        }

        @Test
        @DisplayName("should return false if null")
        public void equals_null_false() {
            assertFalse(addAliceCommand.equals(null));
        }

        @Test
        @DisplayName("should return false if different person")
        public void equals_differentPerson_false() {
            assertFalse(addAliceCommand.equals(addBobCommand));
        }
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearJournalContacts() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setJournal(ReadOnlyJournal newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyJournal getJournal() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasName(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEntry(Entry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateJournalContacts(
                Person originalPerson, Person updatedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateAlias(Map<String, ValidCommand> map) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAliasMap getAliasMap() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEntry(Entry target, Entry editedEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEntry(Entry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEntry(Entry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Entry> getFilteredEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEntryList(Predicate<Entry> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getRecentPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFrequentPersonList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public boolean hasName(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::hasSameName);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
