package jettvarkis.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jettvarkis.exception.JettVarkisException;
import jettvarkis.parser.Parser;
import jettvarkis.task.Task;
import jettvarkis.trivia.Trivia;
import jettvarkis.trivia.TriviaList;

/**
 * Handles the loading and saving of tasks to and from a file.
 */
public class Storage {

    private final String filePath;

    /**
     * Constructs a new Storage object with the specified file path.
     *
     * @param filePath
     *            The path to the file where tasks will be stored.
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty();
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * If the file does not exist, it attempts to create it.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws JettVarkisException
     *             If there is an error reading from the file or the data is
     *             corrupted.
     */
    public ArrayList<Task> load() throws JettVarkisException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (file.isDirectory()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_IS_DIRECTORY);
        }

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                throw new JettVarkisException(JettVarkisException.ErrorType.FILE_WRITE_DENIED);
            }
            return tasks; // Return empty list as the file is new
        }

        if (!file.canRead()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_READ_DENIED);
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
     * @param tasks
     *            The ArrayList of tasks to be saved.
     * @throws JettVarkisException
     *             If there is an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws JettVarkisException {
        assert tasks != null;
        File file = new File(filePath);

        if (file.isDirectory()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_IS_DIRECTORY);
        }

        if (file.exists() && !file.canWrite()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_WRITE_DENIED);
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : tasks) {
                assert task.toFileString() != null : "Task file string cannot be null";
                writer.write(task.toFileString() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_OPERATION_ERROR);
        }
    }

    /**
     * Loads a trivia list from a specific category file.
     *
     * @param category
     *            The name of the trivia category (and file).
     * @return A TriviaList object.
     * @throws JettVarkisException
     *             If there is an error reading the file.
     */
    public TriviaList loadTrivia(String category) throws JettVarkisException {
        List<Trivia> triviaItems = new ArrayList<>();
        File file = new File("data/trivia/" + category + ".txt");

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                throw new JettVarkisException(JettVarkisException.ErrorType.FILE_OPERATION_ERROR);
            }
            return new TriviaList(triviaItems);
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" \\| ");
                if (parts.length == 2) {
                    triviaItems.add(new Trivia(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_OPERATION_ERROR);
        }

        return new TriviaList(triviaItems);
    }

    /**
     * Saves a trivia list to a specific category file.
     *
     * @param category
     *            The name of the trivia category.
     * @param triviaList
     *            The list of trivia to save.
     * @throws JettVarkisException
     *             If there is an error writing to the file.
     */
    public void saveTrivia(String category, TriviaList triviaList) throws JettVarkisException {
        File file = new File("data/trivia/" + category + ".txt");
        try (FileWriter writer = new FileWriter(file)) {
            for (Trivia trivia : triviaList.getAllTrivia()) {
                writer.write(trivia.toFileFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_OPERATION_ERROR);
        }
    }

    /**
     * Gets a list of all available trivia categories.
     *
     * @return A list of category names.
     */
    public List<String> getTriviaCategories() {
        File folder = new File("data/trivia");
        File[] files = folder.listFiles();
        List<String> categoryNames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    categoryNames.add(file.getName().replace(".txt", ""));
                }
            }
        }
        return categoryNames;
    }

    /**
     * Creates a new, empty trivia category file.
     *
     * @param categoryName
     *            The name of the category to create.
     * @throws JettVarkisException
     *             If an error occurs during file creation.
     */
    public void createTriviaCategory(String categoryName) throws JettVarkisException {
        File file = new File("data/trivia/" + categoryName + ".txt");
        if (file.exists()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.TRIVIA_CATEGORY_ALREADY_EXISTS, categoryName);
        }
        try {
            file.getParentFile().mkdirs(); // Ensure parent directories exist
            file.createNewFile(); // Create the new category file
        } catch (IOException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_OPERATION_ERROR);
        }
    }

    /**
     * Deletes a trivia category file.
     *
     * @param categoryName The name of the category to delete.
     * @throws JettVarkisException If an error occurs during file deletion.
     */
    public void deleteTriviaCategory(String categoryName) throws JettVarkisException {
        File file = new File("data/trivia/" + categoryName + ".txt");
        if (!file.exists()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.TRIVIA_CATEGORY_NOT_FOUND, categoryName);
        }
        if (!file.delete()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_OPERATION_ERROR);
        }
    }
}
