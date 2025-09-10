package jettvarkis.command;

import java.util.ArrayList;
import java.util.List;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.task.Task;
import jettvarkis.ui.Ui;

/**
 * Represents a command that updates the status of one or more tasks.
 * This abstract class provides the common logic for marking tasks as done or not done.
 */
public abstract class UpdateStatusCommand extends MultiTaskCommand {

    /**
     * Constructs an UpdateStatusCommand with the specified task indices.
     *
     * @param taskIndices The zero-based indices of the tasks to be updated.
     */
    public UpdateStatusCommand(int... taskIndices) {
        super(taskIndices);
    }

    /**
     * Executes the status update command.
     * Iterates through the specified task indices, applies the status update to each task,
     * displays the result to the user, and saves the updated task list to storage.
     *
     * @param ui The Ui object to interact with the user.
     * @param tasks The TaskList object containing the tasks.
     * @param storage The Storage object to save the tasks.
     * @param jettVarkis The main JettVarkis object.
     * @throws JettVarkisException If any task index is invalid.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage, jettvarkis.JettVarkis jettVarkis)
            throws JettVarkisException {
        assert ui != null;
        assert tasks != null;
        assert storage != null;
        List<Task> affectedTasks = new ArrayList<>();
        for (int taskIndex : taskIndices) {
            if (taskIndex < 0 || taskIndex >= tasks.getTaskCount()) {
                throw new JettVarkisException(JettVarkisException.ErrorType.TASK_NOT_FOUND);
            }
            tasks.getTask(taskIndex).ifPresent(task -> {
                updateTaskStatus(task);
                affectedTasks.add(task);
            });
        }
        if (!affectedTasks.isEmpty()) {
            showResult(ui, affectedTasks);
            storage.save(tasks.getTasks());
        }
    }

    /**
     * Updates the status of a single task. This method must be implemented by concrete subclasses.
     *
     * @param task The task to be updated.
     */
    protected abstract void updateTaskStatus(Task task);

    /**
     * Displays the result of the status update to the user. This method must be implemented by concrete subclasses.
     *
     * @param ui The Ui object to display the result.
     * @param tasks The list of tasks that were affected.
     */
    protected abstract void showResult(Ui ui, List<Task> tasks);
}
