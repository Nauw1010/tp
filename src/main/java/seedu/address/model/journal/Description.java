package seedu.address.model.journal;

import static java.util.Objects.requireNonNull;

public class Description {

    public final String description;

    /**
     * Creates an instance of description for entry.
     *
     * @param description Description of an entry.
     */
    public Description(String description) {
        requireNonNull(description);
        this.description = description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Description
            && ((Description) other).description.equals(description));
    }

    @Override
    public String toString() {
        return description;
    }
}
