package jettvarkis.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.task.Task;
import jettvarkis.ui.Ui;

/**
 * Represents a Delete command. This command deletes one or more tasks from the list.
 */
public class DeleteCommand extends MultiTaskCommand {

    /**
     * Constructs a DeleteCommand with the specified task indices.
     *
     * @param taskIndices The zero-based indices of the tasks to be deleted.
     */
    public DeleteCommand(int... taskIndices) {
        super(taskIndices);
    }

    /**
     * Executes the Delete command.
     * Deletes the tasks at the specified indices from the task list, displays a
     * confirmation message to the user,
     * and saves the updated task list to storage.
     *
     * @param ui The Ui object to interact with the user.
     * @param tasks The TaskList object from which to delete the tasks.
     * @param storage The Storage object to save the tasks.
     * @param jettVarkis The main JettVarkis object (not used in this command).
     * @throws JettVarkisException If any task index is out of bounds.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage,
                        jettvarkis.JettVarkis jettVarkis) throws JettVarkisException {
        assert ui != null;
        assert tasks != null;
        assert storage != null;
        // Sort indices in descending order to avoid issues with shifting indices after deletion
        Integer[] sortedIndices = Arrays.stream(taskIndices).boxed()
                .sorted(Comparator.reverseOrder())
                .toArray(Integer[]::new);

        List<Task> deletedTasks = new ArrayList<>();
        try {
            for (int index : sortedIndices) {
                deletedTasks.add(tasks.deleteTask(index));
            }
            ui.showDeletedTasks(deletedTasks, tasks.getTaskCount());
            storage.save(tasks.getTasks());
        } catch (IndexOutOfBoundsException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.TASK_NOT_FOUND);
        }
    }
}
