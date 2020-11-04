package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearAddressBookCommand;
import seedu.address.logic.commands.ClearJournalCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListContactCommand;
import seedu.address.logic.commands.ListJournalEntryCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class IntelliJournalParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private UUID uuid;

    /**
     * Creates a default instance of IntelliJournalParser.
     */
    public IntelliJournalParser() { }

    /**
     * Creates an instance of IntelliJournalParser with a given uuid.
     * @param uuid used in parsing.
     */
    public IntelliJournalParser(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");


        ValidCommand command = ValidCommand.commandTypeOf(commandWord);

        switch (command) {
        case ADD_CONTACT:
            if (uuid == null) {
                return new AddContactCommandParser().parse(arguments);
            } else {
                return new AddContactCommandParser(uuid).parse(arguments);
            }

        case ADD_JOURNAL_ENTRY:
            return new AddJournalEntryCommandParser().parse(arguments);

        case EDIT_CONTACT:
            return new EditContactCommandParser().parse(arguments);

        case EDIT_JOURNAL_ENTRY:
            return new EditJournalEntryCommandParser().parse(arguments);

        case DELETE_CONTACT:
            return new DeleteContactCommandParser().parse(arguments);

        case DELETE_JOURNAL_ENTRY:
            return new DeleteJournalEntryCommandParser().parse(arguments);

        case CLEAR_ADDRESS_BOOK:
            return new ClearAddressBookCommand();

        case CLEAR_JOURNAL:
            return new ClearJournalCommand();

        case FIND:
            return new FindCommandParser().parse(arguments);

        case LIST_CONTACT:
            return new ListContactCommand();

        case LIST_JOURNAL_ENTRY:
            return new ListJournalEntryCommand();

        case EXIT:
            return new ExitCommand();

        case HELP:
            return new HelpCommandParser().parse(arguments);

        case VIEW:
            return new ViewCommandParser().parse(arguments);

        case SWITCH:
            return new SwitchCommand();

        case CHECK_SCHEDULE:
            return new CheckScheduleCommandParser().parse(arguments);

        case ADD_ALIAS:
            return new AddAliasCommandParser().parse(arguments);

        case CHANGE_THEME:
            return new ChangeThemeCommand();

        case DELETE_ALIAS:
            return new DeleteAliasCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
