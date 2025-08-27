import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime by;
    protected String originalBy;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        this.originalBy = null;
    }

    public Deadline(String description, String by) {
        super(description);
        this.by = null;
        this.originalBy = by;
    }

    @Override
    public String toString() {
        if (by != null) {
            return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"))
                    + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + originalBy + ")";
        }
    }

    @Override
    public String toFileString() {
        if (by != null) {
            return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
        } else {
            return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + originalBy;
        }
    }
}
