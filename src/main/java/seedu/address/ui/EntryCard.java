package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.journal.Entry;

//@@author {Nauw1010}
/**
 * An UI component that displays information of a {@code Entry} briefly.
 */
public class EntryCard extends UiPart<Region> {

    private static final String FXML = "EntryListCard.fxml";

    public final Entry entry;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;
    @FXML
    private Label relatedPersons;

    /**
     * Creates a {@code JournalCard} with the given {@code Entry} and index to display.
     */
    public EntryCard(Entry entry, int displayedIndex) {
        super(FXML);
        this.entry = entry;
        id.setText(displayedIndex + ". ");
        title.setText(entry.getTitle().title);
        date.setText(entry.getDate().value);
        entry.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        relatedPersons.setText(getRelatedPersonString());
    }

    private String getRelatedPersonString() {
        StringBuilder relatedPersonString = new StringBuilder();
        if (this.entry.getContactList().isEmpty()) {
            relatedPersonString.append("No related contact list.");
        } else {
            relatedPersonString.append(this.entry.getContactList().get(0).getName().fullName);
        }
        for (int i = 1; i < this.entry.getContactList().size(); i++) {
            relatedPersonString.append(", ").append(this.entry.getContactList().get(i).getName().fullName);
        }

        return relatedPersonString.toString();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EntryCard)) {
            return false;
        }

        // state check
        EntryCard card = (EntryCard) other;
        return id.getText().equals(card.id.getText())
            && entry.equals(card.entry);
    }
}
