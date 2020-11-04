package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.journal.Entry;

//@@author {zhXchD}
/**
 * Views journal entry at specified index of the list currently displayed.
 */
public class ViewJournalEntryCommand extends ViewCommand {

    public ViewJournalEntryCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Entry> lastShownList = model.getFilteredEntryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Entry entryToView = lastShownList.get(targetIndex.getZeroBased());
        // model.updateFilteredEntryList(entry -> entry.isSameEntry(entryToView));

        return new CommandResult(
                String.format(
                        MESSAGE_VIEW_SUCCESS,
                        "entry",
                        entryToView.toString()
                )
        ).setJournalTab().setViewingJournal(entryToView);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof ViewJournalEntryCommand
                && ((ViewJournalEntryCommand) other).targetIndex.equals(targetIndex));
    }
}
