import java.time.LocalDateTime;
import java.util.Optional;

public class EventCommand extends Command {

    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final String originalFrom;
    private final String originalTo;
    private final boolean showWarning;

    public EventCommand(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
        this.originalFrom = null;
        this.originalTo = null;
        this.showWarning = false;
    }

    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = null;
        this.to = null;
        this.originalFrom = from;
        this.originalTo = to;
        this.showWarning = false;
    }

    public EventCommand(String description, String from, String to, boolean showWarning) {
        this.description = description;
        this.from = null;
        this.to = null;
        this.originalFrom = from;
        this.originalTo = to;
        this.showWarning = showWarning;
    }

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        if (showWarning) {
            ui.showError("Did you mean to use a format like 'd/M/yyyy HHmm'? Still adding as a string.");
        }
        if (from != null && to != null) {
            tasks.addEvent(description, from, to);
        } else {
            tasks.addEvent(description, originalFrom, originalTo);
        }
        Optional<Task> task = tasks.getTask(tasks.getTaskCount() - 1);
        task.ifPresent(value -> ui.showAddedTask(value, tasks.getTaskCount()));
        storage.save(tasks.getTasks());
    }
}
