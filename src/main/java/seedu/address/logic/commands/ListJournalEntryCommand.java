package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

//@@author {zhXchD}
/**
 * Lists all persons in the address book to the user.
 */
public class ListJournalEntryCommand extends Command {

    public static final String COMMAND_WORD = "listj";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all entries in the journal.\n"
            + "Example: listj";

    public static final String MESSAGE_SUCCESS = "Listed all journal entries";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEntryList(entry -> true);
        return new CommandResult(MESSAGE_SUCCESS).setJournalTab();
    }
}
