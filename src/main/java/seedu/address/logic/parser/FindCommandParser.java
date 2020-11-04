package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_AND_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCOPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindContactCommand;
import seedu.address.logic.commands.FindJournalEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.journal.Date;
import seedu.address.model.journal.Description;
import seedu.address.model.journal.Entry;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

//@@author {zhXchD}
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_SCOPE,
                PREFIX_NAME,
                PREFIX_ADDRESS,
                PREFIX_PHONE,
                PREFIX_EMAIL,
                PREFIX_TAG,
                PREFIX_DATE_AND_TIME,
                PREFIX_DESCRIPTION,
                PREFIX_CONTACT
        );

        if (!arePrefixesPresent(argMultimap, PREFIX_SCOPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FindCommand.MESSAGE_USAGE)
            );
        }

        assert argMultimap.getValue(PREFIX_SCOPE).isPresent();
        String scope = ParserUtil.parseScope(
                argMultimap.getValue(PREFIX_SCOPE).get());

        switch (scope) {
        case "c":
            Predicate<Person> personPredicate = person -> true;
            if (!arePrefixesEmpty(argMultimap, PREFIX_DATE_AND_TIME, PREFIX_DESCRIPTION, PREFIX_CONTACT)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContactCommand.MESSAGE_USAGE));
            }
            if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
                assert argMultimap.getValue(PREFIX_NAME).isPresent();
                String nameKeyWord =
                        argMultimap.getValue(PREFIX_NAME).get().trim().toLowerCase();
                personPredicate =
                        personPredicate.and(person -> person.getName().fullName
                                .toLowerCase()
                                .contains(nameKeyWord));
            }
            if (arePrefixesPresent(argMultimap, PREFIX_EMAIL)) {
                assert argMultimap.getValue(PREFIX_EMAIL).isPresent();
                String emailKeyWord =
                        argMultimap.getValue(PREFIX_EMAIL).get().trim().toLowerCase();
                personPredicate =
                        personPredicate.and(person -> {
                            if (person.getEmail().equals(Email.EMPTY_EMAIL)) {
                                return false;
                            } else {
                                return person.getEmail().value
                                        .toLowerCase()
                                        .contains(emailKeyWord);
                            }
                        });
            }
            if (arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
                assert argMultimap.getValue(PREFIX_ADDRESS).isPresent();
                String addressKeyWord =
                        argMultimap.getValue(PREFIX_ADDRESS).get().trim().toLowerCase();
                personPredicate =
                        personPredicate.and(person -> {
                            if (person.getAddress().equals(Address.EMPTY_ADDRESS)) {
                                return false;
                            } else {
                                return person.getAddress().value
                                        .toLowerCase()
                                        .contains(addressKeyWord);
                            }
                        });
            }
            if (arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
                assert argMultimap.getValue(PREFIX_PHONE).isPresent();
                String phoneKeyWord =
                        argMultimap.getValue(PREFIX_PHONE).get().trim().toLowerCase();
                personPredicate =
                        personPredicate.and(person -> {
                            if (person.getPhone().equals(Phone.EMPTY_PHONE)) {
                                return false;
                            } else {
                                return person.getPhone().value
                                        .toLowerCase()
                                        .contains(phoneKeyWord);
                            }
                        });
            }
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            personPredicate = personPredicate.and(person -> person.getTags().containsAll(tagList));
            return new FindContactCommand(personPredicate);
        case "j":
            Predicate<Entry> entryPredicate = entry -> true;
            if (!arePrefixesEmpty(argMultimap, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindJournalEntryCommand.MESSAGE_USAGE));
            }
            if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
                assert argMultimap.getValue(PREFIX_NAME).isPresent();
                String titleKeyWord =
                        argMultimap.getValue(PREFIX_NAME).get().trim().toLowerCase();
                entryPredicate =
                        entryPredicate.and(entry -> entry.getTitle().title
                                .toLowerCase()
                                .contains(titleKeyWord));
            }
            if (arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)) {
                assert argMultimap.getValue(PREFIX_DESCRIPTION).isPresent();
                String descriptionKeyWord =
                        argMultimap.getValue(PREFIX_DESCRIPTION).get().trim().toLowerCase();
                entryPredicate =
                        entryPredicate.and(entry -> {
                            if (entry.getDescription().equals(Description.EMPTY_DESCRIPTION)) {
                                return false;
                            } else {
                                return entry.getDescription().description
                                        .toLowerCase()
                                        .contains(descriptionKeyWord);
                            }
                        });
            }
            if (arePrefixesPresent(argMultimap, PREFIX_DATE_AND_TIME)) {
                assert argMultimap.getValue(PREFIX_DATE_AND_TIME).isPresent();
                Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_AND_TIME).orElse(null));
                entryPredicate = entryPredicate.and(entry -> entry.getDate().equals(date));
            }
            List<String> names = argMultimap.getAllValues(PREFIX_CONTACT);
            entryPredicate = entryPredicate.and(
                entry -> names.stream().allMatch(
                    name -> entry
                        .getContactList().stream()
                            .anyMatch(
                                person -> person.getName().fullName
                                        .toLowerCase()
                                        .contains(name.toLowerCase()))));
            Set<Tag> tagList1 = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            entryPredicate = entryPredicate.and(entry -> entry.getTags().containsAll(tagList1));
            return new FindJournalEntryCommand(entryPredicate);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if none of the prefixes contains present {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesEmpty(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }

}
