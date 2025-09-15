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
        EMPTY_TODO_DESCRIPTION("A task without purpose... even I cannot inscribe emptiness into the grimoire."),
        EMPTY_DEADLINE_DESCRIPTION(
                "The chronicles require substance. A deadline without meaning holds no power over time."),
        EMPTY_DEADLINE_BY(
                "Time flows, but where does this deadline anchor itself? The temporal binding is incomplete."),
        EMPTY_EVENT_DESCRIPTION(
                "Events are moments in time's tapestry. Without description, they fade before they begin."),
        EMPTY_EVENT_FROM(
                "When does this moment begin? Even I need a starting point in time's endless flow."),
        EMPTY_EVENT_TO(
                "All things must end, yet this event lacks conclusion. Time demands boundaries."),
        MISSING_TASK_NUMBER(
                "Which thread in the tapestry of tasks do you wish to touch? A number is required."),
        INVALID_TASK_NUMBER(
                "This number points to nothing in the void. Choose from what exists in your grimoire."),
        TASK_NOT_FOUND(
                "The mists of time obscure this task from view. It dwells not within your collection."),
        UNKNOWN_COMMAND(
                "These words echo strangely in the halls of memory. I know not this incantation."),
        EMPTY_FIND_KEYWORD(
                "To search the depths of memory, one must provide a key. What shall we seek?"),
        FILE_OPERATION_ERROR(
                "The scrolls resist our touch. Some barrier prevents the preservation of knowledge."),
        CORRUPTED_DATA_ERROR(
                "Time has worn these records thin. The data speaks in broken whispers."),
        EMPTY_TRIVIA_LIST("The archive stands empty, holding no wisdom to share in this moment."),
        INVALID_TRIVIA_INDEX("This path leads to naught. Choose from the knowledge that exists."),
        NOT_IN_QUIZ_MODE("No trial is in progress. Perhaps you seek to begin one?"),
        EMPTY_TRIVIA_CATEGORY_NAME("A realm of knowledge requires a name to exist. What shall it be called?"),
        TRIVIA_CATEGORY_ALREADY_EXISTS("This realm already manifests in the archives. Choose another name."),
        TRIVIA_CATEGORY_NOT_FOUND("Such a realm of knowledge exists not in these archives.");

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
