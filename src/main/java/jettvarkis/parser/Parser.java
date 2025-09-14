package jettvarkis.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import jettvarkis.command.ByeCommand;
import jettvarkis.command.Command;
import jettvarkis.command.DeadlineCommand;
import jettvarkis.command.DeleteCommand;
import jettvarkis.command.EventCommand;
import jettvarkis.command.FindCommand;
import jettvarkis.command.HelpCommand;
import jettvarkis.command.ListCommand;
import jettvarkis.command.MarkCommand;
import jettvarkis.command.TodoCommand;
import jettvarkis.command.UnmarkCommand;
import jettvarkis.command.trivia.TriviaAddCommand;
import jettvarkis.command.trivia.TriviaCreateCommand;
import jettvarkis.command.trivia.TriviaDeleteCommand;
import jettvarkis.command.trivia.TriviaHelpCommand;
import jettvarkis.command.trivia.TriviaListCommand;
import jettvarkis.command.trivia.TriviaSelectCommand;
import jettvarkis.command.trivia.TriviaStartCommand;
import jettvarkis.command.trivia.TriviaStopCommand;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.task.Deadline;
import jettvarkis.task.Event;
import jettvarkis.task.Task;
import jettvarkis.task.Todo;

/**
 * Handles parsing of user commands and file input.
 */
public class Parser {

    private static final int FILE_TYPE_INDEX = 0;
    private static final int FILE_IS_DONE_INDEX = 1;
    private static final int FILE_DESCRIPTION_INDEX = 2;
    private static final int FILE_DEADLINE_BY_INDEX = 3;
    private static final int FILE_EVENT_FROM_INDEX = 3;
    private static final int FILE_EVENT_TO_INDEX = 4;

    private static final int MIN_DEADLINE_PARTS = 4;
    private static final int MIN_EVENT_PARTS = 5;

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
        assert fullCommand != null;
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
        case "trivia":
            return parseTriviaCommand(content);
        case "help": // Add this case
            return new HelpCommand(); // Return the new HelpCommand
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
        if (content == null || content.trim().isEmpty()) {
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
        if (content == null || content.trim().isEmpty()) {
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
            boolean hasDigits = by.matches(".*\\d.*") && (by.contains("/") || by.contains("-"));
            boolean hasSlashOrHyphen = by.contains("/") || by.contains("-");
            boolean shouldShowWarning = hasDigits && hasSlashOrHyphen;
            return new DeadlineCommand(description, by, shouldShowWarning);
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
        if (content == null || content.trim().isEmpty()) {
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
            boolean fromHasDigits = from.matches(".*\\d.*") && (from.contains("/") || from.contains("-"));
            boolean fromHasSlashOrHyphen = from.contains("/") || from.contains("-");
            boolean toHasDigits = to.matches(".*\\d.*") && (to.contains("/") || to.contains("-"));
            boolean toHasSlashOrHyphen = to.contains("/") || to.contains("-");
            boolean shouldShowWarning = (fromHasDigits && fromHasSlashOrHyphen) || (toHasDigits && toHasSlashOrHyphen);
            return new EventCommand(description, from, to, shouldShowWarning);
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
        String[] indexStrings = content.split("\\s+");
        try {
            int[] taskIndices = Arrays.stream(indexStrings)
                    .mapToInt(s -> Integer.parseInt(s) - 1)
                    .toArray();
            return new DeleteCommand(taskIndices);
        } catch (NumberFormatException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.INVALID_TASK_NUMBER);
        }
    }

    private static Command parseTriviaCommand(String content) throws JettVarkisException {
        if (content == null || content.trim().isEmpty()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.UNKNOWN_COMMAND); // Or a more specific trivia
        }

        String[] parts = content.trim().split(" ", 2);
        String subCommand = parts[0];
        String subContent = parts.length > 1 ? parts[1] : null;

        switch (subCommand) {
        case "list":
            return parseTriviaList(subContent);
        case "add":
            return parseTriviaAdd(subContent);
        case "select":
            return parseTriviaSelect(subContent);
        case "start":
            return new TriviaStartCommand();
        case "stop":
            return new TriviaStopCommand();
        case "delete":
            return parseTriviaDelete(subContent);
        case "create":
            return parseTriviaCreate(subContent);
        case "help":
            return new TriviaHelpCommand();
        default:
            throw new JettVarkisException(JettVarkisException.ErrorType.UNKNOWN_COMMAND); // Or a more specific trivia
        }
    }

    private static TriviaListCommand parseTriviaList(String subContent) {
        boolean shouldShowAll = subContent != null && subContent.trim().equals("/l");
        return new TriviaListCommand(shouldShowAll);
    }

