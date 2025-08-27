package jettvarkis.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;
    protected String originalFrom;
    protected String originalTo;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
        this.originalFrom = null;
        this.originalTo = null;
    }

    public Event(String description, String from, String to) {
        super(description);
        this.from = null;
        this.to = null;
        this.originalFrom = from;
        this.originalTo = to;
    }

    @Override
    public String toString() {
        String fromStr = from != null ? from.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) : originalFrom;
        String toStr = to != null ? to.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) : originalTo;
        return "[E]" + super.toString() + " (from: " + fromStr + " to: " + toStr + ")";
    }

    @Override
    public String toFileString() {
        String fromStr = from != null ? from.toString() : originalFrom;
        String toStr = to != null ? to.toString() : originalTo;
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + fromStr + " | " + toStr;
    }
}
