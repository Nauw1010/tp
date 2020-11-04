package seedu.address.ui;

import java.util.logging.Logger;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.journal.Entry;

//@@author {Nauw1010}
/**
 * Panel containing the list of entries.
 */
public class EntryListPanel extends UiPart<Region> {
    private static final String FXML = "EntryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EntryListPanel.class);

    @FXML
    private JFXListView<Entry> entryListView;

    /**
     * Creates a {@code EntryListPanel} with the given {@code ObservableList}.
     */
    public EntryListPanel(ObservableList<Entry> journalList) {
        super(FXML);
        entryListView.setItems(journalList);
        entryListView.setCellFactory(listView -> new EntryListViewCell());
        //@@author {zhXchD}
        entryListView.getSelectionModel().selectFirst();
        //@@author
    }

    class EntryListViewCell extends JFXListCell<Entry> {
        @Override
        protected void updateItem(Entry entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {
                setGraphic(null);
            } else {
                setGraphic(new EntryCard(entry, getIndex() + 1).getRoot());
            }
            setText(null);
        }
    }

    //@@author {zhXchD}
    public ObservableList<Entry> getEntryListItems() {
        return entryListView.getItems();
    }

    /**
     * Selects the item in the entry list at specified index.
     * @param index the index of item to be selected
     */
    public void select(int index) {
        entryListView.getSelectionModel().select(index);
        entryListView.scrollTo(index);
    }

    /**
     * Selects the first item if nothing is selected.
     */
    public void select() {
        if (entryListView.getSelectionModel().getSelectedItems().isEmpty()) {
            entryListView.getSelectionModel().selectFirst();
            entryListView.scrollTo(0);
        }
    }
    //@@author

    /**
     * Sets up the listener listen to the changes of selected entry cell and pass the new entry
     * to the {@code EntryContent}.
     * @param entryContent the EntryContent
     */
    public void setListenerToSelectedChangesAndPassToEntryContent(EntryContent entryContent) {
        entryListView.getSelectionModel().selectedItemProperty().addListener((observableValue, prev, curr) -> {
            //@@author {zhXchD}
            if (curr == null) {
                entryListView.getSelectionModel().selectFirst();
                curr = entryListView.getSelectionModel().getSelectedItem();
            }
            //@@author
            entryContent.setEntryContentToUser(curr);
        });
    }
}
