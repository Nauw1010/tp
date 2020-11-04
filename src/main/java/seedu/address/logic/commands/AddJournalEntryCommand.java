package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_AND_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.journal.Entry;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

//@@author {zhXchD}
/**
 * Adds a person to the address book.
 */
public class AddJournalEntryCommand extends Command {

    public static final String COMMAND_WORD = "addj";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a journal entry to the journal.\n"
            + "Parameters: "
            + PREFIX_NAME + "TITLE "
            + "[" + PREFIX_DATE_AND_TIME + "DATE_AND_TIME] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_CONTACT + "CONTACT_NAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Meet with client John Doe "
            + PREFIX_DATE_AND_TIME + "2020-10-10 10:00 "
            + PREFIX_DESCRIPTION + "Discussed about his demands "
            + PREFIX_CONTACT + "John Doe "
            + PREFIX_TAG + "Meeting";

    public static final String MESSAGE_SUCCESS =
            "New journal entry added: %1$s";
    public static final String MESSAGE_DUPLICATE_ENTRY =
            "This entry already exists in the journal";
    public static final String MESSAGE_PERSON_NOT_FOUND =
            "Person named %s does not exist in the address book!";

    private final Entry toAdd;

    /**
     * Creates an AddJournalEntryCommand to add the specified {@code Entry}
     */
    public AddJournalEntryCommand(Entry entry) {
        requireNonNull(entry);
        toAdd = entry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UniquePersonList uniquePersonList = new UniquePersonList();
        for (Person person : toAdd.getContactList()) {
            Optional<Person> personInList = model.getAddressBook()
                    .getPersonList()
                    .stream()
                    .filter(p -> p.getName().equals(person.getName()))
                    .findFirst();
            if (personInList.isEmpty()) {
                throw new CommandException(
                        "Person named " + person.getName()
                                + " does not exist in the address book!");
            } else {
                uniquePersonList.add(personInList.get());
            }
        }

        Entry validToAdd = new Entry(
                toAdd.getTitle(),
                toAdd.getDate(),
                toAdd.getDescription(),
                uniquePersonList,
                toAdd.getTags()
        );

        if (model.hasEntry(validToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.addEntry(validToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, validToAdd))
                .setJournalTab().setViewingJournal(validToAdd);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddJournalEntryCommand // instanceof handles nulls
                && toAdd.equals(((AddJournalEntryCommand) other).toAdd));
    }
}
