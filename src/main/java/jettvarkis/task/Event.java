package jettvarkis.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task. An Event task has a description, a start time, and an end time.
 */
public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;
    protected String originalFrom;
    protected String originalTo;

    /**
     * Constructs a new Event task with the given description, start time, and end time.
     *
     * @param description The description of the Event task.
     * @param from The start time of the event as a LocalDateTime object.
     * @param to The end time of the event as a LocalDateTime object.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        assert from != null : "LocalDateTime 'from' cannot be null";
        assert to != null : "LocalDateTime 'to' cannot be null";
        this.from = from;
        this.to = to;
        this.originalFrom = null;
        this.originalTo = null;
    }

    /**
     * Constructs a new Event task with the given description, start time, and end time as strings.
     *
     * @param description The description of the Event task.
     * @param from The start time of the event as a string.
     * @param to The end time of the event as a string.
     */
    public Event(String description, String from, String to) {
        super(description);
        assert from != null && !from.trim().isEmpty() : "String 'from' cannot be null or empty";
        assert to != null && !to.trim().isEmpty() : "String 'to' cannot be null or empty";
        this.from = null;
        this.to = null;
        this.originalFrom = from;
        this.originalTo = to;
    }

    /**
     * Returns a string representation of the Event task for display.
     *
     * @return A string representing the Event task.
     */
    @Override
    public String toString() {
        String fromStr = from != null ? from.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) : originalFrom;
        String toStr = to != null ? to.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) : originalTo;
        return "[E]" + super.toString() + " (from: " + fromStr + " to: " + toStr + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Event other = (Event) obj;
        boolean fromEquals = (from == null && other.from == null) || (from != null && from.equals(other.from));
        boolean toEquals = (to == null && other.to == null) || (to != null && to.equals(other.to));
        boolean originalFromEquals = (originalFrom == null && other.originalFrom == null)
                || (originalFrom != null && originalFrom.equals(other.originalFrom));
        boolean originalToEquals = (originalTo == null && other.originalTo == null)
                || (originalTo != null && originalTo.equals(other.originalTo));
        return fromEquals && toEquals && originalFromEquals && originalToEquals;
    }

    /**
     * Returns a string representation of the Event task for saving to a file.
     *
     * @return A string representing the Event task in file format.
     */
    @Override
    public String toFileString() {
        String fromStr = from != null ? from.toString() : originalFrom;
        String toStr = to != null ? to.toString() : originalTo;
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + fromStr + " | " + toStr;
    }
}
