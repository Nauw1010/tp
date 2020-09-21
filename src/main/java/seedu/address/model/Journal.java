package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.journal.Entry;
import seedu.address.model.journal.UniqueEntryList;

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

    @Override
    public ObservableList<Entry> getEntryList() {
        return entryList.asUnmodifiableObservableList();
    }
}
