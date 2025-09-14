package jettvarkis.command;

import jettvarkis.TaskList;
import jettvarkis.storage.Storage;
import jettvarkis.ui.Ui;

/**
 * Represents a Bye command. This command exits the application.
 */
public class ByeCommand extends Command {

    /**
     * Executes the Bye command.
     * Displays a goodbye message to the user.
     *
     * @param ui
     *            The Ui object to interact with the user.
     * @param tasks
     *            The TaskList object (not used in this command).
     * @param storage
     *            The Storage object (not used in this command).
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Returns true, indicating that this command is an exit command.
     *
     * @return True.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
