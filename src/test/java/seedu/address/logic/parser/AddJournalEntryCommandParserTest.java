package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_OCTOBER;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_STORY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OCTOBER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_STORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_MEETING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddJournalEntryCommand;
import seedu.address.model.journal.Date;
import seedu.address.model.journal.Description;
import seedu.address.model.journal.Entry;
import seedu.address.model.journal.Title;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EntryBuilder;

public class AddJournalEntryCommandParserTest {
    private final AddJournalEntryCommandParser parser = new AddJournalEntryCommandParser();

    @Nested
    @DisplayName("parse method")
    class Parse {
        @Test
        @DisplayName("should add entry successfully if all required fields "
                + "are present")
        public void parse_allFieldsPresent_success() {
            Entry expectedEntry = new EntryBuilder()
                    .withTitle(VALID_TITLE_MEETING)
                    .withDate(VALID_DATE_OCTOBER)
                    .withDescription(VALID_DESCRIPTION_STORY)
                    .build();

            assertParseSuccess(
                    parser,
                    PREAMBLE_WHITESPACE + TITLE_DESC_MEETING
                            + DATE_DESC_OCTOBER + DESCRIPTION_DESC_STORY,
                    new AddJournalEntryCommand(expectedEntry)
            );
        }

        @Test
        @DisplayName("should fail to add entry if compulsory fields are "
                + "missing")
        public void parse_compulsoryFieldMissing_failure() {
            String expectedMessage = String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    AddJournalEntryCommand.MESSAGE_USAGE
            );

            // missing title prefix
            assertParseFailure(parser,
                    VALID_TITLE_MEETING + DATE_DESC_OCTOBER + DESCRIPTION_DESC_STORY,
                    expectedMessage
            );

            // all prefixes missing
            assertParseFailure(parser,
                    VALID_TITLE_MEETING + VALID_DATE_OCTOBER + VALID_DESCRIPTION_STORY,
                    expectedMessage
            );
        }

        @Test
        @DisplayName("should not add entry if there are invalid values in the"
                + " input")
        public void parse_invalidValue_failure() {
            // invalid title
            assertParseFailure(
                    parser,
                    INVALID_TITLE_DESC + VALID_DATE_OCTOBER + VALID_DESCRIPTION_MOVIE,
                    Title.MESSAGE_CONSTRAINTS
            );

            // invalid date
            assertParseFailure(
                    parser,
                    TITLE_DESC_MEETING + INVALID_DATE_DESC + DESCRIPTION_DESC_STORY,
                    Date.MESSAGE_CONSTRAINTS
            );

            // invalid description
            assertParseFailure(
                    parser,
                    TITLE_DESC_MEETING + VALID_DATE_OCTOBER + INVALID_DESCRIPTION_DESC,
                    Description.MESSAGE_CONSTRAINTS
            );

            // invalid tag
            assertParseFailure(parser,
                    TITLE_DESC_MEETING
                            + VALID_DATE_OCTOBER
                            + DESCRIPTION_DESC_STORY
                            + INVALID_TAG_DESC,
                    Tag.MESSAGE_CONSTRAINTS);

            // non-empty preamble
            assertParseFailure(parser,
                    PREAMBLE_NON_EMPTY + TITLE_DESC_MEETING + DATE_DESC_OCTOBER
                            + DESCRIPTION_DESC_STORY,
                    String.format(
                            MESSAGE_INVALID_COMMAND_FORMAT,
                            AddJournalEntryCommand.MESSAGE_USAGE
                    )
            );
        }
    }
}
