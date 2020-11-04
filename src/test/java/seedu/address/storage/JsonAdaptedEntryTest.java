package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.storage.JsonAdaptedEntry.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEntries.TEST_ENTRY_DIFF_CONTACTS;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.journal.Date;
import seedu.address.model.journal.Description;
import seedu.address.model.journal.Title;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalPersons;

class JsonAdaptedEntryTest {
    private static final ReadOnlyAddressBook addressBook =
            TypicalPersons.getTypicalAddressBook();

    private static final String INVALID_TITLE = "Te@ with the Qu33n";
    private static final String INVALID_DATE = "1922-12-23 4123";
    private static final String INVALID_DESCRIPTION = "@-+/1234567890";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TITLE =
            TEST_ENTRY_DIFF_CONTACTS.getTitle().toString();
    private static final String VALID_DATE =
            TEST_ENTRY_DIFF_CONTACTS.getDate().toString();
    private static final String VALID_DESCRIPTION =
            TEST_ENTRY_DIFF_CONTACTS.getDescription().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS =
            TEST_ENTRY_DIFF_CONTACTS
                    .getTags()
                    .stream()
                    .map(JsonAdaptedTag::new)
                    .collect(Collectors.toList());
    private static final List<String> VALID_CONTACTS =
            TEST_ENTRY_DIFF_CONTACTS
                    .getContactList()
                    .stream()
                    .map(person -> person.getUuid().toString())
                    .collect(Collectors.toList());

    @Nested
    @DisplayName("toModelType method")
    class ToModelType {
        @Test
        @DisplayName("should return a valid entry if details are valid")
        public void toModelType_validEntry_returnsEntry()
                throws IllegalValueException {
            System.out.println(TEST_ENTRY_DIFF_CONTACTS);
            JsonAdaptedEntry entry =
                    new JsonAdaptedEntry(TEST_ENTRY_DIFF_CONTACTS);
            assertEquals(
                    TEST_ENTRY_DIFF_CONTACTS, entry.toModelType(addressBook));
        }

        @Test
        @DisplayName("should throw IllegalValueException if title is null")
        public void toModelType_nullTitle_throwIllegalValueException() {
            JsonAdaptedEntry entry = new JsonAdaptedEntry(
                    null,
                    VALID_DATE,
                    VALID_DESCRIPTION,
                    VALID_CONTACTS,
                    VALID_TAGS
            );
            String expectedMessage = String.format(
                    MISSING_FIELD_MESSAGE_FORMAT,
                    Title.class.getSimpleName()
            );
            assertThrows(
                    IllegalValueException.class, () ->
                            entry.toModelType(addressBook),
                    expectedMessage
            );
        }

        @Test
        @DisplayName("should throw IllegalValueException if date is null")
        public void toModelType_nullDate_throwIllegalValueException() {
            JsonAdaptedEntry entry = new JsonAdaptedEntry(
                    VALID_TITLE,
                    null,
                    VALID_DESCRIPTION,
                    VALID_CONTACTS,
                    VALID_TAGS
            );
            String expectedMessage = String.format(
                    MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()
            );
            assertThrows(
                    IllegalValueException.class, () ->
                            entry.toModelType(addressBook),
                    expectedMessage
            );
        }

        @Test
        @DisplayName("should throw IllegalValueException if date is in "
                + "invalid format")
        public void toModelType_invalidDate_throwIllegalValueException() {
            JsonAdaptedEntry entry = new JsonAdaptedEntry(
                    VALID_TITLE,
                    INVALID_DATE,
                    VALID_DESCRIPTION,
                    VALID_CONTACTS,
                    VALID_TAGS
            );
            String expectedMessage = Date.MESSAGE_CONSTRAINTS;
            assertThrows(
                    IllegalValueException.class, () ->
                            entry.toModelType(addressBook),
                    expectedMessage
            );
        }

        @Test
        @DisplayName("should throw IllegalValueException if description is "
                + "null")
        public void toModelType_nullDescription_throwIllegalValueException() {
            JsonAdaptedEntry entry = new JsonAdaptedEntry(
                    VALID_TITLE,
                    VALID_DATE,
                    null,
                    VALID_CONTACTS,
                    VALID_TAGS
            );
            String expectedMessage = String.format(
                    MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()
            );
            assertThrows(
                    IllegalValueException.class, () ->
                            entry.toModelType(addressBook),
                    expectedMessage
            );
        }

        @Test
        @DisplayName("should throw IllegalValueException if there are "
                + "invalid tags")
        public void toModelType_invalidTags_throwIllegalValueException() {
            List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
            invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
            JsonAdaptedEntry entry = new JsonAdaptedEntry(
                    VALID_TITLE,
                    VALID_DATE,
                    VALID_DESCRIPTION,
                    VALID_CONTACTS,
                    invalidTags
            );
            Assert.assertThrows(IllegalValueException.class, () ->
                    entry.toModelType(addressBook));
        }
    }
}
