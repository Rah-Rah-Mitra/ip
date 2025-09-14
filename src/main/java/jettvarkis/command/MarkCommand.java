package jettvarkis.command;

import java.util.List;

import jettvarkis.task.Task;
import jettvarkis.ui.Ui;

/**
 * Represents a Mark command. This command marks one or more tasks as done.
 */
public class MarkCommand extends UpdateStatusCommand {

    /**
     * Constructs a MarkCommand with the specified task indices.
     *
     * @param taskIndices The zero-based indices of the tasks to be marked.
     */
    public MarkCommand(int... taskIndices) {
        super(taskIndices);
    }

    /**
     * Updates the status of the task by marking it as done.
     *
     * @param task The task to be marked as done.
     */
    @Override
    protected void updateTaskStatus(Task task) {
        task.markAsDone();
    }

    /**
     * Displays the marked tasks to the user.
     *
     * @param ui The Ui object to display the result.
     * @param tasks The list of tasks that were marked as done.
     */
    @Override
    protected void showResult(Ui ui, List<Task> tasks) {
        ui.showMarkedTasks(tasks);
    }
}
