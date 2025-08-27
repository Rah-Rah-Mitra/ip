import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public static Command parse(String fullCommand) throws JettVarkisException {
        String[] parts = fullCommand.split(" ", 2);
        String command = parts[0];
        String content = parts.length > 1 ? parts[1] : null;

        switch (command) {
        case "bye":
            return new ByeCommand();
        case "list":
            return new ListCommand();
        case "mark":
            return parseMarkCommand(content);
        case "unmark":
            return parseUnmarkCommand(content);
        case "todo":
            return parseTodoCommand(content);
        case "deadline":
            return parseDeadlineCommand(content);
        case "event":
            return parseEventCommand(content);
        case "delete":
            return parseDeleteCommand(content);
        default:
            throw new JettVarkisException(JettVarkisException.ErrorType.UNKNOWN_COMMAND);
        }
    }

    private static MarkCommand parseMarkCommand(String content) throws JettVarkisException {
        if (content == null) {
            throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
        }
        try {
            int taskIndex = Integer.parseInt(content) - 1;
            return new MarkCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.INVALID_TASK_NUMBER);
        }
    }

    private static UnmarkCommand parseUnmarkCommand(String content) throws JettVarkisException {
        if (content == null) {
            throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
        }
        try {
            int taskIndex = Integer.parseInt(content) - 1;
            return new UnmarkCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.INVALID_TASK_NUMBER);
        }
    }

    private static TodoCommand parseTodoCommand(String content) throws JettVarkisException {
        if (content == null) {
            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION);
        }
        return new TodoCommand(content);
    }

    private static DeadlineCommand parseDeadlineCommand(String content) throws JettVarkisException {
        if (content == null) {
            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_DEADLINE_DESCRIPTION);
        }
        String[] deadlineParts = content.split(" /by ");
        if (deadlineParts.length < 2) {
            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_DEADLINE_BY);
        }
        String description = deadlineParts[0];
        String by = deadlineParts[1];
        try {
            LocalDateTime byDateTime = parseDateTime(by);
            return new DeadlineCommand(description, byDateTime);
        } catch (DateTimeParseException e) {
            boolean showWarning = by.matches(".*\\d.*") && (by.contains("/") || by.contains("-"));
            return new DeadlineCommand(description, by, showWarning);
        }
    }

    private static EventCommand parseEventCommand(String content) throws JettVarkisException {
        if (content == null) {
            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_EVENT_DESCRIPTION);
        }
        String[] eventParts = content.split(" /from ");
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
            return new EventCommand(description, fromDateTime, toDateTime);
        } catch (DateTimeParseException e) {
            boolean showWarning = (from.matches(".*\\d.*") && (from.contains("/") || from.contains("-"))) ||
                    (to.matches(".*\\d.*") && (to.contains("/") || to.contains("-")));
            return new EventCommand(description, from, to, showWarning);
        }
    }

    private static DeleteCommand parseDeleteCommand(String content) throws JettVarkisException {
        if (content == null) {
            throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
        }
        try {
            int taskIndex = Integer.parseInt(content) - 1;
            return new DeleteCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.INVALID_TASK_NUMBER);
        }
    }

    public static Task parseFileLine(String line) throws JettVarkisException {
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
            String byString = parts[3];
            try {
                LocalDateTime byDateTime = LocalDateTime.parse(byString);
                task = new Deadline(description, byDateTime);
            } catch (DateTimeParseException e) {
                task = new Deadline(description, byString);
            }
            break;
        case "E":
            if (parts.length < 5) {
                throw new JettVarkisException(JettVarkisException.ErrorType.CORRUPTED_DATA_ERROR);
            }
            String fromString = parts[3];
            String toString = parts[4];
            LocalDateTime fromDateTime = null;
            LocalDateTime toDateTime = null;
            try {
                fromDateTime = LocalDateTime.parse(fromString);
            } catch (DateTimeParseException e) {
                // Keep fromDateTime as null, use original string
            }
            try {
                toDateTime = LocalDateTime.parse(toString);
            } catch (DateTimeParseException e) {
                // Keep toDateTime as null, use original string
            }

            if (fromDateTime != null && toDateTime != null) {
                task = new Event(description, fromDateTime, toDateTime);
            } else {
                task = new Event(description, fromString, toString);
            }
            break;
        default:
            throw new JettVarkisException(JettVarkisException.ErrorType.CORRUPTED_DATA_ERROR);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private static LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (DateTimeFormatter formatter : formatters) {
            try {
                if (!formatter.toString().contains("HHmm")) {
                    return java.time.LocalDate.parse(dateTimeString, formatter).atStartOfDay();
                }
                return LocalDateTime.parse(dateTimeString, formatter);
            } catch (DateTimeParseException e) {
                // Continue to try the next format
            }
        }
        throw new DateTimeParseException("Unable to parse date-time: " + dateTimeString, dateTimeString, 0);
    }
}