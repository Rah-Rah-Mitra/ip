package jettvarkis;

import jettvarkis.command.Command;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.parser.Parser;
import jettvarkis.storage.Storage;
import jettvarkis.trivia.Trivia;
import jettvarkis.trivia.TriviaList;
import jettvarkis.ui.Ui;

/**
 * Represents the main class of the JettVarkis application.
 * This class handles the initialization and execution of the application,
 * including UI, storage, and task management.
 */
public class JettVarkis {

    private final Ui ui;
    private TaskList tasks;
    private final Storage storage;
    private TriviaList triviaList;
    private String currentTriviaCategory;
    private boolean inQuizMode = false;
    private Trivia currentQuizTrivia = null;

    /**
     * Constructs a new JettVarkis object.
     * Initializes the UI, storage, and loads tasks from the specified file path.
     * If loading tasks fails, an empty TaskList is created.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public JettVarkis(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty();
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (JettVarkisException e) {
            // Error should be handled by the GUI
            tasks = new TaskList();
        }
        try {
            // Load a default trivia category
            currentTriviaCategory = "default";
            triviaList = storage.loadTrivia(currentTriviaCategory);
        } catch (JettVarkisException e) {
            // Error should be handled by the GUI
            triviaList = new TriviaList();
        }
    }

    /**
     * Creates a response to user input.
     *
     * @param input The user input string.
     * @return The response from the chatbot.
     */
    public String getResponse(String input) {
        assert input != null;
        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream ps = new java.io.PrintStream(baos);
            java.io.PrintStream old = System.out;
            System.setOut(ps);

            if (inQuizMode) {
                // Handle quiz answers
                if (input.equalsIgnoreCase("trivia stop")) {
                    setInQuizMode(false);
                    setCurrentQuizTrivia(null);
                    ui.showTriviaStop();
                } else if (currentQuizTrivia != null) {
                    checkAnswer(input);
                    if (inQuizMode) { // if not stopped
                        askNextQuestion();
                    }
                }
            } else {
                Command c = Parser.parse(input);
                assert c != null : "Parsed command cannot be null";
                c.execute(ui, tasks, storage, this); // Pass this JettVarkis instance
            }

            System.out.flush();
            System.setOut(old);
            return baos.toString();
        } catch (JettVarkisException e) {
            return e.getMessage();
        }
    }

    private void checkAnswer(String userAnswer) {
        if (userAnswer.equalsIgnoreCase(currentQuizTrivia.getAnswer())) {
            ui.showCorrectAnswer();
        } else {
            ui.showIncorrectAnswer(currentQuizTrivia.getAnswer());
        }
    }

    /**
     * Asks the next trivia question in the current quiz session.
     * If there are no more questions, it will display an error and exit quiz mode.
     */
    public void askNextQuestion() {
        currentQuizTrivia = triviaList.getRandomTrivia();
        if (currentQuizTrivia != null) {
            ui.showTriviaQuestion(currentQuizTrivia);
        } else {
            ui.showError("The current trivia category is empty!");
            setInQuizMode(false);
        }
    }


    /**
     * Returns the welcome message for the application.
     * @return The welcome message string.
     */
    public String getWelcomeMessage() {
        return ui.getWelcomeMessage();
    }

    public TriviaList getTriviaList() {
        return triviaList;
    }

    public void setTriviaList(TriviaList triviaList) {
        this.triviaList = triviaList;
    }

    public String getCurrentTriviaCategory() {
        return currentTriviaCategory;
    }

    public void setCurrentTriviaCategory(String currentTriviaCategory) {
        this.currentTriviaCategory = currentTriviaCategory;
    }

    /**
     * Gets the Storage object.
     * @return The Storage object.
     */
    public Storage getStorage() {
        return storage;
    }

    public boolean isInQuizMode() {
        return inQuizMode;
    }

    public void setInQuizMode(boolean inQuizMode) {
        this.inQuizMode = inQuizMode;
    }

    public Trivia getCurrentQuizTrivia() {
        return currentQuizTrivia;
    }

    public void setCurrentQuizTrivia(Trivia currentQuizTrivia) {
        this.currentQuizTrivia = currentQuizTrivia;
    }
}
