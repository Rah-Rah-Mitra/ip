package jettvarkis.command;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.ui.Ui;

/**
 * Represents a Mark command. This command marks a task as done.
 */
public class MarkCommand extends Command {

    private final int taskIndex;

    /**
     * Constructs a MarkCommand with the specified task index.
     *
     * @param taskIndex
     *            The zero-based index of the task to be marked.
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the Mark command.
     * Marks the task at the specified index as done and displays a message to the
     * user.
     * The changes are then saved to storage.
     *
     * @param ui
     *            The Ui object to interact with the user.
     * @param tasks
     *            The TaskList object containing the tasks.
     * @param storage
     *            The Storage object to save the tasks.
     * @throws JettVarkisException
     *             If the task index is invalid or the task is not found.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        tasks.getTask(taskIndex).ifPresentOrElse(task -> {
            task.markAsDone();
            ui.showMarkedTask(task);
        }, () -> ui.showError(JettVarkisException.ErrorType.TASK_NOT_FOUND.getMessage()));
        storage.save(tasks.getTasks());
    }
}
