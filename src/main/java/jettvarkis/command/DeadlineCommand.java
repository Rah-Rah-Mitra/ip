package jettvarkis.command;

import java.time.LocalDateTime;
import java.util.Optional;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.task.Task;
import jettvarkis.ui.Ui;

/**
 * Represents a Deadline command. This command adds a new Deadline task to the
 * list.
 */
public class DeadlineCommand extends Command {

    private final String description;
    private final LocalDateTime by;
    private final String originalBy;
    private final boolean showWarning;

    /**
     * Constructs a DeadlineCommand with the specified description and due date/time
     * as a LocalDateTime object.
     *
     * @param description
     *            The description of the Deadline task.
     * @param by
     *            The due date/time of the task.
     */
    public DeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
        this.originalBy = null;
        this.showWarning = false;
    }

    /**
     * Constructs a DeadlineCommand with the specified description and due date/time
     * as a string.
     *
     * @param description
     *            The description of the Deadline task.
     * @param by
     *            The due date/time of the task as a string.
     */
    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = null;
        this.originalBy = by;
        this.showWarning = false;
    }

    /**
     * Constructs a DeadlineCommand with the specified description, due date/time as
     * a string, and a warning flag.
     *
     * @param description
     *            The description of the Deadline task.
     * @param by
     *            The due date/time of the task as a string.
     * @param showWarning
     *            A boolean indicating whether to show a warning about date format.
     */
    public DeadlineCommand(String description, String by, boolean showWarning) {
        this.description = description;
        this.by = null;
        this.originalBy = by;
        this.showWarning = showWarning;
    }

    /**
     * Executes the Deadline command.
     * Adds a new Deadline task to the task list, displays a confirmation message to
     * the user,
     * and saves the updated task list to storage.
     * If a warning is flagged, it displays a date format warning to the user.
     *
     * @param ui
     *            The Ui object to interact with the user.
     * @param tasks
     *            The TaskList object to add the task to.
     * @param storage
     *            The Storage object to save the tasks.
     * @throws JettVarkisException
     *             If there is an error during execution (e.g., storage error).
     */
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
