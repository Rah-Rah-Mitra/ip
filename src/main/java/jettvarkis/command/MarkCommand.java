package jettvarkis.command;

import java.util.ArrayList;
import java.util.List;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.task.Task;
import jettvarkis.ui.Ui;

/**
 * Represents a Mark command. This command marks one or more tasks as done.
 */
public class MarkCommand extends Command {

    private final int[] taskIndices;

    /**
     * Constructs a MarkCommand with the specified task indices.
     *
     * @param taskIndices
     *            The zero-based indices of the tasks to be marked.
     */
    public MarkCommand(int... taskIndices) {
        this.taskIndices = taskIndices;
    }

    /**
     * Executes the Mark command.
     * Marks the tasks at the specified indices as done and displays a message to the
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
     *             If any task index is invalid or a task is not found.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        List<Task> markedTasks = new ArrayList<>();
        for (int taskIndex : taskIndices) {
            tasks.getTask(taskIndex).ifPresentOrElse(task -> {
                task.markAsDone();
                markedTasks.add(task);
            }, () -> {
                try {
                    throw new JettVarkisException(JettVarkisException.ErrorType.TASK_NOT_FOUND);
                } catch (JettVarkisException e) {
                    ui.showError(e.getMessage());
                }
            });
        }
        if (!markedTasks.isEmpty()) {
            ui.showMarkedTasks(markedTasks);
            storage.save(tasks.getTasks());
        }
    }
}
