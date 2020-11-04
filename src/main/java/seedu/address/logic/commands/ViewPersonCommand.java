package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

//@@author {zhXchD}
/**
 * Views a person at specified index in the current list displayed.
 */
public class ViewPersonCommand extends ViewCommand {

    public ViewPersonCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(targetIndex.getZeroBased());
        // model.updateFilteredPersonList(person -> person.isSamePerson(personToView));

        return new CommandResult(
                String.format(
                        MESSAGE_VIEW_SUCCESS,
                        "contact",
                        personToView.toString()
                )
        ).setAddressBookTab().setViewingPerson(personToView);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof ViewPersonCommand
                && ((ViewPersonCommand) other).targetIndex.equals(targetIndex));
    }
}
