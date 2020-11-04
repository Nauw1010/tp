package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.journal.Entry;
import seedu.address.model.person.Person;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private boolean isAddressBookTab = true;
    private boolean isSameTab = false;
    private boolean isSwitchingTab = false;
    private boolean isViewingJournal = false;
    private boolean isCleaningJournalView = false;
    private boolean isChangingTheme = false;
    private boolean isViewingPerson = false;
    private Person personToView = null;
    private Entry entryToView = null;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(
            String feedbackToUser,
            boolean showHelp,
            boolean exit
    ) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified
     * {@code feedbackToUser}, and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    public boolean isAddressBookTab() {
        return isAddressBookTab;
    }

    public CommandResult setAddressBookTab() {
        this.isAddressBookTab = true;
        return this;
    }

    public CommandResult setJournalTab() {
        this.isAddressBookTab = false;
        return this;
    }

    public CommandResult setSwitch() {
        this.isSwitchingTab = true;
        return this;
    }

    public CommandResult setSameTab() {
        this.isSameTab = true;
        return this;
    }

    public CommandResult setViewingJournal(Entry entryToView) {
        this.isViewingJournal = true;
        this.entryToView = entryToView;
        return this;
    }

    public CommandResult setViewingPerson(Person personToView) {
        this.isViewingPerson = true;
        this.personToView = personToView;
        return this;
    }

    public CommandResult setCleaningJournalView(boolean isCleaningJournalView) {
        this.isCleaningJournalView = isCleaningJournalView;
        return this;
    }

    public CommandResult setChangingTheme() {
        this.isChangingTheme = true;
        return this;
    }

    public boolean isSwitch() {
        return isSwitchingTab;
    }

    public boolean isSameTab() {
        return isSameTab;
    }

    public boolean isViewingJournal() {
        return isViewingJournal;
    }

    public boolean isViewingPerson() {
        return isViewingPerson;
    }

    public Person getPersonToView() {
        assert (isViewingPerson);
        return personToView;
    }

    public Entry getEntryToView() {
        assert (isViewingJournal);
        return entryToView;
    }

    public boolean isCleaningJournalView() {
        return isCleaningJournalView;
    }

    public boolean isChangingTheme() {
        return isChangingTheme;
    }
}
