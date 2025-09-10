package jettvarkis.exception;

/**
 * Represents an exception specific to the JettVarkis application.
 * This exception is used to handle various error conditions within the
 * application.
 */
public class JettVarkisException extends Exception {

    /**
     * Enumerates the types of errors that can occur in the JettVarkis application.
     * Each error type has an associated message.
     */
    public enum ErrorType {
        EMPTY_TODO_DESCRIPTION("The description of a todo cannot be empty."),
        EMPTY_DEADLINE_DESCRIPTION(
                "The description of a deadline cannot be empty."),
        EMPTY_DEADLINE_BY(
                "The date/time of a deadline cannot be empty."),
        EMPTY_EVENT_DESCRIPTION(
                "The description of an event cannot be empty."),
        EMPTY_EVENT_FROM(
                "The start time of an event cannot be empty."),
        EMPTY_EVENT_TO(
                "The end time of an event cannot be empty."),
        MISSING_TASK_NUMBER(
                "The task number is missing."),
        INVALID_TASK_NUMBER(
                "The task number provided is invalid."),
        TASK_NOT_FOUND(
                "Task not found."),
        UNKNOWN_COMMAND(
                "I'm sorry, but I don't know what that means :-("),
        EMPTY_FIND_KEYWORD(
                "The keyword for find command cannot be empty."),
        FILE_OPERATION_ERROR(
                "Error during file operation."),
        CORRUPTED_DATA_ERROR(
                "Data file is corrupted."),
        EMPTY_TRIVIA_LIST("The current trivia list is empty."),
        INVALID_TRIVIA_INDEX("The trivia item number provided is invalid."),
        NOT_IN_QUIZ_MODE("You are not currently in quiz mode."),
        EMPTY_TRIVIA_CATEGORY_NAME("Trivia category name cannot be empty."),
        TRIVIA_CATEGORY_ALREADY_EXISTS("Trivia category already exists."),
        TRIVIA_CATEGORY_NOT_FOUND("Trivia category not found.");

        private final String message;

        /**
         * Constructs an ErrorType with the given message.
         *
         * @param message
         *            The error message.
         */
        ErrorType(String message) {
            this.message = message;
        }

        /**
         * Returns the error message associated with this ErrorType.
         *
         * @return The error message.
         */
        public String getMessage() {
            return message;
        }
    }

    private final ErrorType errorType;
    private String customMessage;

    /**
     * Constructs a new JettVarkisException with the specified error type.
     * The exception's message is derived from the ErrorType.
     *
     * @param errorType
     *            The type of error that occurred.
     */
    public JettVarkisException(ErrorType errorType) {
        super(errorType.getMessage());
        assert errorType != null;
        this.errorType = errorType;
    }

    /**
     * Constructs a new JettVarkisException with the specified error type and a custom message.
     *
     * @param errorType The type of error that occurred.
     * @param customMessage A custom message to append or use instead of the default.
     */
    public JettVarkisException(ErrorType errorType, String customMessage) {
        super(errorType.getMessage() + (customMessage != null && !customMessage.isEmpty() ? ": " + customMessage : ""));
        assert errorType != null;
        this.errorType = errorType;
        this.customMessage = customMessage;
    }

    /**
     * Returns the type of error associated with this exception.
     *
     * @return The ErrorType of the exception.
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    @Override
    public String getMessage() {
        if (customMessage != null && !customMessage.isEmpty()) {
            return errorType.getMessage() + ": " + customMessage;
        } else {
            return errorType.getMessage();
        }
    }
}
