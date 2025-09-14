package jettvarkis.command.trivia;

import jettvarkis.JettVarkis;
import jettvarkis.TaskList;
import jettvarkis.command.Command;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.trivia.Trivia;
import jettvarkis.trivia.TriviaList;
import jettvarkis.ui.Ui;

/**
 * Represents a command to delete a trivia item or a whole category.
 */
public class TriviaDeleteCommand extends Command {
    private int index = -1; // Default to -1 to indicate not set
    private String categoryToDelete;
    private boolean isCategoryDeletion;

    /**
     * Constructor for deleting a specific trivia question by index.
     *
     * @param index The 0-based index of the trivia item to delete.
     */
    public TriviaDeleteCommand(int index) {
        this.index = index;
        this.isCategoryDeletion = false;
    }

    /**
     * Constructor for deleting a whole trivia category.
     *
     * @param categoryToDelete The name of the category to delete.
     */
    public TriviaDeleteCommand(String categoryToDelete) {
        this.categoryToDelete = categoryToDelete;
        this.isCategoryDeletion = true;
    }

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage, JettVarkis jettVarkis) throws JettVarkisException {
        if (isCategoryDeletion) {
            storage.deleteTriviaCategory(categoryToDelete);
            ui.showTriviaCategoryDeleted(categoryToDelete);
            // If the deleted category is the current one, reset to default
            if (jettVarkis.getCurrentTriviaCategory().equals(categoryToDelete)) {
                jettVarkis.setCurrentTriviaCategory("default");
                jettVarkis.setTriviaList(storage.loadTrivia("default"));
            }
        } else {
            String category = jettVarkis.getCurrentTriviaCategory();
            TriviaList triviaList = storage.loadTrivia(category);

            if (index < 0 || index >= triviaList.size()) {
                throw new JettVarkisException(JettVarkisException.ErrorType.INVALID_TRIVIA_INDEX);
            }

            Trivia deletedTrivia = triviaList.delete(index);
            storage.saveTrivia(category, triviaList);
            ui.showTriviaDeleted(deletedTrivia, category, triviaList.size());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
