package jettvarkis;

import jettvarkis.ui.Ui;
import jettvarkis.storage.Storage;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.command.Command;
import jettvarkis.parser.Parser;

/**
 * Represents the main class of the JettVarkis application.
 * This class handles the initialization and execution of the application,
 * including UI, storage, and task management.
 */
public class JettVarkis {

    private final Ui ui;
    private TaskList tasks;
    private final Storage storage;

    /**
     * Constructs a new JettVarkis object.
     * Initializes the UI, storage, and loads tasks from the specified file path.
     * If loading tasks fails, an empty TaskList is created.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public JettVarkis(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (JettVarkisException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the JettVarkis application.
     * Displays a welcome message, reads user commands, parses them,
     * executes the corresponding actions, and handles exceptions.
     * The application continues to run until an exit command is received.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(ui, tasks, storage);
                isExit = c.isExit();
            } catch (JettVarkisException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * The main method to start the JettVarkis application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new JettVarkis("../data/jettvarkis.txt").run();
    }
}