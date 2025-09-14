package jettvarkis.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import jettvarkis.task.Task;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.parser.Parser;

/**
 * Handles the loading and saving of tasks to and from a file.
 */
public class Storage {

    private final String filePath;

    /**
     * Constructs a new Storage object with the specified file path.
     *
     * @param filePath The path to the file where tasks will be stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * If the file does not exist, it attempts to create it.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws JettVarkisException If there is an error reading from the file or the data is corrupted.
     */
    public ArrayList<Task> load() throws JettVarkisException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            // Handle case where file doesn't exist
            try {
                // Create parent directories if they don't exist
                file.getParentFile().mkdirs();
                // Create the file itself
                file.createNewFile();
            } catch (IOException e) {
                throw new JettVarkisException(JettVarkisException.ErrorType.FILE_OPERATION_ERROR);
            }
            return tasks; // Return empty list as the file is new
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                tasks.add(Parser.parseFileLine(line));
            }
        } catch (IOException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_OPERATION_ERROR);
        } catch (JettVarkisException e) {
            // Handle corrupted data file
            throw new JettVarkisException(JettVarkisException.ErrorType.CORRUPTED_DATA_ERROR);
        }

        return tasks;
    }

    /**
     * Saves the given list of tasks to the storage file.
     *
     * @param tasks The ArrayList of tasks to be saved.
     * @throws JettVarkisException If there is an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws JettVarkisException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(task.toFileString() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_OPERATION_ERROR);
        }
    }
}