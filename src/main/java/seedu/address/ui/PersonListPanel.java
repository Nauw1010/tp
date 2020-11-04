package seedu.address.ui;

import java.util.logging.Logger;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private JFXListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        //@@author {zhXchD}
        personListView.getSelectionModel().selectFirst();
        //@@author
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends JFXListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
            setText(null);
        }
    }

    //@@author {zhXchD}
    public ObservableList<Person> getPersonListItems() {
        return personListView.getItems();
    }

    /**
     * Selects the item in the list of specified index.
     * @param index the index of the item to be selected
     */
    public void select(int index) {
        personListView.getSelectionModel().select(index);
        personListView.scrollTo(index);
    }

    /**
     * Selects the first item if nothing is being selected.
     */
    public void select() {
        if (personListView.getSelectionModel().getSelectedItems().isEmpty()) {
            personListView.getSelectionModel().selectFirst();
            personListView.scrollTo(0);
        }
    }
    //@@author

    //@@author {Nauw1010}
    /**
     * Sets up the listener listen to the changes of selected person cell and pass the new person's
     * information to the {@code ContactContent}.
     * @param contactContent the ContactContent
     */
    public void setListenerToSelectedChangesAndPassToContactContent(ContactContent contactContent) {
        personListView.getSelectionModel().selectedItemProperty().addListener((observableValue, prev, curr) -> {
            //@@author {zhXchD}
            if (curr == null) {
                personListView.getSelectionModel().selectFirst();
                curr = personListView.getSelectionModel().getSelectedItem();
            }
            //@@author
            contactContent.setContactContentToUser(curr);
        });
    }
    //@@author
}
