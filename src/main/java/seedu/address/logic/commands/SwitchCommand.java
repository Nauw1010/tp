package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

//@@author {zhXchD}
public class SwitchCommand extends Command {
    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": switches the current tab to the next tab.\n"
            + "Example: switch";

    public static final String MESSAGE_SUCCESS = "Switch to the next tab.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS).setSwitch();
    }
}
