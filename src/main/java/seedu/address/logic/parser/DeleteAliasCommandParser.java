package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteAliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteAliasCommandParser implements Parser<DeleteAliasCommand> {

    @Override
    public DeleteAliasCommand parse(String userInput) throws ParseException {
        String[] target = userInput.trim().split("\\s+");

        if (target.length != 1 || target[0].length() == 0) {
            throw new ParseException(
                    String.format(
                            MESSAGE_INVALID_COMMAND_FORMAT,
                            DeleteAliasCommand.MESSAGE_USAGE
                    )
            );
        }

        String toDelete = target[0];

        return new DeleteAliasCommand(toDelete);
    }
}
