import java.time.LocalDateTime;
import java.util.Optional;

public class DeadlineCommand extends Command {

    private final String description;
    private final LocalDateTime by;
    private final String originalBy;
    private final boolean showWarning;

    public DeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
        this.originalBy = null;
        this.showWarning = false;
    }

    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = null;
        this.originalBy = by;
        this.showWarning = false;
    }

    public DeadlineCommand(String description, String by, boolean showWarning) {
        this.description = description;
        this.by = null;
        this.originalBy = by;
        this.showWarning = showWarning;
    }

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        if (showWarning) {
            ui.showError("Did you mean to use a format like 'd/M/yyyy HHmm'? Still adding as a string.");
        }
        if (by != null) {
            tasks.addDeadline(description, by);
        } else {
            tasks.addDeadline(description, originalBy);
        }
        Optional<Task> task = tasks.getTask(tasks.getTaskCount() - 1);
        task.ifPresent(value -> ui.showAddedTask(value, tasks.getTaskCount()));
        storage.save(tasks.getTasks());
    }
}
