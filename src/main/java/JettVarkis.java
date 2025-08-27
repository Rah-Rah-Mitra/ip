import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class JettVarkis {

    private final Ui ui;
    private TaskList tasks;
    private final Storage storage;

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

    private LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                // For formats without time, default to the start of the day.
                if (!formatter.toString().contains("HHmm")) {
                     return LocalDate.parse(dateTimeString, formatter).atStartOfDay();
                }
                return LocalDateTime.parse(dateTimeString, formatter);
            } catch (DateTimeParseException e) {
                // Continue to try the next format
            }
        }
        throw new DateTimeParseException("Unable to parse date-time: " + dateTimeString, dateTimeString, 0);
    }

    public void run() {
        ui.showWelcome();
        String fullCommand = ui.readCommand();

        while (!fullCommand.equals("bye")) {
            try {
                String[] parts = fullCommand.split(" ", 2);
                String command = parts[0];

                switch (command) {
                    case "list":
                        ui.showTasks(tasks.getTasks());
                        break;
                    case "mark": {
                        if (parts.length < 2) {
                            throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
                        }
                        int taskIndex = Integer.parseInt(parts[1]) - 1;
                        tasks.getTask(taskIndex).ifPresentOrElse(task -> {
                            task.markAsDone();
                            ui.showMarkedTask(task);
                        }, () -> ui.showError(JettVarkisException.ErrorType.TASK_NOT_FOUND.getMessage()));
                        break;
                    }
                    case "unmark": {
                        if (parts.length < 2) {
                            throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
                        }
                        int taskIndex = Integer.parseInt(parts[1]) - 1;
                        tasks.getTask(taskIndex).ifPresentOrElse(task -> {
                            task.markAsUndone();
                            ui.showUnmarkedTask(task);
                        }, () -> ui.showError(JettVarkisException.ErrorType.TASK_NOT_FOUND.getMessage()));
                        break;
                    }
                    case "todo": {
                        if (parts.length < 2) {
                            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION);
                        }
                        tasks.addTodo(parts[1]);
                        Optional<Task> task = tasks.getTask(tasks.getTaskCount() - 1);
                        task.ifPresent(value -> ui.showAddedTask(value, tasks.getTaskCount()));
                        break;
                    }
                    case "deadline": {
                        if (parts.length < 2) {
                            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_DEADLINE_DESCRIPTION);
                        }
                        String[] deadlineParts = parts[1].split(" /by ");
                        if (deadlineParts.length < 2) {
                            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_DEADLINE_BY);
                        }
                        String description = deadlineParts[0];
                        String by = deadlineParts[1];
                        try {
                            LocalDateTime byDateTime = parseDateTime(by);
                            tasks.addDeadline(description, byDateTime);
                        } catch (DateTimeParseException e) {
                            if (by.matches(".*\\d.*") && (by.contains("/") || by.contains("-"))) {
                                ui.showError("Did you mean to use a format like 'd/M/yyyy HHmm'? Still adding as a string.");
                            }
                            tasks.addDeadline(description, by);
                        }
                        Optional<Task> task = tasks.getTask(tasks.getTaskCount() - 1);
                        task.ifPresent(value -> ui.showAddedTask(value, tasks.getTaskCount()));
                        break;
                    }
                    case "event": {
                        if (parts.length < 2) {
                            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_EVENT_DESCRIPTION);
                        }
                        String[] eventParts = parts[1].split(" /from ");
                        if (eventParts.length < 2) {
                            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_EVENT_FROM);
                        }
                        String[] fromToParts = eventParts[1].split(" /to ");
                        if (fromToParts.length < 2) {
                            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_EVENT_TO);
                        }
                        String description = eventParts[0];
                        String from = fromToParts[0];
                        String to = fromToParts[1];
                        try {
                            LocalDateTime fromDateTime = parseDateTime(from);
                            LocalDateTime toDateTime = parseDateTime(to);
                            tasks.addEvent(description, fromDateTime, toDateTime);
                        } catch (DateTimeParseException e) {
                            if ((from.matches(".*\\d.*") && (from.contains("/") || from.contains("-"))) || 
                                (to.matches(".*\\d.*") && (to.contains("/") || to.contains("-")))) {
                                ui.showError("Did you mean to use a format like 'd/M/yyyy HHmm'? Still adding as a string.");
                            }
                            tasks.addEvent(description, from, to);
                        }
                        Optional<Task> task = tasks.getTask(tasks.getTaskCount() - 1);
                        task.ifPresent(value -> ui.showAddedTask(value, tasks.getTaskCount()));
                        break;
                    }
                    case "delete": {
                        if (parts.length < 2) {
                            throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
                        }
                        try {
                            int taskIndex = Integer.parseInt(parts[1]) - 1;
                            Task deletedTask = tasks.deleteTask(taskIndex);
                            ui.showDeletedTask(deletedTask, tasks.getTaskCount());
                        } catch (NumberFormatException e) {
                            throw new JettVarkisException(JettVarkisException.ErrorType.INVALID_TASK_NUMBER);
                        } catch (IndexOutOfBoundsException e) {
                            throw new JettVarkisException(JettVarkisException.ErrorType.TASK_NOT_FOUND);
                        }
                        break;
                    }
                    default:
                        throw new JettVarkisException(JettVarkisException.ErrorType.UNKNOWN_COMMAND);
                }
                storage.save(tasks.getTasks());
            } catch (JettVarkisException e) {
                ui.showError(e.getMessage());
            }
            fullCommand = ui.readCommand();
        }
        ui.showGoodbye();
    }

    public static void main(String[] args) {
        new JettVarkis("../data/jettvarkis.txt").run();
    }
}

