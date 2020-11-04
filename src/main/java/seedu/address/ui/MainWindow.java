package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.journal.Entry;
import seedu.address.model.person.Person;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String THEME_DARK = "view/DarkTheme.css";
    private static final String THEME_BRIGHT = "view/ColorScheme_1.css";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ContactContent contactContent;
    private EntryListPanel entryListPanel;
    private EntryContent entryContent;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    private DashboardTab dashboardTab;


    @FXML
    private Scene primaryScene;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab addressBookTab;

    @FXML
    private Tab journalTab;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane contactContentPlaceholder;

    @FXML
    private StackPane entryListPanelPlaceholder;

    @FXML
    private StackPane dashboardTabPlaceHolder;

    @FXML
    private StackPane entryContentPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        entryListPanel = new EntryListPanel(logic.getFilteredEntryList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        entryListPanelPlaceholder.getChildren().add(entryListPanel.getRoot());

        contactContent = new ContactContent();
        contactContent.setContactContentToUser(personListPanel.getPersonListItems().get(0));
        contactContentPlaceholder.getChildren().add(contactContent.getRoot());
        entryContent = new EntryContent();
        entryContent.setEntryContentToUser(entryListPanel.getEntryListItems().get(0));
        entryContentPlaceholder.getChildren().add(entryContent.getRoot());

        dashboardTab = new DashboardTab(logic.getRecentPersonList(), logic.getFrequentPersonList());
        dashboardTabPlaceHolder.getChildren().add(dashboardTab.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    //@@author {Nauw1010}
    /**
     * Configures all the listeners.
     */
    void configureListener() {
        entryListPanel.setListenerToSelectedChangesAndPassToEntryContent(entryContent);
        personListPanel.setListenerToSelectedChangesAndPassToContactContent(contactContent);
    }
    //@@author

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryScene.getStylesheets().add(THEME_BRIGHT);
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
            (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    //@@author {Nauw1010}
    /**
     * Changes the color theme.
     */
    @FXML
    private void handleChangeTheme() {
        ObservableList<String> styleSheetList = primaryScene.getStylesheets();

        if (styleSheetList.get(styleSheetList.size() - 1).equals(THEME_DARK)) {
            styleSheetList.set(styleSheetList.size() - 1, THEME_BRIGHT);
        } else {
            styleSheetList.set(styleSheetList.size() - 1, THEME_DARK);
        }
    }
    //@@author

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            personListPanel.select();
            entryListPanel.select();
            logger.info("Execute result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (!commandResult.isSameTab()) {
                if (commandResult.isSwitch()) {
                    handleSwapTabs();
                } else if (commandResult.isAddressBookTab()) {
                    handleSwitchToAddressBookTab();
                } else {
                    handleSwitchToJournalTab();
                }
            }

            if (commandResult.isViewingPerson()) {
                Person personToView = commandResult.getPersonToView();
                personListPanel.select(logic.getFilteredPersonList().indexOf(personToView));
            }

            if (commandResult.isViewingJournal()) {
                Entry entryToView = commandResult.getEntryToView();
                entryListPanel.select(logic.getFilteredEntryList().indexOf(entryToView));
            }

            if (commandResult.isCleaningJournalView()) {
                handleCleaningJournalView();
            }

            if (commandResult.isChangingTheme()) {
                handleChangeTheme();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.warning("Invalid command: " + commandText + ". " + e.getMessage());
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    private void handleSwitchToAddressBookTab() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(addressBookTab);
    }

    private void handleSwitchToJournalTab() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(journalTab);
    }

    private void handleSwapTabs() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();
        selectionModel.select((selectedIndex + 1) % 3);
    }

    private void handleCleaningJournalView() {
        entryContent.setEntryContentToUser(null);
    }
}
