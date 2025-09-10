package jettvarkis.command.trivia;

import java.util.List;

import jettvarkis.JettVarkis;
import jettvarkis.TaskList;
import jettvarkis.command.Command;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.trivia.TriviaList;
import jettvarkis.ui.Ui;

/**
 * Represents a command to list all available trivia categories or all questions in the current category.
 */
public class TriviaListCommand extends Command {

    private final boolean showAll;

    /**
     * Constructs a TriviaListCommand.
     *
     * @param showAll If true, lists all questions in the current category. Otherwise, lists all categories.
     */
    public TriviaListCommand(boolean showAll) {
        this.showAll = showAll;
    }

    /**
     * Executes the TriviaListCommand.
     * Based on the 'showAll' flag, either displays all questions in the current trivia category
     * or lists all available trivia categories.
     *
     * @param ui      The Ui to interact with the user.
     * @param tasks   The TaskList (not used in this command).
     * @param storage The Storage to retrieve trivia data.
     * @param jettVarkis The main application instance.
     * @throws JettVarkisException If an error occurs during storage operations.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage, JettVarkis jettVarkis) throws JettVarkisException {
        if (showAll) {
            String category = jettVarkis.getCurrentTriviaCategory();
            TriviaList triviaList = storage.loadTrivia(category);
            ui.showTriviaList(triviaList, category);
        } else {
            List<String> categories = storage.getTriviaCategories();
            ui.showTriviaCategories(categories);
        }
    }

    /**
     * Indicates whether this command should exit the application.
     *
     * @return False, as this command does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
