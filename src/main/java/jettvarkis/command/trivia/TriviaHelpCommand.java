package jettvarkis.command.trivia;

import jettvarkis.JettVarkis;
import jettvarkis.TaskList;
import jettvarkis.command.Command;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.ui.Ui;

/**
 * Represents a command to display help information for trivia commands.
 */
public class TriviaHelpCommand extends Command {

    /**
     * Executes the TriviaHelpCommand.
     * Displays a summary of all trivia commands and their functions.
     *
     * @param ui The Ui to interact with the user.
     * @param tasks The TaskList (not used in this command).
     * @param storage The Storage (not used in this command).
     * @param jettVarkis The JettVarkis instance (not used in this command).
     * @throws JettVarkisException If an error occurs during command execution.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage, JettVarkis jettVarkis) throws JettVarkisException {
        ui.showTriviaHelp();
    }

    /**
     * Indicates whether this command should exit the application.
     *
     * @return False, as displaying help does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
