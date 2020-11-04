package seedu.address.logic.commands;

import seedu.address.logic.parser.ValidCommand;
import seedu.address.model.Model;

//@@author {Nauw1010}
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows program usage instructions.\n"
            + "Parameters: " + COMMAND_WORD + " [of/COMMAND]\n"
            + "Example: " + COMMAND_WORD + " of/addj";

    public static final String SHOWING_HELP_MESSAGE = "Showing help.\n";

    protected boolean isShowHelpWindow;
    protected ValidCommand validCommandType;

    /**
     * Constructs a {@code HelpCommand} specified showing the help window
     * or not.
     * @param isShowHelpWindow show the help window or not.
     */
    public HelpCommand(boolean isShowHelpWindow) {
        this.isShowHelpWindow = isShowHelpWindow;
        validCommandType = null;
    }

    /**
     * Constructs a {@code HelpCommand} of a specific valid command.
     * @param validCommandType the valid command of type {@code ValidCommand}
     */
    public HelpCommand(ValidCommand validCommandType) {
        isShowHelpWindow = false;
        this.validCommandType = validCommandType;
    }

    @Override
    public CommandResult execute(Model model) {
        if (isShowHelpWindow) {
            return new CommandResult(SHOWING_HELP_MESSAGE, true, false)
                    .setSameTab();
        }
        assert validCommandType != null : "The valid command type is null. Please check.";

        switch (validCommandType) {
        case ADD_ALIAS:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + AddAliasCommand.MESSAGE_USAGE
            ).setSameTab();
        case ADD_CONTACT:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + AddContactCommand.MESSAGE_USAGE
            ).setSameTab();
        case ADD_JOURNAL_ENTRY:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + AddJournalEntryCommand.MESSAGE_USAGE
            ).setSameTab();
        case CHECK_SCHEDULE:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + CheckScheduleCommand.MESSAGE_USAGE
            ).setSameTab();
        case CLEAR_ADDRESS_BOOK:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + ClearAddressBookCommand.MESSAGE_USAGE
            ).setSameTab();
        case CLEAR_JOURNAL:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + ClearJournalCommand.MESSAGE_USAGE
            ).setSameTab();
        case DELETE_CONTACT:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + DeleteContactCommand.MESSAGE_USAGE
            ).setSameTab();
        case DELETE_JOURNAL_ENTRY:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE
                            + DeleteJournalEntryCommand.MESSAGE_USAGE
            ).setSameTab();
        case EDIT_CONTACT:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + EditContactCommand.MESSAGE_USAGE
            ).setSameTab();
        case EXIT:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + ExitCommand.MESSAGE_USAGE
            ).setSameTab();
        case EDIT_JOURNAL_ENTRY:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + EditJournalEntryCommand.MESSAGE_USAGE
            ).setSameTab();
        case FIND:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + FindCommand.MESSAGE_USAGE
            ).setSameTab();
        case HELP:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + HelpCommand.MESSAGE_USAGE
            ).setSameTab();
        case LIST_CONTACT:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + ListContactCommand.MESSAGE_USAGE
            ).setSameTab();
        case LIST_JOURNAL_ENTRY:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + ListJournalEntryCommand.MESSAGE_USAGE
            ).setSameTab();
        case SWITCH:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + SwitchCommand.MESSAGE_USAGE
            ).setSameTab();
        case VIEW:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + ViewCommand.MESSAGE_USAGE
            ).setSameTab();
        case CHANGE_THEME:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + ChangeThemeCommand.MESSAGE_USAGE
            ).setSameTab();
        default:
            return new CommandResult(
                    SHOWING_HELP_MESSAGE + "This is a direct command to use."
            ).setSameTab();
        }
    }
}
