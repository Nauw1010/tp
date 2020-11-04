package seedu.address.model.journal;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Represents date for journal.
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the format: \"YYYY-MM-DD HH:MM\"";

    public static final String VALID_DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public static final DateTimeFormatter VALID_FORMATTER =
            DateTimeFormatter.ofPattern(VALID_DATE_FORMAT);

    public final String value;

    public final LocalDateTime date;

    /**
     * Creates an instance of date to represent the date of the entry.
     *
     * @param date Event date.
     */
    public Date(LocalDateTime date) {
        requireNonNull(date);
        this.date = date.truncatedTo(ChronoUnit.MINUTES);
        this.value = date.format(VALID_FORMATTER);
    }

    /**
     * Creates an instance of date from a string to represent the date of the
     * entry.
     *
     * @param date Event date.
     */
    public Date(String date) {
        if (date == null || date.length() == 0) {
            date = LocalDateTime.now().format(VALID_FORMATTER);
        }
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDateTime.parse(date, VALID_FORMATTER)
                .truncatedTo(ChronoUnit.MINUTES);
        value = date;
    }

    /**
     * Returns true if a given string is a parsable date.
     * @param test String to be tested on.
     * @return true if the given string is a parsable date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDateTime.parse(test, VALID_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if both Date objects are on the same date.
     * @param other date object.
     * @return true if both Date objects are on the same date.
     */
    public boolean isSameDate(Date other) {
        return date.toLocalDate().isEqual(other.date.toLocalDate());
    }

    /**
     * Returns a string representing the date without time.
     * @return a string representing the date without time.
     */
    public String getDateString() {
        return date.toLocalDate().toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Date && ((Date) other).date.equals(date));
    }

    @Override
    public String toString() {
        return date.format(VALID_FORMATTER);
    }
}

