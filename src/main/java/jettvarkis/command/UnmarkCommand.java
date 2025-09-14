package jettvarkis.command;

import java.util.List;

import jettvarkis.task.Task;
import jettvarkis.ui.Ui;

/**
 * Represents an Unmark command. This command unmarks one or more tasks as not done.
 */
public class UnmarkCommand extends UpdateStatusCommand {

    /**
     * Constructs an UnmarkCommand with the specified task indices.
     *
     * @param taskIndices The zero-based indices of the tasks to be unmarked.
     */
    public UnmarkCommand(int... taskIndices) {
        super(taskIndices);
    }

    /**
     * Updates the status of the task by marking it as not done.
     *
     * @param task The task to be marked as not done.
     */
    @Override
    protected void updateTaskStatus(Task task) {
        task.markAsUndone();
    }

    /**
     * Displays the unmarked tasks to the user.
     *
     * @param ui The Ui object to display the result.
     * @param tasks The list of tasks that were unmarked.
     */
    @Override
    protected void showResult(Ui ui, List<Task> tasks) {
        ui.showUnmarkedTasks(tasks);
    }
}
