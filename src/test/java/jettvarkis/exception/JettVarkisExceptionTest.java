package jettvarkis.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JettVarkisExceptionTest {

    @Test
    public void testConstructorWithErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION);
        assertEquals(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION, exception.getErrorType());
        assertEquals(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION.getMessage(), exception.getMessage());
    }

    @Test
    public void testConstructorWithErrorTypeAndCustomMessage() {
        String customMessage = "Additional error details";
        JettVarkisException exception = new JettVarkisException(
            JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION, customMessage);
        assertEquals(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION, exception.getErrorType());
        assertTrue(exception.getMessage().contains(customMessage));
        assertTrue(exception.getMessage().contains(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION.getMessage()));
    }

    @Test
    public void testConstructorWithErrorTypeAndEmptyCustomMessage() {
        JettVarkisException exception = new JettVarkisException(
            JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION, "");
        assertEquals(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION, exception.getErrorType());
        assertEquals(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION.getMessage(), exception.getMessage());
    }

    @Test
    public void testConstructorWithErrorTypeAndNullCustomMessage() {
        JettVarkisException exception = new JettVarkisException(
            JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION, null);
        assertEquals(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION, exception.getErrorType());
        assertEquals(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION.getMessage(), exception.getMessage());
    }

    @Test
    public void testAllErrorTypesHaveMessages() {
        for (JettVarkisException.ErrorType errorType : JettVarkisException.ErrorType.values()) {
            assertNotNull(errorType.getMessage());
            assertTrue(errorType.getMessage().length() > 0);
        }
    }

    @Test
    public void testEmptyTodoDescriptionErrorType() {
        JettVarkisException.ErrorType errorType = JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION;
        assertTrue(errorType.getMessage().contains("Usage: todo"));
    }

    @Test
    public void testEmptyDeadlineDescriptionErrorType() {
        JettVarkisException.ErrorType errorType = JettVarkisException.ErrorType.EMPTY_DEADLINE_DESCRIPTION;
        assertTrue(errorType.getMessage().contains("Usage: deadline"));
    }

    @Test
    public void testEmptyEventDescriptionErrorType() {
        JettVarkisException.ErrorType errorType = JettVarkisException.ErrorType.EMPTY_EVENT_DESCRIPTION;
        assertTrue(errorType.getMessage().contains("Usage: event"));
    }

    @Test
    public void testFileOperationErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.FILE_OPERATION_ERROR);
        assertTrue(exception.getMessage().contains("scrolls"));
    }

    @Test
    public void testCorruptedDataErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.CORRUPTED_DATA_ERROR);
        assertTrue(exception.getMessage().contains("worn"));
    }

    @Test
    public void testUnknownCommandErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.UNKNOWN_COMMAND);
        assertTrue(exception.getMessage().contains("help"));
    }

    @Test
    public void testInvalidTaskNumberErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.INVALID_TASK_NUMBER);
        assertTrue(exception.getMessage().contains("list"));
    }

    @Test
    public void testEmptyFindKeywordErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.EMPTY_FIND_KEYWORD);
        assertTrue(exception.getMessage().contains("Usage: find"));
    }

    @Test
    public void testTriviaRelatedErrorTypes() {
        JettVarkisException.ErrorType[] triviaErrorTypes = {
            JettVarkisException.ErrorType.EMPTY_TRIVIA_LIST,
            JettVarkisException.ErrorType.INVALID_TRIVIA_INDEX,
            JettVarkisException.ErrorType.NOT_IN_QUIZ_MODE,
            JettVarkisException.ErrorType.EMPTY_TRIVIA_CATEGORY_NAME,
            JettVarkisException.ErrorType.TRIVIA_CATEGORY_ALREADY_EXISTS,
            JettVarkisException.ErrorType.TRIVIA_CATEGORY_NOT_FOUND
        };

        for (JettVarkisException.ErrorType errorType : triviaErrorTypes) {
            JettVarkisException exception = new JettVarkisException(errorType);
            assertNotNull(exception.getMessage());
            assertEquals(errorType, exception.getErrorType());
        }
    }

    @Test
    public void testMultipleDeadlineByErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.MULTIPLE_DEADLINE_BY);
        assertTrue(exception.getMessage().contains("/by"));
        assertTrue(exception.getMessage().contains("once"));
    }

    @Test
    public void testMultipleEventFromErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.MULTIPLE_EVENT_FROM);
        assertTrue(exception.getMessage().contains("/from"));
        assertTrue(exception.getMessage().contains("once"));
    }

    @Test
    public void testMultipleEventToErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.MULTIPLE_EVENT_TO);
        assertTrue(exception.getMessage().contains("/to"));
        assertTrue(exception.getMessage().contains("once"));
    }

    @Test
    public void testInvalidEventTimesErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.INVALID_EVENT_TIMES);
        assertTrue(exception.getMessage().contains("from"));
        assertTrue(exception.getMessage().contains("to"));
    }

    @Test
    public void testFileIsDirectoryErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.FILE_IS_DIRECTORY);
        assertTrue(exception.getMessage().contains("directory"));
    }

    @Test
    public void testFileReadDeniedErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.FILE_READ_DENIED);
        assertTrue(exception.getMessage().contains("permission"));
        assertTrue(exception.getMessage().contains("read"));
    }

    @Test
    public void testFileWriteDeniedErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.FILE_WRITE_DENIED);
        assertTrue(exception.getMessage().contains("permission"));
        assertTrue(exception.getMessage().contains("write"));
    }

    @Test
    public void testDuplicateTaskErrorType() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.DUPLICATE_TASK);
        assertTrue(exception.getMessage().contains("exists"));
    }

    @Test
    public void testCustomMessageWithSpecialCharacters() {
        String customMessage = "@#$%^&*(){}[]|\\:;\"'<>,.?/~`!";
        JettVarkisException exception = new JettVarkisException(
            JettVarkisException.ErrorType.UNKNOWN_COMMAND, customMessage);
        assertTrue(exception.getMessage().contains(customMessage));
    }

    @Test
    public void testCustomMessageWithUnicodeCharacters() {
        String customMessage = "エラーメッセージ 🚫 τέλος";
        JettVarkisException exception = new JettVarkisException(
            JettVarkisException.ErrorType.UNKNOWN_COMMAND, customMessage);
        assertTrue(exception.getMessage().contains(customMessage));
    }

    @Test
    public void testCustomMessageWithLongString() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longMessage.append("a");
        }
        JettVarkisException exception = new JettVarkisException(
            JettVarkisException.ErrorType.UNKNOWN_COMMAND, longMessage.toString());
        assertTrue(exception.getMessage().contains(longMessage.toString()));
    }

    @Test
    public void testExceptionInheritance() {
        JettVarkisException exception = new JettVarkisException(JettVarkisException.ErrorType.UNKNOWN_COMMAND);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }
}