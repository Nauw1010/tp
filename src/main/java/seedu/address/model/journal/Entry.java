package seedu.address.model.journal;

import seedu.address.model.person.UniquePersonList;

public class Entry {

    private final Title title;
    private final Date date;
    private final Description description;

    // Represents a contact list for a certain event
    private final UniquePersonList contactList;

    /**
     * Creates a new entry.
     */
    public Entry(Title title, Date date, Description description, UniquePersonList contactList) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.contactList = contactList;
    }

    /**
     * Returns true if the given entry is the same as the current one.
     */
    public boolean isSameEntry(Entry toCheck) {
        if (toCheck == this) {
            return true;
        }

        return toCheck != null && toCheck.title == this.title
                && toCheck.date == this.date && toCheck.description == this.description;
    }

}
