package seedu.address.model;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.parser.ValidCommand;
import seedu.address.model.journal.Entry;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;


    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Entry> PREDICATE_SHOW_ALL_ENTRIES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Update journal regarding to a {@code addressbook}
     */
    void clearJournalContacts();

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces journal book data with the data in {@code journal}.
     */
    void setJournal(ReadOnlyJournal journal);

    /**
     * Updates entries involving the contact given.
     */
    void updateJournalContacts(Person originalPerson, Person updatedPerson);

    /**
     * Returns the Journal
     */
    ReadOnlyJournal getJournal();

    /**
     * Returns true if a person with the same identity as {@code person} exists
     * in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same name as {@code person} exists
     * in the address book.
     */
    boolean hasName(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as
     * another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns true if a journal entry with the same identity as {@code entry}
     * exists in the journal.
     */
    boolean hasEntry(Entry entry);

    /**
     * Adds the given person.
     * {@code entry} must not already exist in the journal.
     */
    void addEntry(Entry entry);

    /**
     * Delete given entry.
     * @param entry Target entry to delete.
     */
    void deleteEntry(Entry entry);

    /**
     * Update the alias with a given map.
     */
    void updateAlias(Map<String, ValidCommand> map);

    /**
     * Returns the aliasMap
     */
    ReadOnlyAliasMap getAliasMap();

    /**
     * Replaces the given entry {@code target} with {@code description}
     * @param target Target entry to be replaced.
     * @param editedEntry New entry.
     */
    void setEntry(Entry target, Entry editedEntry);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered journal list
     */
    ObservableList<Entry> getFilteredEntryList();

    ObservableList<Person> getRecentPersonList();

    ObservableList<Person> getFrequentPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given
     * {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    void updateFilteredEntryList(Predicate<Entry> predicate);
}
