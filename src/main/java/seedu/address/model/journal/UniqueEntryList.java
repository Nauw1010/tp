package seedu.address.model.journal;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.journal.exceptions.DuplicateEntryException;
import seedu.address.model.journal.exceptions.EntryNotFoundException;

public class UniqueEntryList implements Iterable<Entry> {

    private final ObservableList<Entry> internalList =
            FXCollections.observableArrayList();
    private final ObservableList<Entry> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if there is a same entry in the list.
     */
    public boolean contains(Entry toCheck) {
        return internalList.stream().anyMatch(toCheck::isSameEntry);
    }

    /**
     * Returns an observable and unmodified list of the entries.
     */
    public ObservableList<Entry> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Adds an entry to the entry list.
     * @param entry Entry that will be added.
     */
    public void add(Entry entry) {
        requireNonNull(entry);
        if (contains(entry)) {
            throw new DuplicateEntryException();
        }
        internalList.add(entry);
    }

    /**
     * Replace the {@code target} Entry with a new {@code editedEntry}.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EntryNotFoundException();
        }

        if (!target.isSameEntry(editedEntry) && contains(editedEntry)) {
            throw new DuplicateEntryException();
        }

        internalList.set(index, editedEntry);
    }

    /**
     * Removes an entry from entry list.
     * @param entry Entry that will be removed.
     */
    public void remove(Entry entry) {
        requireNonNull(entry);
        if (!internalList.remove(entry)) {
            throw new EntryNotFoundException();
        }
    }

    public void setEntries(List<Entry> replacement) {
        this.internalList.setAll(replacement);
    }

    @Override
    public Iterator<Entry> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueEntryList
                        && ((UniqueEntryList) other).internalList.equals(
                                internalList));
    }
}
