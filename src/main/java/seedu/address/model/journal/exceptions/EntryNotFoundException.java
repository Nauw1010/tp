package seedu.address.model.journal.exceptions;

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException() {
        super("Cannot find the entry");
    }
}
