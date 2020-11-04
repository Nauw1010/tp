package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OCTOBER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_STORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEntries.getTypicalJournal;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AliasMap;
import seedu.address.model.Journal;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.journal.Entry;
import seedu.address.model.person.Person;
import seedu.address.testutil.EntryBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code AddJournalEntryCommand}.
 */
public class AddJournalEntryCommandIntegrationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                getTypicalAddressBook(), new Journal(), new UserPrefs(), new AliasMap());
        model.addEntry(
                new EntryBuilder()
                        .withTitle(VALID_TITLE_MEETING)
                        .withDate(VALID_DATE_OCTOBER)
                        .withDescription(VALID_DESCRIPTION_STORY)
                        .build()
        );
    }

    @Nested
    @DisplayName("execute")
    class Execute {
        @Test
        @DisplayName("should successfully add new entry")
        public void execute_newEntry_success() {
            Entry validEntry = new EntryBuilder().build();

            Model expectedModel = new ModelManager(
                    model.getAddressBook(), model.getJournal(), new UserPrefs(), new AliasMap());
            expectedModel.addEntry(validEntry);

            assertCommandSuccess(
                    new AddJournalEntryCommand(validEntry),
                    model,
                    String.format(AddJournalEntryCommand.MESSAGE_SUCCESS, validEntry),
                    expectedModel
            );
        }

        @Test
        @DisplayName("should throw CommandException if contact specified is "
                + "not in model")
        public void execute_contactNotInModel_throwsCommandException() {
            // default PersonBuilder is Alice Pauline
            Person toAdd = new PersonBuilder()
                    .withName(VALID_NAME_AMY)
                    .build();
            Entry validEntry = new EntryBuilder()
                    .withContacts(toAdd)
                    .build();
            Model model = new ModelManager(
                    getTypicalAddressBook(),
                    getTypicalJournal(),
                    new UserPrefs(),
                    new AliasMap()
            );
            AddJournalEntryCommand addCommand = new AddJournalEntryCommand(validEntry);
            String expectedMessage =
                    String.format(AddJournalEntryCommand.MESSAGE_PERSON_NOT_FOUND,
                            toAdd.getName());

            assertThrows(CommandException.class,
                    expectedMessage, () ->
                            addCommand.execute(model));

        }

        @Test
        @DisplayName("should add entry with contacts successfully if contacts"
                + " is in model")
        public void execute_entryWithContactsAcceptedByModel_addSuccessful()
                throws CommandException {
            Model model = new ModelManager(
                    getTypicalAddressBook(),
                    new Journal(),
                    new UserPrefs(),
                    new AliasMap()
            );
            // ALICE is in typicalAddressBook
            Person toAdd = ALICE;
            Entry validEntry = new EntryBuilder()
                    .withContacts(toAdd)
                    .build();
            AddJournalEntryCommand addCommand =
                    new AddJournalEntryCommand(validEntry);
            CommandResult commandResult =
                    addCommand.execute(model);
            assertEquals(
                    String.format(AddJournalEntryCommand.MESSAGE_SUCCESS,
                            validEntry),
                    commandResult.getFeedbackToUser()
            );
            assertEquals(Arrays.asList(validEntry), model.getJournal().getEntryList());
        }

        @Test
        @DisplayName("should throw CommandException if a duplicate entry is "
                + "added")
        public void execute_duplicateEntry_throwsCommandException() {
            Entry entryInList = model.getJournal().getEntryList().get(0);
            assertCommandFailure(
                    new AddJournalEntryCommand(entryInList),
                    model,
                    AddJournalEntryCommand.MESSAGE_DUPLICATE_ENTRY
            );
        }
    }
}
