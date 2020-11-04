package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.journal.Entry;
import seedu.address.model.journal.UniqueEntryList;
import seedu.address.model.person.Person;

public class Journal implements ReadOnlyJournal {

    private final UniqueEntryList entryList;

    public Journal() {
        this.entryList = new UniqueEntryList();
    }

    /**
     * Overwrite the original journal with given data.
     * @param newData Journal to overwrite.
     */
    public Journal(ReadOnlyJournal newData) {
        this();
        resetData(newData);
    }

    // operations

    /**
     * Adds an entry to the existing journal.
     * @param newEntry Entry to be added.
     */
    public void addEntry(Entry newEntry) {
        requireNonNull(newEntry);
        this.entryList.add(newEntry);
    }

    /**
     * Removes a certain entry inside the journal.
     * @param target Target entry to be removed.
     */
    public void removeEntry(Entry target) {
        requireNonNull(target);
        this.entryList.remove(target);
    }

    //TODO: Add dependency between Journal and Person in DG UML diagram

    /**
     * Removes entry that associate with certain person.
     * @param person Target person to be removed from {@code AddressBook}.
     */
    public void removeAssociateEntryContact(Person person) {
        requireNonNull(person);

        HashSet<Entry> toDeleteEntry = new HashSet<>();

        for (Entry entry : entryList) {
            if (entry.isRelatedTo(person)) {
                toDeleteEntry.add(entry);
            }
        }

        for (Entry entry: toDeleteEntry) {
            entry.removeContact(person);
        }
    }

    // util methods

    /**
     * Checks whether there is a certain entry in journal.
     * @param toCheck Entry that needs to be checked.
     * @return True if the entry is inside the journal, false otherwise.
     */
    public boolean hasEntry(Entry toCheck) {
        return this.entryList.contains(toCheck);
    }

    /**
     * Resets the current journal with new data.
     * @param newData Journal that is used to overwrite.
     */
    public void resetData(ReadOnlyJournal newData) {
        requireNonNull(newData);

        setEntries(newData.getEntryList());
    }

    public void setEntries(List<Entry> entryList) {
        this.entryList.setEntries(entryList);
    }

    /**
     * Clear contact list for each entry.
     */
    public void clearContacts() {
        for (Entry entry : entryList) {
            for (Person person: entry.getContactList()) {
                entry.removeContact(person);
            }
        }
    }

    public void setEntry(Entry target, Entry editedEntry) {
        entryList.setEntry(target, editedEntry);
    }

    /**
     * Goes through the Journal and updates each entry involved with the
     * original contact with the edited contact.
     * @param target the original contact.
     * @param editedPerson the edited contact.
     */
    public void updateJournalContacts(
            Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        for (Entry entry : entryList) {
            if (entry.isRelatedTo(target)) {
                entry.setContact(target, editedPerson);
            }
        }
    }

    @Override
    public ObservableList<Entry> getEntryList() {
        return entryList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Journal
                && entryList.equals(((Journal) other).entryList));
    }
}
