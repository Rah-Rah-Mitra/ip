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
                "Data file is corrupted.");

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

    /**
     * Constructs a new JettVarkisException with the specified error type.
     * The exception's message is derived from the ErrorType.
     *
     * @param errorType
     *            The type of error that occurred.
     */
    public JettVarkisException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    /**
     * Returns the type of error associated with this exception.
     *
     * @return The ErrorType of the exception.
     */
    public ErrorType getErrorType() {
        return errorType;
    }
}
