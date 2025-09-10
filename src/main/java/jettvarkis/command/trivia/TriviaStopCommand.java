package jettvarkis.command.trivia;

import jettvarkis.JettVarkis;
import jettvarkis.TaskList;
import jettvarkis.command.Command;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.ui.Ui;

/**
 * Represents a command to stop the current trivia quiz.
 */
public class TriviaStopCommand extends Command {

    /**
     * Executes the TriviaStopCommand.
     * Stops the trivia quiz mode.
     *
     * @param ui The Ui to interact with the user.
     * @param tasks The TaskList (not used in this command).
     * @param storage The Storage (not used in this command).
     * @param jettVarkis The JettVarkis instance to manage quiz mode.
     * @throws JettVarkisException If an error occurs during command execution.
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage, JettVarkis jettVarkis) throws JettVarkisException {
        if (!jettVarkis.isInQuizMode()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.NOT_IN_QUIZ_MODE);
        }
        jettVarkis.setInQuizMode(false);
        jettVarkis.setCurrentQuizTrivia(null);
        ui.showTriviaStop();
    }

    /**
     * Indicates whether this command should exit the application.
     *
     * @return False, as stopping trivia does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
