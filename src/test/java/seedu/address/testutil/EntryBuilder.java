package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.journal.Date;
import seedu.address.model.journal.Description;
import seedu.address.model.journal.Entry;
import seedu.address.model.journal.Title;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

public class EntryBuilder {

    public static final String DEFAULT_TITLE = "Team Meeting";
    public static final String DEFAULT_DESCRIPTION = "Team Meeting for CS2103T";
    public static final String DEFAULT_DATE = "2020-12-20 18:00";


    private Title title;
    private Date date;
    private Description description;
    private Set<Tag> tags;

    // Represents a contact list for a certain event
    private UniquePersonList contactList;

    /**
     * Initialize the Entry with default value.
     */
    public EntryBuilder() {
        title = new Title(DEFAULT_TITLE);
        date = new Date(DEFAULT_DATE);
        description = new Description(DEFAULT_DESCRIPTION);
        contactList = new UniquePersonList();
        tags = new HashSet<>();
    }

    /**
     * Initializes the Entry with a given entry.
     */
    public EntryBuilder(Entry entryToCopy) {
        title = entryToCopy.getTitle();
        date = entryToCopy.getDate();
        description = entryToCopy.getDescription();
        contactList = new UniquePersonList();
        entryToCopy.getContactList()
                .forEach(contactList::add);
        tags = new HashSet<>();
        tags.addAll(entryToCopy.getTags());
    }

    /**
     * Builds an entry with a title.
     *
     * @param title Title of the entry.
     * @return Entry with a new title.
     */
    public EntryBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Builds an entry with description.
     *
     * @param description Description of the entry.
     * @return Entry with a new description.
     */
    public EntryBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Builds an entry with a date.
     *
     * @param date Date of the entry.
     * @return Entry with a new date.
     */
    public EntryBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the
     * {@code Person} that we are building.
     */
    public EntryBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Adds person to the contact list.
     */
    public EntryBuilder withContacts(Person... persons) {
        contactList = new UniquePersonList();
        Arrays.stream(persons).forEach(contactList::add);
        return this;
    }

    public Entry build() {
        return new Entry(title, date, description, contactList, tags);
    }
}
