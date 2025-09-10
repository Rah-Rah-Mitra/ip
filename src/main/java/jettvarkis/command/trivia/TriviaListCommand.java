package jettvarkis.command.trivia;

import jettvarkis.JettVarkis;
import jettvarkis.TaskList;
import jettvarkis.command.Command;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.trivia.TriviaList;
import jettvarkis.ui.Ui;

/**
 * Represents a command to list all available trivia categories.
 */
public class TriviaListCommand extends Command {

    /**
     * Executes the TriviaListCommand.
     * Retrieves and displays all available trivia categories.
     *
     * @param tasks The TaskList (not used in this command).
     * @param ui The Ui to interact with the user.
     * @param storage The Storage to retrieve trivia categories.
     * @throws JettVarkisException If an error occurs during storage operations.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage, JettVarkis jettVarkis) throws JettVarkisException {
        String category = jettVarkis.getCurrentTriviaCategory();
        TriviaList triviaList = storage.loadTrivia(category);
        ui.showTriviaList(triviaList, category);
    }

    /**
     * Indicates whether this command should exit the application.
     *
     * @return False, as listing trivia categories does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
