package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.AliasExistsException;
import seedu.address.logic.parser.exceptions.AliasNotFoundException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAliasMap;


/**
 * ValidCommand enum represents the possible command type.
 */
public enum ValidCommand {

    ADD_CONTACT("addc", "addcontact"),
    ADD_JOURNAL_ENTRY("addj", "adde", "addjournal"),
    CLEAR_JOURNAL("clearj", "cj"),
    CLEAR_ADDRESS_BOOK("clearc", "cc"),
    DELETE_CONTACT("deletec", "delc"),
    DELETE_JOURNAL_ENTRY("deletej", "delj"),
    EDIT_CONTACT("editc", "edc"),
    EDIT_JOURNAL_ENTRY("editj", "edj"),
    FIND("find", "f"),
    EXIT("exit", "quit", "q"),
    HELP("help", "h"),
    LIST_CONTACT("listc", "lc"),
    LIST_JOURNAL_ENTRY("listj", "lj"),
    SWITCH("switch", "swt"),
    VIEW("view", "v"),
    CHECK_SCHEDULE("check", "ck"),
    ADD_ALIAS("alias", "al"),
    CHANGE_THEME("changetheme", "ctheme"),
    DELETE_ALIAS("deletea", "dela");

    private static final Logger logger = LogsCenter.getLogger(ValidCommand.class);

    /**
     * Map that match alias with valid command
     */
    private static final Map<String, ValidCommand> aliasMap = new HashMap<>();

    static {
        Arrays.stream(ValidCommand.values()).forEach(command -> Arrays.stream(command.aliases)
                .forEach(alias -> {
                    assert aliasMap.get(alias) == null;
                    aliasMap.put(alias, command);
                }));
    }

    /**
     * Valid alias for the commands
     */
    private final String[] aliases;


    /**
     * Creates command alias from aliases list.
     *
     * @param aliases Aliases list.
     */
    ValidCommand(String... aliases) {
        this.aliases = aliases;
    }

    /**
     * Update the alias with given readOnlyAliasMap
     */
    public static void update(ReadOnlyAliasMap readOnlyAliasMap) {
        Map<String, ValidCommand> aliasMap = readOnlyAliasMap.getAliasMap();

        for (String alias: aliasMap.keySet()) {
            ValidCommand.aliasMap.put(alias, aliasMap.get(alias));
        }
    }

    /**
     * Give the command type of a valid alias.
     */
    public static ValidCommand commandTypeOf(String alias) throws ParseException {
        ValidCommand command = aliasMap.get(alias);

        if (command == null) {
            throw new ParseException("Unknown command");
        }

        logger.info(alias + " is a valid alias.");

        return command;
    }

    /**
     * Remove an alias from map.
     */
    public static void deleteAlias(String alias) throws AliasNotFoundException {
        requireNonNull(alias);

        if (!aliasMap.containsKey(alias)) {
            throw new AliasNotFoundException();
        }

        aliasMap.remove(alias);
    }

    /**
     * Add a new {@code alias} to a valid command.
     */
    public static void addAlias(ValidCommand command, String alias) throws AliasExistsException {
        assert command != null;
        assert alias != null && !alias.equals("");

        if (aliasMap.containsKey(alias)) {
            throw new AliasExistsException();
        }

        aliasMap.put(alias, command);

        logger.info("Map the alias " + alias + " to " + command.toString());
    }

    public static Map<String, ValidCommand> getAliasMap() {
        return aliasMap;
    }
}

