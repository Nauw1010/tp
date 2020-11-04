package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_AND_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.journal.Date;
import seedu.address.model.journal.Description;
import seedu.address.model.journal.Entry;
import seedu.address.model.journal.Title;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

public class EditJournalEntryCommand extends Command {

    public static final String COMMAND_WORD = "editj";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the entry at the index position in the "
            + "currently displayed entry list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "TITLE] "
            + "[" + PREFIX_DATE_AND_TIME + "DATE_AND_TIME] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_CONTACT + "CONTACT_NAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Meet with client "
            + PREFIX_DATE_AND_TIME + "2020-10-10 10:00 "
            + PREFIX_CONTACT + "Robert";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Journal "
            + "Entry: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to "
            + "edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This person already"
            + " exists in the address book.";
    public static final String MESSAGE_CONTACT_NOT_IN_ADDRESSBOOK = "Person "
            + "named %s does not exist in the address book!";

    private final Index index;
    private final EditEntryDescriptor editEntryDescriptor;

    /**
     * @param index the index of journal entry to edit
     */
    public EditJournalEntryCommand(Index index,
                                   EditEntryDescriptor editEntryDescriptor) {
        requireNonNull(index);
        requireNonNull(editEntryDescriptor);

        this.index = index;
        this.editEntryDescriptor = editEntryDescriptor;
    }

    private static Entry createEditedEntry(Entry entryToEdit,
                                           EditEntryDescriptor editEntryDescriptor,
                                           Model model) throws CommandException {
        assert entryToEdit != null;

        Title updatedTitle =
                editEntryDescriptor.getTitle().orElse(entryToEdit.getTitle());
        Date updatedDate =
                editEntryDescriptor.getDate().orElse(entryToEdit.getDate());
        Description updatedDescription =
                editEntryDescriptor.getDescription().orElse(entryToEdit.getDescription());
        ObservableList<Person> updatedPersonList =
                editEntryDescriptor.getContactList().orElse(entryToEdit.getContactList());

        UniquePersonList updatedContactList = new UniquePersonList();
        for (Person person : updatedPersonList) {
            Optional<Person> personInList = model.getAddressBook()
                    .getPersonList()
                    .stream()
                    .filter(p -> p.getName().equals(person.getName()))
                    .findFirst();
            if (personInList.isEmpty()) {
                throw new CommandException(
                        String.format(MESSAGE_CONTACT_NOT_IN_ADDRESSBOOK,
                                person.getName()));
            } else {
                updatedContactList.add(personInList.get());
            }
        }

        Set<Tag> updatedTags =
                editEntryDescriptor.getTags().orElse(entryToEdit.getTags());

        return new Entry(
                updatedTitle,
                updatedDate,
                updatedDescription,
                updatedContactList,
                updatedTags
        );
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Entry> lastShownList = model.getFilteredEntryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }
        Entry entryToEdit = lastShownList.get(index.getZeroBased());
        Entry editedEntry = createEditedEntry(entryToEdit,
                editEntryDescriptor, model);
        if (!entryToEdit.isSameEntry(editedEntry) && model.hasEntry(editedEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }
        model.setEntry(entryToEdit, editedEntry);
        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);

        return new CommandResult(
                String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry))
                .setJournalTab()
                .setViewingJournal(editedEntry);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditJournalEntryCommand)) {
            return false;
        }

        EditJournalEntryCommand e = (EditJournalEntryCommand) other;
        return index.equals(e.index)
                && editEntryDescriptor.equals(e.editEntryDescriptor);
    }

    public static class EditEntryDescriptor {
        private Title title;
        private Date date;
        private Description description;
        private UniquePersonList contactList;
        private Set<Tag> tags;

        public EditEntryDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} and {@code contactList} is used
         * internally.
         */
        public EditEntryDescriptor(EditEntryDescriptor toCopy) {
            setTitle(toCopy.title);
            setDate(toCopy.date);
            setDescription(toCopy.description);
            setContactList(toCopy.contactList.asUnmodifiableObservableList());
            setTags(toCopy.tags);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(
                    title,
                    date,
                    description,
                    tags)
                    || contactList.asUnmodifiableObservableList().size() > 0;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<ObservableList<Person>> getContactList() {
            return contactList != null
                    && contactList.asUnmodifiableObservableList().size() > 0
                    ? Optional.ofNullable(contactList.asUnmodifiableObservableList())
                    : Optional.empty();
        }

        /**
         * Sets {@code contactList} to this object's {@code contactList}.
         */
        public void setContactList(ObservableList<Person> contactList) {
            // Only sets if there are more than 0 contacts
            this.contactList = new UniquePersonList();
            contactList.forEach(this.contactList::add);
        }

        /**
         * Returns an unmodifiable tag set, which throws
         * {@code UnsupportedOperationException} if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return tags != null
                    ? Optional.of(Collections.unmodifiableSet(tags))
                    : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEntryDescriptor)) {
                return false;
            }

            // state check
            EditEntryDescriptor e = (EditEntryDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getContactList().equals(e.getContactList())
                    && getDate().equals(e.getDate())
                    && getDescription().equals(e.getDescription())
                    && getTags().equals(e.getTags());

        }
    }
}
