package jettvarkis.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import jettvarkis.task.Task;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.parser.Parser;

public class Storage {

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
