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

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        assert ui != null;
        assert tasks != null;
        List<Task> foundTasks = tasks.findTasks(keyword);
        assert foundTasks != null : "Found tasks list should not be null";
        ui.showFoundTasks(foundTasks);
    }
}
