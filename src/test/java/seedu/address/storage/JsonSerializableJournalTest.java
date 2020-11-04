package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalEntries.getTypicalJournal;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Journal;

class JsonSerializableJournalTest {

    private static final Path TEST_DATA_FOLDER = Paths.get(
            "src",
            "test",
            "data",
            "JsonSerializableJournalTest"
    );
    private static final Path TYPICAL_ENTRIES_FILE =
            TEST_DATA_FOLDER.resolve("typicalEntriesJournal.json");
    private static final Path INVALID_ENTRY_FILE =
            TEST_DATA_FOLDER.resolve("invalidEntryJournal.json");
    private static final Path DUPLICATE_ENTRY_FILE =
            TEST_DATA_FOLDER.resolve("duplicateEntryJournal.json");

    @Nested
    @DisplayName("toModelType method")
    class ToModelType {
        @Test
        @DisplayName("should successfully return a Journal object from a "
                + "json file with valid entries")
        public void toModelType_typicalEntriesFile_success() throws Exception {
            JsonSerializableJournal dataFromFile = JsonUtil.readJsonFile(
                    TYPICAL_ENTRIES_FILE,
                    JsonSerializableJournal.class
            ).get();
            Journal journalFromFile = dataFromFile
                    .toModelType(getTypicalAddressBook());
            Journal typicalEntriesJournal = getTypicalJournal();

            assertEquals(typicalEntriesJournal, journalFromFile);
        }

        @Test
        @DisplayName("should throw IllegalValueException if there is invalid "
                + "entry in the file")
        public void toModelType_invalidEntryFile_throwsIllegalValueException()
                throws Exception {
            JsonSerializableJournal dataFromFile = JsonUtil.readJsonFile(
                    INVALID_ENTRY_FILE,
                    JsonSerializableJournal.class
            ).get();
            assertThrows(IllegalValueException.class, () ->
                    dataFromFile.toModelType(getTypicalAddressBook()));
        }

        @Test
        @DisplayName("should throw IllegalValueException if there is duplicate "
                + "entries in the file")
        public void toModelType_duplicateEntries_throwsIllegalValueException()
                throws Exception {
            JsonSerializableJournal dataFromFile = JsonUtil.readJsonFile(
                    DUPLICATE_ENTRY_FILE,
                    JsonSerializableJournal.class
            ).get();
            assertThrows(IllegalValueException.class, () ->
                    dataFromFile.toModelType(getTypicalAddressBook()));
        }
    }
}
