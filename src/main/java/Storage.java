import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
                tasks.add(parseLineToTask(line));
            }
        } catch (IOException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.FILE_OPERATION_ERROR);
        } catch (JettVarkisException e) {
            // Handle corrupted data file
            throw new JettVarkisException(JettVarkisException.ErrorType.CORRUPTED_DATA_ERROR);
        }

        return tasks;
    }

    private Task parseLineToTask(String line) throws JettVarkisException {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new JettVarkisException(JettVarkisException.ErrorType.CORRUPTED_DATA_ERROR);
                }
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                if (parts.length < 5) {
                    throw new JettVarkisException(JettVarkisException.ErrorType.CORRUPTED_DATA_ERROR);
                }
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                throw new JettVarkisException(JettVarkisException.ErrorType.CORRUPTED_DATA_ERROR);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
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