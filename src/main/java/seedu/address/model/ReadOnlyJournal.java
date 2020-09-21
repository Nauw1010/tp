package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.journal.Entry;

public interface ReadOnlyJournal {

    /**
     * Returns an unmodifiable list of the entries.
     */
    ObservableList<Entry> getEntryList();

}
