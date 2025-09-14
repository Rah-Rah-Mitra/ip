package jettvarkis.command;

import java.util.List;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.task.Task;
import jettvarkis.ui.Ui;

/**
 * Represents a Find command. This command finds tasks that contain the given
 * keyword.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for.
     */
    public FindCommand(String keyword) {
        assert keyword != null && !keyword.trim().isEmpty();
        this.keyword = keyword;
    }

    /**
     * Executes the Find command.
     * Finds tasks that contain the given keyword and displays them to the user.
     *
     * @param ui
     *            The Ui object to interact with the user.
     * @param tasks
     *            The TaskList object to search for tasks.
     * @param storage
     *            The Storage object (not used in this command).
     * @param jettVarkis
     *            The main JettVarkis object (not used in this command).
     * @throws JettVarkisException
     *             If there is an error during execution.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage,
                        jettvarkis.JettVarkis jettVarkis) throws JettVarkisException {
        assert ui != null;
        assert tasks != null;
        List<Task> foundTasks = tasks.findTasks(keyword);
        assert foundTasks != null : "Found tasks list should not be null";
        ui.showFoundTasks(foundTasks);
    }
}
