package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.ValidCommand;
import seedu.address.model.journal.Entry;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger =
            LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final Journal journal;
    private final UserPrefs userPrefs;
    private final AliasMap aliasMap;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Entry> filteredEntries;

    private final FilteredList<Person> metBeforePersons;
    private final SortedList<Person> frequentPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(
            ReadOnlyAddressBook addressBook,
            ReadOnlyJournal journal,
            ReadOnlyUserPrefs userPrefs,
            ReadOnlyAliasMap aliasMap
    ) {
        super();
        requireAllNonNull(addressBook, journal, userPrefs);

        logger.fine(
                String.format(
                        "Initializing with address book: %s and user prefs %s",
                        addressBook,
                        userPrefs
                )
        );

        this.addressBook = new AddressBook(addressBook);
        this.journal = new Journal(journal);
        this.userPrefs = new UserPrefs(userPrefs);
        this.aliasMap = new AliasMap(aliasMap);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredEntries = new FilteredList<>(this.journal.getEntryList());
        metBeforePersons = new FilteredList<>(this.addressBook.getPersonList());
        metBeforePersons.setPredicate(this::hasMetBefore);

        frequentPersons = new SortedList<>(metBeforePersons);
    }

    public ModelManager() {
        this(new AddressBook(), new Journal(), new UserPrefs(), new AliasMap());
    }

    //=========== UserPrefs ====================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ==================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
        clearJournalContacts();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasName(Person person) {
        requireNonNull(person);
        return addressBook.hasName(person);
    }

    @Override
    public void deletePerson(Person target) {
        requireNonNull(target);
        addressBook.removePerson(target);
        journal.removeAssociateEntryContact(target);
        metBeforePersons.setPredicate(this::hasMetBefore);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Journal ======================================================

    @Override
    public void clearJournalContacts() {
        journal.clearContacts();
        metBeforePersons.setPredicate(this::hasMetBefore);
    }

    @Override
    public void setJournal(ReadOnlyJournal journal) {
        this.journal.resetData(journal);
        metBeforePersons.setPredicate(this::hasMetBefore);
    }

    @Override
    public ReadOnlyJournal getJournal() {
        return journal;
    }

    @Override
    public void addPerson(Person person) {
        requireNonNull(person);
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return journal.hasEntry(entry);
    }

    @Override
    public void addEntry(Entry entry) {
        requireNonNull(entry);
        journal.addEntry(entry);
        updateFilteredEntryList(e -> true);
    }

    @Override
    public void deleteEntry(Entry entry) {
        requireNonNull(entry);
        journal.removeEntry(entry);
        metBeforePersons.setPredicate(this::hasMetBefore);
    }

    @Override
    public void updateJournalContacts(
            Person originalPerson, Person updatedPerson) {
        requireAllNonNull(originalPerson, updatedPerson);
        journal.updateJournalContacts(originalPerson, updatedPerson);
    }

    @Override
    public void updateAlias(Map<String, ValidCommand> map) {
        requireNonNull(map);
        aliasMap.updateMap(map);
    }

    @Override
    public ReadOnlyAliasMap getAliasMap() {
        return aliasMap;
    }

    @Override
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);

        journal.setEntry(target, editedEntry);
        metBeforePersons.setPredicate(this::hasMetBefore);
    }

    //=========== Filtered Person List Accessors ===============================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the
     * internal list of {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public ObservableList<Person> getRecentPersonList() {
        return metBeforePersons.sorted((person1, person2) ->
            getLatestDate(person2).compareTo(getLatestDate(person1))
        );
    }

    @Override
    public ObservableList<Person> getFrequentPersonList() {
        return metBeforePersons.sorted((person1, person2) ->
            Long.compare(getFrequency(person2), getFrequency(person1))
        );
    }

    /**
     * Returns an unmodifiable view of the list of {@code Journal} backed by the
     * internal list of {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Entry> getFilteredEntryList() {
        return filteredEntries;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        metBeforePersons.setPredicate(this::hasMetBefore);
    }

    @Override
    public void updateFilteredEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredEntries.setPredicate(predicate);
        metBeforePersons.setPredicate(this::hasMetBefore);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
            && userPrefs.equals(other.userPrefs)
            && filteredPersons.equals(other.filteredPersons)
            && filteredEntries.equals(other.filteredEntries);
    }

    private boolean hasMetBefore(Person person) {
        assert (addressBook.getPersonList().contains(person));
        for (Entry entry : journal.getEntryList()) {
            if (entry.isRelatedTo(person)) {
                return true;
            }
        }
        return false;
    }

    private LocalDateTime getLatestDate(Person person) {
        assert (getAddressBook().getPersonList().contains(person));
        return getJournal().getEntryList().stream()
                .filter(entry -> entry.isRelatedTo(person))
                .max(Comparator.comparing(e -> e.getDate().date))
                .map(entry -> entry.getDate().date)
                .orElse(LocalDateTime.MIN);
    }

    private long getFrequency(Person person) {
        assert (getAddressBook().getPersonList().contains(person));
        return getJournal().getEntryList().stream()
                .filter(entry -> entry.isRelatedTo(person))
                .count();
    }
}
