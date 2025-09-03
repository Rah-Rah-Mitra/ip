package jettvarkis.command;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.ui.Ui;

/**
 * Represents an Unmark command. This command unmarks a task as not done.
 */
public class UnmarkCommand extends Command {

    private final int taskIndex;

    /**
     * Constructs an UnmarkCommand with the specified task index.
     *
     * @param taskIndex
     *            The zero-based index of the task to be unmarked.
     */
    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the Unmark command.
     * Unmarks the task at the specified index and displays a message to the user.
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
            task.markAsUndone();
            ui.showUnmarkedTask(task);
        }, () -> ui.showError(JettVarkisException.ErrorType.TASK_NOT_FOUND.getMessage()));
        storage.save(tasks.getTasks());
    }
}
