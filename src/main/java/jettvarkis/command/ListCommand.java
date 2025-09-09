package jettvarkis.command;

import jettvarkis.TaskList;
import jettvarkis.storage.Storage;
import jettvarkis.ui.Ui;

/**
 * Represents a List command. This command displays all tasks in the list.
 */
public class ListCommand extends Command {

    /**
     * Executes the List command.
     * Displays all tasks currently in the task list to the user.
     *
     * @param ui
     *            The Ui object to interact with the user.
     * @param tasks
     *            The TaskList object containing the tasks to be displayed.
     * @param storage
     *            The Storage object (not used in this command).
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        assert ui != null;
        assert tasks != null;
        assert tasks.getTasks() != null : "Task list from getTasks() should not be null";
        ui.showTasks(tasks.getTasks());
    }
}
