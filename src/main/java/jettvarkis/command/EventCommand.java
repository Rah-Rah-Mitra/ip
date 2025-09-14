package jettvarkis.command;

import java.time.LocalDateTime;
import java.util.Optional;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.task.Task;
import jettvarkis.ui.Ui;

/**
 * Represents an Event command. This command adds a new Event task to the list.
 */
public class EventCommand extends Command {

    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final String originalFrom;
    private final String originalTo;
    private final boolean shouldShowWarning;

    /**
     * Constructs an EventCommand with the specified description, start time, and
     * end time as LocalDateTime objects.
     *
     * @param description
     *            The description of the Event task.
     * @param from
     *            The start time of the event.
     * @param to
     *            The end time of the event.
     */
    public EventCommand(String description, LocalDateTime from, LocalDateTime to) {
        assert description != null && !description.trim().isEmpty();
        this.description = description;
        this.from = from;
        this.to = to;
        this.originalFrom = null;
        this.originalTo = null;
        this.shouldShowWarning = false;
    }

    /**
     * Constructs an EventCommand with the specified description, start time, and
     * end time as strings.
     *
     * @param description
     *            The description of the Event task.
     * @param from
     *            The start time of the event as a string.
     * @param to
     *            The end time of the event as a string.
     */
    public EventCommand(String description, String from, String to) {
        assert description != null && !description.trim().isEmpty();
        this.description = description;
        this.from = null;
        this.to = null;
        this.originalFrom = from;
        this.originalTo = to;
        this.shouldShowWarning = false;
    }

    /**
     * Constructs an EventCommand with the specified description, start time, end
     * time as strings, and a warning flag.
     *
     * @param description
     *            The description of the Event task.
     * @param from
     *            The start time of the event as a string.
     * @param to
     *            The end time of the event as a string.
     * @param shouldShowWarning
     *            A boolean indicating whether to show a warning about date format.
     */
    public EventCommand(String description, String from, String to, boolean shouldShowWarning) {
        assert description != null && !description.trim().isEmpty();
        this.description = description;
        this.from = null;
        this.to = null;
        this.originalFrom = from;
        this.originalTo = to;
        this.shouldShowWarning = shouldShowWarning;
    }

    /**
     * Executes the Event command.
     * Adds a new Event task to the task list, displays a confirmation message to
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
     * @param jettVarkis
     *            The main JettVarkis object (not used in this command).
     * @throws JettVarkisException
     *             If there is an error during execution (e.g., storage error).
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage,
                        jettvarkis.JettVarkis jettVarkis) throws JettVarkisException {
        assert ui != null;
        assert tasks != null;
        assert storage != null;
        if (shouldShowWarning) {
            ui.showError("Did you mean to use a format like 'd/M/yyyy HHmm'? Still adding as a string.");
        }
        if (from != null && to != null) {
            tasks.addEvent(description, from, to);
        } else {
            tasks.addEvent(description, originalFrom, originalTo);
        }
        assert tasks.getTaskCount() > 0 : "Task list should not be empty after adding a task";
        Optional<Task> task = tasks.getTask(tasks.getTaskCount() - 1);
        assert task.isPresent() : "Newly added task should be present";
        task.ifPresent(value -> ui.showAddedTask(value, tasks.getTaskCount()));
        storage.save(tasks.getTasks());
    }
}
