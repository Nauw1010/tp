package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import seedu.address.model.AliasMap;
import seedu.address.model.Journal;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code AddContactCommand}.
 */
public class AddContactCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                getTypicalAddressBook(), new Journal(), new UserPrefs(), new AliasMap());
    }

    @Nested
    @DisplayName("execute")
    class Execute {
        @Test
        @DisplayName("should successfully add new person")
        public void execute_newPerson_success() {
            Person validPerson = new PersonBuilder()
                    .withName(VALID_NAME_AMY)
                    .build();

            Model expectedModel = new ModelManager(
                    model.getAddressBook(), new Journal(), new UserPrefs(), new AliasMap());
            expectedModel.addPerson(validPerson);

            assertCommandSuccess(
                    new AddContactCommand(validPerson),
                    model,
                    String.format(AddContactCommand.MESSAGE_SUCCESS, validPerson),
                    expectedModel
            );
        }

        @Test
        @DisplayName("should throw CommandException if a duplicate person is "
                + "added")
        public void execute_duplicatePerson_throwsCommandException() {
            Person personInList = model.getAddressBook().getPersonList().get(0);
            assertCommandFailure(
                    new AddContactCommand(personInList),
                    model,
                    AddContactCommand.MESSAGE_DUPLICATE_PERSON
            );
        }
    }
}
