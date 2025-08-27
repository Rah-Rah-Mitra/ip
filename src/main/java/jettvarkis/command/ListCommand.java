
package jettvarkis.command;

import jettvarkis.ui.Ui;
import jettvarkis.TaskList;
import jettvarkis.storage.Storage;

public class ListCommand extends Command {

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        ui.showTasks(tasks.getTasks());
    }
}
