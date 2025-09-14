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

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        List<Task> foundTasks = tasks.findTasks(keyword);
        ui.showFoundTasks(foundTasks);
    }
}
