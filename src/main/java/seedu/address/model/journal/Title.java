package seedu.address.model.journal;

import static java.util.Objects.requireNonNull;

public class Title {

    private final String title;

    /**
     * Creates an instance of title for entry.
     * @param title Title of the entry.
     */
    public Title(String title) {
        requireNonNull(title);
        this.title = title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Title && ((Title) other).title.equals(this.title));
    }

}
