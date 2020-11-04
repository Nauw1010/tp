package seedu.address.ui;

import java.util.Comparator;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.model.person.Person;

//@@author {Nauw1010}
public class ContactContent extends UiPart<Region> {

    private static final String FXML = "ContactContent.fxml";

    private Person person;

    @FXML
    private ImageView profile;
    @FXML
    private Label nameArea;
    @FXML
    private JFXTextField phoneArea;
    @FXML
    private JFXTextField addressArea;
    @FXML
    private JFXTextField emailArea;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ContactContent} containing no {@code Person} temporarily.
     */
    public ContactContent() {
        super(FXML);
        this.person = null;
        this.profile.setImage(getImage("/images/profile.png"));
        setDefaultContent();
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    private void setDefaultContent() {
        nameArea.setText("");
        phoneArea.setText("");
        addressArea.setText("");
        emailArea.setText("");
        tags.getChildren().clear();
    }

    private void setContent(Person person) {
        nameArea.setText(person.getName().fullName);
        phoneArea.setText(person.getPhone().value);
        addressArea.setText(person.getAddress().value);
        emailArea.setText(person.getEmail().value);
        tags.getChildren().clear();
        person.getTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Updates the information of a nullable {@code Person}.
     * If person is null, the content will be reset to default.
     * @param person the new person
     */
    public void setContactContentToUser(Person person) {
        this.person = person;
        if (person == null) {
            setDefaultContent();
        } else {
            setContent(person);
        }
    }

    public Person getPerson() {
        return person;
    }
}
