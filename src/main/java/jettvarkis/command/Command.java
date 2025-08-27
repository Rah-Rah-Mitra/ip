
package jettvarkis.command;

import jettvarkis.ui.Ui;
import jettvarkis.TaskList;
import jettvarkis.storage.Storage;
import jettvarkis.exception.JettVarkisException;

public abstract class Command {
    public abstract void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException;

    public boolean isExit() {
        return false;
    }
}
