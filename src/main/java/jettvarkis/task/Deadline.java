package jettvarkis.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task. A Deadline task has a description and a due date/time.
 */
public class Deadline extends Task {

    protected LocalDateTime by;
    protected String originalBy;

    /**
     * Constructs a new Deadline task with the given description and due date/time.
     *
     * @param description The description of the Deadline task.
     * @param by The due date/time of the task as a LocalDateTime object.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        this.originalBy = null;
    }

    /**
     * Constructs a new Deadline task with the given description and due date/time as a string.
     *
     * @param description The description of the Deadline task.
     * @param by The due date/time of the task as a string.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = null;
        this.originalBy = by;
    }

    /**
     * Returns a string representation of the Deadline task for display.
     *
     * @return A string representing the Deadline task.
     */
    @Override
    public String toString() {
        if (by != null) {
            return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"))
                    + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + originalBy + ")";
        }
    }

    /**
     * Returns a string representation of the Deadline task for saving to a file.
     *
     * @return A string representing the Deadline task in file format.
     */
    @Override
    public String toFileString() {
        if (by != null) {
            return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
        } else {
            return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + originalBy;
        }
    }
}
