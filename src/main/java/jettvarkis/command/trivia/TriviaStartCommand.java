package jettvarkis.command.trivia;

import jettvarkis.JettVarkis;
import jettvarkis.TaskList;
import jettvarkis.command.Command;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.trivia.TriviaList;
import jettvarkis.ui.Ui;

/**
 * Represents a command to start a trivia quiz.
 */
public class TriviaStartCommand extends Command {

    /**
     * Executes the TriviaStartCommand.
     * Initiates the trivia quiz mode and asks the first question.
     *
     * @param ui The Ui to interact with the user.
     * @param tasks The TaskList (not used in this command).
     * @param storage The Storage (not used in this command).
     * @param jettVarkis The JettVarkis instance to manage quiz mode and trivia questions.
     * @throws JettVarkisException If no trivia category is selected or the selected category is empty.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage, JettVarkis jettVarkis) throws JettVarkisException {
        TriviaList currentTriviaList = jettVarkis.getTriviaList();
        if (currentTriviaList == null || currentTriviaList.size() == 0) {
            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_TRIVIA_LIST);
        }

        jettVarkis.setInQuizMode(true);
        ui.showTriviaStart(jettVarkis.getCurrentTriviaCategory());
        jettVarkis.askNextQuestion();
    }

    /**
     * Indicates whether this command should exit the application.
     *
     * @return False, as starting trivia does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
