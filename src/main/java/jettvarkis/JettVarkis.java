package jettvarkis;

import jettvarkis.command.Command;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.parser.Parser;
import jettvarkis.storage.Storage;
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

            Command c = Parser.parse(input);
            assert c != null : "Parsed command cannot be null";
            c.execute(ui, tasks, storage);

            System.out.flush();
            System.setOut(old);
            return baos.toString();
        } catch (JettVarkisException e) {
            return e.getMessage();
        }
    }
}
