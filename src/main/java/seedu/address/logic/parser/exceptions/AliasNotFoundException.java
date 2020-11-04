package seedu.address.logic.parser.exceptions;

public class AliasNotFoundException extends RuntimeException {
    public AliasNotFoundException() {
        super("Cannot delete alias because alias is not in system.");
    }
}
