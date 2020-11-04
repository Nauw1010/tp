package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ValidCommand;
import seedu.address.logic.parser.exceptions.AliasNotFoundException;
import seedu.address.model.Model;

public class DeleteAliasCommand extends Command {

    public static final String COMMAND_WORD = "deletea";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": delete a shortcut for an existing alias.\n"
            + "Parameters: CUSTOMISED_ALIAS\n"
            + "Example: deletea sw ";

    private static final String MESSAGE_ALIAS_NOTFOUND =
            "This alias is not in the system.";
    private static final String MESSAGE_ADD_ALIAS_SUCCEED =
            "Delete alias %s.";

    private final String target;

    /**
     * Creates a command to delete alias.
     *
     * @param target Target alias to delete.
     */
    public DeleteAliasCommand(String target) {
        requireNonNull(target);

        this.target = target;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            ValidCommand.deleteAlias(target);
        } catch (AliasNotFoundException e) {
            throw new CommandException(MESSAGE_ALIAS_NOTFOUND);
        }

        model.updateAlias(ValidCommand.getAliasMap());

        return new CommandResult(String.format(MESSAGE_ADD_ALIAS_SUCCEED, target))
                .setSameTab();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof DeleteAliasCommand
                && target.equals(((DeleteAliasCommand) other).target));

    }
}
