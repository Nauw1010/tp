package seedu.address.ui;

import java.util.Comparator;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.MainApp;
import seedu.address.model.journal.Entry;
import seedu.address.model.person.Person;

//@@author {Nauw1010}
/**
 * An UI component that displays information of a {@code Entry} in detail.
 */
public class EntryContent extends UiPart<Region> {

    private static final String FXML = "EntryContent.fxml";

    private Entry entry;

    private final Text emptyRelatedListText = new Text("No related contact list.");
    @FXML
    private VBox entryPane;
    @FXML
    private StackPane titlePane;
    @FXML
    private StackPane relatedListPane;
    @FXML
    private Text title;
    @FXML
    private JFXTextArea description;
    @FXML
    private JFXListView<Person> relatedPersonListView;
    @FXML
    private ImageView calendar;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code EntryContent} containing no {@code Entry} temporarily.
     */
    public EntryContent() {
        super(FXML);
        this.entry = null;
        // this.calendar.setImage(getImage("/images/calendar_1.png"));
        emptyRelatedListText.getStyleClass().add("text-empty-list");
        relatedPersonListView.setCellFactory(listView -> new PersonListViewCell());
        setDefaultContent();
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    private void setDefaultContent() {
        this.calendar.setImage(null);
        title.setText("");
        relatedListPane.getChildren().setAll(new Text(""));
        description.setText("Please select a Journal Entry...");
        date.setText("");
        tags.getChildren().clear();
    }

    private void setContent(Entry entry) {
        this.calendar.setImage(getImage("/images/calendar_1.png"));
        title.setText(entry.getTitle().title);
        if (entry.getContactList().isEmpty()) {
            relatedListPane.getChildren().setAll(emptyRelatedListText);
        } else {
            relatedPersonListView.setItems(entry.getContactList());
            relatedListPane.getChildren().setAll(relatedPersonListView);
        }
        description.setText(entry.getDescription().description);
        date.setText(entry.getDate().value);
        tags.getChildren().clear();
        entry.getTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Updates the information of a nullable {@code Entry}.
     * If entry is null, the content will be reset to default.
     * @param entry the new entry
     */
    public void setEntryContentToUser(Entry entry) {
        this.entry = entry;
        if (entry == null) {
            setDefaultContent();
        } else {
            setContent(entry);
        }
    }

    public Entry getEntry() {
        return entry;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends JFXListCell<Person> {
        {
            setPrefWidth(250.0);
        }

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
}
