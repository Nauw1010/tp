package seedu.address.logic.commands;

import seedu.address.model.Model;

//@@author {Nauw1010}

/**
 * Changes the color theme of IntelliJournal.
 */
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "changetheme";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Changes the main color theme of IntelliJournal\n"
        + "Example: changetheme";

    public static final String MESSAGE_THEME_CHANGED =
        "Theme changed ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_THEME_CHANGED).setChangingTheme().setSameTab();
    }
}