    private static TriviaAddCommand parseTriviaAdd(String subContent) throws JettVarkisException {
        if (subContent == null || subContent.trim().isEmpty()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.UNKNOWN_COMMAND); // Or more specific
        }
        String[] triviaParts = subContent.split(" \\| ");
        if (triviaParts.length < 2) {
            throw new JettVarkisException(JettVarkisException.ErrorType.UNKNOWN_COMMAND); // Or more specific
        }
        return new TriviaAddCommand(triviaParts[0], triviaParts[1]);
    }

    private static TriviaSelectCommand parseTriviaSelect(String subContent) throws JettVarkisException {
        if (subContent == null || subContent.trim().isEmpty()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.UNKNOWN_COMMAND); // Or more specific
        }
        return new TriviaSelectCommand(subContent);
    }

    private static TriviaDeleteCommand parseTriviaDelete(String subContent) throws JettVarkisException {
        if (subContent == null || subContent.trim().isEmpty()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
        }
        if (subContent.contains("/c")) {
            String categoryToDelete = subContent.replace("/c", "").trim();
            if (categoryToDelete.isEmpty()) {
                throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_TRIVIA_CATEGORY_NAME);
            }
            return new TriviaDeleteCommand(categoryToDelete);
        } else {
            try {
                int index = Integer.parseInt(subContent.trim()) - 1;
                return new TriviaDeleteCommand(index);
            } catch (NumberFormatException e) {
                throw new JettVarkisException(JettVarkisException.ErrorType.INVALID_TASK_NUMBER);
            }
        }
    }

    private static TriviaCreateCommand parseTriviaCreate(String subContent) throws JettVarkisException {
        if (subContent == null || subContent.trim().isEmpty()) {
            throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_TRIVIA_CATEGORY_NAME);
        }
        return new TriviaCreateCommand(subContent);
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
        assert line != null;
        String[] parts = line.split(" \\| ");
        assert parts.length >= 3 : "File line must have at least 3 parts: " + line;
        String type = parts[FILE_TYPE_INDEX];
        boolean isDone = parts[FILE_IS_DONE_INDEX].equals("1");
        String description = parts[FILE_DESCRIPTION_INDEX];

        Task task;
        switch (type) {
        case "T":
            task = parseTodoFromFile(description);
            break;
        case "D":
            task = parseDeadlineFromFile(description, parts);
            break;
        case "E":
            task = parseEventFromFile(description, parts);
            break;
        default:
            throw new JettVarkisException(JettVarkisException.ErrorType.CORRUPTED_DATA_ERROR);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private static Task parseTodoFromFile(String description) {
        return new Todo(description);
    }

    private static Task parseDeadlineFromFile(String description, String[] parts) throws JettVarkisException {
        if (parts.length < MIN_DEADLINE_PARTS) {
            throw new JettVarkisException(JettVarkisException.ErrorType.CORRUPTED_DATA_ERROR);
        }
        String byString = parts[FILE_DEADLINE_BY_INDEX];
        Optional<LocalDateTime> byDateTime = parseDateTimeSafely(byString);
        if (byDateTime.isPresent()) {
            return new Deadline(description, byDateTime.get());
        } else {
            return new Deadline(description, byString);
        }
    }

    private static Task parseEventFromFile(String description, String[] parts) throws JettVarkisException {
        if (parts.length < MIN_EVENT_PARTS) {
            throw new JettVarkisException(JettVarkisException.ErrorType.CORRUPTED_DATA_ERROR);
        }
        String fromString = parts[FILE_EVENT_FROM_INDEX];
        String toString = parts[FILE_EVENT_TO_INDEX];

        Optional<LocalDateTime> fromDateTime = parseDateTimeSafely(fromString);
        Optional<LocalDateTime> toDateTime = parseDateTimeSafely(toString);

        if (fromDateTime.isPresent() && toDateTime.isPresent()) {
            return new Event(description, fromDateTime.get(), toDateTime.get());
        } else {
            return new Event(description, fromString, toString);
        }
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
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            throw new DateTimeParseException("Date-time string cannot be empty or null.", dateTimeString, 0);
        }
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

    /**
     * Safely parses a date-time string into a LocalDateTime object.
     * Returns an Optional.empty() if parsing fails.
     *
     * @param dateTimeString
     *            The date-time string to parse.
     * @return An Optional containing the LocalDateTime object if successful,
     *         otherwise an empty Optional.
     */
    private static Optional<LocalDateTime> parseDateTimeSafely(String dateTimeString) {
        try {
            return Optional.of(parseDateTime(dateTimeString));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
}
