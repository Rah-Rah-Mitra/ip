package jettvarkis.command.trivia;

import jettvarkis.JettVarkis;
import jettvarkis.TaskList;
import jettvarkis.command.Command;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.ui.Ui;

/**
 * Represents a command to create a new trivia category.
 */
public class TriviaCreateCommand extends Command {
    private final String categoryName;

    /**
     * Constructs a TriviaCreateCommand with the specified category name.
     *
     * @param categoryName
     *            The name of the new trivia category.
     */
    public TriviaCreateCommand(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Executes the TriviaCreateCommand.
     * Creates a new trivia category file.
     *
     * @param ui
     *            The Ui to interact with the user.
     * @param tasks
     *            The TaskList (not used in this command).
     * @param storage
     *            The Storage to create the new category.
     * @param jettVarkis
     *            The JettVarkis instance (not used in this command).
     * @throws JettVarkisException
     *             If the category name is invalid or an error occurs during file
     *             operations.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage, JettVarkis jettVarkis) throws JettVarkisException {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_TRIVIA_CATEGORY_NAME);
        }
        storage.createTriviaCategory(categoryName);
        ui.showTriviaCategoryCreated(categoryName);
    }

    /**
     * Indicates whether this command should exit the application.
     *
     * @return False, as creating a trivia category does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
