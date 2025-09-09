package jettvarkis.command;

import java.util.ArrayList;
import java.util.List;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.task.Task;
import jettvarkis.ui.Ui;

/**
 * Represents an Unmark command. This command unmarks one or more tasks as not done.
 */
public class UnmarkCommand extends Command {

    private final int[] taskIndices;

    /**
     * Constructs an UnmarkCommand with the specified task indices.
     *
     * @param taskIndices
     *            The zero-based indices of the tasks to be unmarked.
     */
    public UnmarkCommand(int... taskIndices) {
        assert taskIndices != null;
        this.taskIndices = taskIndices;
    }

    /**
     * Executes the Unmark command.
     * Unmarks the tasks at the specified indices and displays a message to the user.
     * The changes are then saved to storage.
     *
     * @param ui
     *            The Ui object to interact with the user.
     * @param tasks
     *            The TaskList object containing the tasks.
     * @param storage
     *            The Storage object to save the tasks.
     * @throws JettVarkisException
     *             If any task index is invalid or a task is not found.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        assert ui != null;
        assert tasks != null;
        assert storage != null;
        List<Task> unmarkedTasks = new ArrayList<>();
        for (int taskIndex : taskIndices) {
            assert taskIndex >= 0 && taskIndex < tasks.getTaskCount();
            tasks.getTask(taskIndex).ifPresentOrElse(task -> {
                task.markAsUndone();
                unmarkedTasks.add(task);
            }, () -> {
                try {
                    throw new JettVarkisException(JettVarkisException.ErrorType.TASK_NOT_FOUND);
                } catch (JettVarkisException e) {
                    ui.showError(e.getMessage());
                }
            });
        }
        if (!unmarkedTasks.isEmpty()) {
            assert unmarkedTasks != null : "Unmarked tasks list should not be null";
            ui.showUnmarkedTasks(unmarkedTasks);
            storage.save(tasks.getTasks());
        }
    }
}
