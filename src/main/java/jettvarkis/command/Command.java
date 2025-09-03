package jettvarkis.command;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.ui.Ui;

/**
 * Represents an abstract command in the JettVarkis application.
 * All specific commands (e.g., ByeCommand, AddCommand) should extend this
 * class.
 */
public abstract class Command {
    /**
     * Executes the command.
     * This method must be implemented by concrete command classes to define their
     * specific behavior.
     *
     * @param ui
     *            The Ui object to interact with the user.
     * @param tasks
     *            The TaskList object to manipulate tasks.
     * @param storage
     *            The Storage object to save/load tasks.
     * @throws JettVarkisException
     *             If an error occurs during command execution.
     */
    public abstract void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException;

    /**
     * Checks if this command is an exit command.
     *
     * @return True if this command signifies an exit from the application, false
     *         otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
