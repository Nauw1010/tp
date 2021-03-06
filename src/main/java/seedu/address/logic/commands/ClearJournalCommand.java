package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Journal;
import seedu.address.model.Model;

//@@author {zhXchD}
/**
 * Clears the address book.
 */
public class ClearJournalCommand extends Command {

    public static final String COMMAND_WORD = "clearj";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all entries in the journal.\n"
            + "Example: clearj";

    public static final String MESSAGE_SUCCESS =
            "Journal has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setJournal(new Journal());
        return new CommandResult(MESSAGE_SUCCESS).setJournalTab();
    }
}
