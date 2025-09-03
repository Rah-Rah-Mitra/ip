package jettvarkis.command;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.task.Task;
import jettvarkis.ui.Ui;

/**
 * Represents a Delete command. This command deletes a task from the list.
 */
public class DeleteCommand extends Command {

    private final int taskIndex;

    /**
     * Constructs a DeleteCommand with the specified task index.
     *
     * @param taskIndex
     *            The zero-based index of the task to be deleted.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the Delete command.
     * Deletes the task at the specified index from the task list, displays a
     * confirmation message to the user,
     * and saves the updated task list to storage.
     *
     * @param ui
     *            The Ui object to interact with the user.
     * @param tasks
     *            The TaskList object from which to delete the task.
     * @param storage
     *            The Storage object to save the tasks.
     * @throws JettVarkisException
     *             If the task index is out of bounds or the task is not found.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        try {
            Task deletedTask = tasks.deleteTask(taskIndex);
            ui.showDeletedTask(deletedTask, tasks.getTaskCount());
            storage.save(tasks.getTasks());
        } catch (IndexOutOfBoundsException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.TASK_NOT_FOUND);
        }
    }
}
