package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ValidCommand;
import seedu.address.logic.parser.exceptions.AliasExistsException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

public class AddAliasCommand extends Command {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a shortcut for an existing alias.\n"
            + "Parameters: COMMAND CUSTOMISED_ALIAS\n"
            + "Example: alias sw switch";

    private static final String MESSAGE_ADD_ALIAS_SUCCEED =
            "Added %s as an alias for %s.";
    private static final String MESSAGE_COMMAND_INVALID =
            "Your target command is invalid.";
    private static final String MESSAGE_ALIAS_EXISTS =
            "This alias has been used.";

    private final String targetCommand;
    private final String alias;

    /**
     * Creates a addAlias command.
     */
    public AddAliasCommand(String targetCommand, String alias) {
        requireAllNonNull(targetCommand, alias);
        this.targetCommand = targetCommand;
        this.alias = alias;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            ValidCommand.addAlias(ValidCommand.commandTypeOf(targetCommand), alias);
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_COMMAND_INVALID);
        } catch (AliasExistsException e) {
            throw new CommandException(MESSAGE_ALIAS_EXISTS);
        }

        model.updateAlias(ValidCommand.getAliasMap());

        return new CommandResult(String.format(MESSAGE_ADD_ALIAS_SUCCEED, alias, targetCommand))
                .setSameTab();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof AddAliasCommand
                && targetCommand.equals(((AddAliasCommand) other).targetCommand)
                && alias.equals(((AddAliasCommand) other).alias));
    }
}
