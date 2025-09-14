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
 * Represents a command to add a new trivia question and answer.
 */
public class TriviaAddCommand extends Command {
    private final String question;
    private final String answer;
    /**
     * Constructs a TriviaAddCommand with the specified question, answer, and category.
     *
     * @param question The trivia question.
     * @param answer The answer to the trivia question.
     */
    public TriviaAddCommand(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Executes the TriviaAddCommand.
     * Adds the new trivia item to the specified category and saves it.
     *
     * @param tasks The TaskList (not used in this command).
     * @param ui The Ui to interact with the user.
     * @param storage The Storage to save the trivia.
     * @throws JettVarkisException If an error occurs during storage operations.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage, JettVarkis jettVarkis) throws JettVarkisException {
        String category = jettVarkis.getCurrentTriviaCategory();
        Trivia newTrivia = new Trivia(question, answer);
        TriviaList triviaList = storage.loadTrivia(category);
        triviaList.add(newTrivia);
        storage.saveTrivia(category, triviaList);
        ui.showTriviaAdded(newTrivia, category, triviaList.size());
    }

    /**
     * Indicates whether this command should exit the application.
     *
     * @return False, as adding trivia does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
