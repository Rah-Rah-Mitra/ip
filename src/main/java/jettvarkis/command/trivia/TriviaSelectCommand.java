package jettvarkis.command.trivia;

import jettvarkis.JettVarkis;
import jettvarkis.TaskList;
import jettvarkis.command.Command;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.trivia.TriviaList;
import jettvarkis.ui.Ui;

/**
 * Represents a command to select a trivia category.
 */
public class TriviaSelectCommand extends Command {
    private final String category;

    /**
     * Constructs a TriviaSelectCommand with the specified category.
     *
     * @param category The name of the trivia category to select.
     */
    public TriviaSelectCommand(String category) {
        this.category = category;
    }

    /**
     * Executes the TriviaSelectCommand.
     * Loads the specified trivia category and sets it as the current one.
     *
     * @param ui The Ui to interact with the user.
     * @param tasks The TaskList (not used in this command).
     * @param storage The Storage to load the trivia.
     * @param jettVarkis The JettVarkis instance to update the current trivia list.
     * @throws JettVarkisException If an error occurs during storage operations or the category is invalid.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage, JettVarkis jettVarkis) throws JettVarkisException {
        TriviaList selectedTriviaList = storage.loadTrivia(category);
        jettVarkis.setTriviaList(selectedTriviaList);
        jettVarkis.setCurrentTriviaCategory(category);
        ui.showTriviaCategorySelected(category, selectedTriviaList.size());
    }

    /**
     * Indicates whether this command should exit the application.
     *
     * @return False, as selecting a trivia category does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
