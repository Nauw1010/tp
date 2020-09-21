package seedu.address.model.journal;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

/**
 * Represents date for journal.
 */
public class Date {

    private final LocalDateTime date;

    /**
     * Creates an instance of date to represent the date of the entry.
     * @param date Event date.
     */
    public Date(LocalDateTime date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Date && ((Date) other).date == this.date);
    }
}

