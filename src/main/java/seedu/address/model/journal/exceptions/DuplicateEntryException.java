package seedu.address.model.journal.exceptions;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException() {
        super("You can not add a duplicate entry");
    }
}
