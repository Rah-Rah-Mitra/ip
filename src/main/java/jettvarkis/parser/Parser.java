package jettvarkis.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import jettvarkis.command.ByeCommand;
import jettvarkis.command.Command;
import jettvarkis.command.DeadlineCommand;
import jettvarkis.command.DeleteCommand;
import jettvarkis.command.EventCommand;
import jettvarkis.command.FindCommand;
import jettvarkis.command.ListCommand;
import jettvarkis.command.MarkCommand;
import jettvarkis.command.TodoCommand;
import jettvarkis.command.UnmarkCommand;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.task.Deadline;
import jettvarkis.task.Event;
import jettvarkis.task.Task;
import jettvarkis.task.Todo;

/**
 * Handles parsing of user commands and file input.
 */
public class Parser {

    /**
     * Parses the full command string from the user and returns the corresponding
     * Command object.
     *
     * @param fullCommand
     *            The full command string entered by the user.
     * @return The Command object corresponding to the parsed command.
     * @throws JettVarkisException
     *             If the command is unknown or invalid.
     */
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
        case "find":
            if (content == null || content.trim().isEmpty()) {
                throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_FIND_KEYWORD);
            }
            return new FindCommand(content.trim());
        default:
            throw new JettVarkisException(JettVarkisException.ErrorType.UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses the content for a "mark" command.
     *
     * @param content
     *            The content part of the command, expected to be the task number.
     * @return A MarkCommand object.
     * @throws JettVarkisException
     *             If the task number is missing or invalid.
     */
    private static MarkCommand parseMarkCommand(String content) throws JettVarkisException {
        if (content == null || content.trim().isEmpty()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
        }
        String[] indexStrings = content.split("\\s+");
        try {
            int[] taskIndices = Arrays.stream(indexStrings)
                    .mapToInt(s -> Integer.parseInt(s) - 1)
                    .toArray();
            return new MarkCommand(taskIndices);
        } catch (NumberFormatException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.INVALID_TASK_NUMBER);
        }
    }

    /**
     * Parses the content for an "unmark" command.
     *
     * @param content
     *            The content part of the command, expected to be the task number.
     * @return An UnmarkCommand object.
     * @throws JettVarkisException
     *             If the task number is missing or invalid.
     */
    private static UnmarkCommand parseUnmarkCommand(String content) throws JettVarkisException {
        if (content == null || content.trim().isEmpty()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
        }
        String[] indexStrings = content.split("\\s+");
        try {
            int[] taskIndices = Arrays.stream(indexStrings)
                    .mapToInt(s -> Integer.parseInt(s) - 1)
                    .toArray();
            return new UnmarkCommand(taskIndices);
        } catch (NumberFormatException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.INVALID_TASK_NUMBER);
        }
    }

    /**
     * Parses the content for a "todo" command.
     *
     * @param content
     *            The content part of the command, expected to be the todo
     *            description.
     * @return A TodoCommand object.
     * @throws JettVarkisException
     *             If the todo description is empty.
     */
    private static TodoCommand parseTodoCommand(String content) throws JettVarkisException {
        if (content == null) {
            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION);
        }
        return new TodoCommand(content);
    }

    /**
     * Parses the content for a "deadline" command.
     *
     * @param content
     *            The content part of the command, expected to be "description /by
     *            datetime".
     * @return A DeadlineCommand object.
     * @throws JettVarkisException
     *             If the description or due date is missing or invalid.
     */
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

    /**
     * Parses the content for an "event" command.
     *
     * @param content
     *            The content part of the command, expected to be "description /from
     *            datetime /to datetime".
     * @return An EventCommand object.
     * @throws JettVarkisException
     *             If the description, from, or to dates are missing or invalid.
     */
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
            boolean showWarning = (from.matches(".*\\d.*") && (from.contains("/") || from.contains("-")))
                    || (to.matches(".*\\d.*") && (to.contains("/") || to.contains("-")));
            return new EventCommand(description, from, to, showWarning);
        }
    }

    /**
     * Parses the content for a "delete" command.
     *
     * @param content
     *            The content part of the command, expected to be the task number.
     * @return A DeleteCommand object.
     * @throws JettVarkisException
     *             If the task number is missing or invalid.
     */
    private static DeleteCommand parseDeleteCommand(String content) throws JettVarkisException {
        if (content == null || content.trim().isEmpty()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
        }
        String[] indexStrings = content.split("\s+");
        try {
            int[] taskIndices = Arrays.stream(indexStrings)
                    .mapToInt(s -> Integer.parseInt(s) - 1)
                    .toArray();
            return new DeleteCommand(taskIndices);
        } catch (NumberFormatException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.INVALID_TASK_NUMBER);
        }
    }

    /**
     * Parses a line from the task file and returns the corresponding Task object.
     *
     * @param line
     *            The line read from the task file.
     * @return A Task object parsed from the line.
     * @throws JettVarkisException
     *             If the file line is corrupted or in an unknown format.
     */
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

    /**
     * Parses a date-time string into a LocalDateTime object using various
     * predefined formats.
     *
     * @param dateTimeString
     *            The date-time string to parse.
     * @return A LocalDateTime object parsed from the string.
     * @throws DateTimeParseException
     *             If the string cannot be parsed by any of the supported formats.
     */
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
