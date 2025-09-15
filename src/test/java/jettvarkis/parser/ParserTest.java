package jettvarkis.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
import jettvarkis.exception.JettVarkisException;
import jettvarkis.task.Deadline;
import jettvarkis.task.Event;
import jettvarkis.task.Task;
import jettvarkis.task.Todo;

public class ParserTest {

    @Test
    public void testParseBasicCommands() throws JettVarkisException {
        assertTrue(Parser.parse("bye") instanceof ByeCommand);
        assertTrue(Parser.parse("list") instanceof ListCommand);
        assertTrue(Parser.parse("help") instanceof HelpCommand);
    }

    @Test
    public void testParseTodoCommand() throws JettVarkisException {
        Command command = Parser.parse("todo read book");
        assertTrue(command instanceof TodoCommand);
    }

    @Test
    public void testParseTodoCommandWithSpecialCharacters() throws JettVarkisException {
        Command command = Parser.parse("todo read book @#$%^&*()");
        assertTrue(command instanceof TodoCommand);
    }

    @Test
    public void testParseTodoCommandWithSqlInjectionString() throws JettVarkisException {
        Command command = Parser.parse("todo '; DROP TABLE tasks; --");
        assertTrue(command instanceof TodoCommand);
    }

    @Test
    public void testParseTodoCommandWithUnicodeCharacters() throws JettVarkisException {
        Command command = Parser.parse("todo 読書する 📚 τέλος");
        assertTrue(command instanceof TodoCommand);
    }

    @Test
    public void testParseEmptyTodoCommand() {
        assertThrows(JettVarkisException.class, () -> Parser.parse("todo"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("todo   "));
    }

    @Test
    public void testParseDeadlineCommand() throws JettVarkisException {
        Command command = Parser.parse("deadline return book /by 2025-08-27");
        assertTrue(command instanceof DeadlineCommand);
    }

    @Test
    public void testParseDeadlineCommandWithDateTime() throws JettVarkisException {
        Command command = Parser.parse("deadline submit report /by 2025-08-27 1400");
        assertTrue(command instanceof DeadlineCommand);
    }

    @Test
    public void testParseDeadlineCommandWithInvalidDate() throws JettVarkisException {
        Command command = Parser.parse("deadline task /by invalid-date");
        assertTrue(command instanceof DeadlineCommand);
    }

    @Test
    public void testParseDeadlineCommandWithSpecialCharacters() throws JettVarkisException {
        Command command = Parser.parse("deadline @#$%^&*() /by 2025-12-25");
        assertTrue(command instanceof DeadlineCommand);
    }

    @Test
    public void testParseEmptyDeadlineCommand() {
        assertThrows(JettVarkisException.class, () -> Parser.parse("deadline"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("deadline   "));
    }

    @Test
    public void testParseDeadlineCommandMissingBy() {
        assertThrows(JettVarkisException.class, () -> Parser.parse("deadline task"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("deadline task /by"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("deadline task /by   "));
    }

    @Test
    public void testParseDeadlineCommandMultipleBy() {
        assertThrows(JettVarkisException.class, () -> Parser.parse("deadline task /by 2025-08-27 /by 2025-08-28"));
    }

    @Test
    public void testParseEventCommand() throws JettVarkisException {
        Command command = Parser.parse("event meeting /from 2025-08-27 /to 2025-08-28");
        assertTrue(command instanceof EventCommand);
    }

    @Test
    public void testParseEventCommandWithInvalidDates() throws JettVarkisException {
        Command command = Parser.parse("event meeting /from invalid-start /to invalid-end");
        assertTrue(command instanceof EventCommand);
    }

    @Test
    public void testParseEventCommandWithSpecialCharacters() throws JettVarkisException {
        Command command = Parser.parse("event @#$%^&*()meeting /from 2025-08-27 /to 2025-08-28");
        assertTrue(command instanceof EventCommand);
    }

    @Test
    public void testParseEventCommandInvalidTimeOrder() {
        assertThrows(JettVarkisException.class, () -> 
            Parser.parse("event meeting /from 2025-08-27 1600 /to 2025-08-27 1400"));
    }

    @Test
    public void testParseEventCommandMissingParts() {
        assertThrows(JettVarkisException.class, () -> Parser.parse("event"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("event meeting"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("event meeting /from"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("event meeting /from 2025-08-27"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("event meeting /to 2025-08-27"));
    }

    @Test
    public void testParseMarkCommand() throws JettVarkisException {
        Command command = Parser.parse("mark 1");
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void testParseMarkCommandInvalidNumber() {
        assertThrows(JettVarkisException.class, () -> Parser.parse("mark abc"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("mark"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("mark   "));
    }

    @Test
    public void testParseUnmarkCommand() throws JettVarkisException {
        Command command = Parser.parse("unmark 1");
        assertTrue(command instanceof UnmarkCommand);
    }

    @Test
    public void testParseDeleteCommand() throws JettVarkisException {
        Command command = Parser.parse("delete 1");
        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void testParseFindCommand() throws JettVarkisException {
        Command command = Parser.parse("find book");
        assertTrue(command instanceof FindCommand);
    }

    @Test
    public void testParseFindCommandWithSpecialCharacters() throws JettVarkisException {
        Command command = Parser.parse("find @#$%^&*()");
        assertTrue(command instanceof FindCommand);
    }

    @Test
    public void testParseFindCommandEmpty() {
        assertThrows(JettVarkisException.class, () -> Parser.parse("find"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("find   "));
    }

    @Test
    public void testParseUnknownCommand() {
        assertThrows(JettVarkisException.class, () -> Parser.parse("unknown"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("xyz"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("@#$%"));
    }

    @Test
    public void testParseEmptyInput() {
        assertThrows(JettVarkisException.class, () -> Parser.parse(""));
        assertThrows(JettVarkisException.class, () -> Parser.parse("   "));
    }

    @Test
    public void testParseNullInput() {
        assertThrows(AssertionError.class, () -> Parser.parse(null));
    }

    @Test
    public void testParseFileLineTodo() throws JettVarkisException {
        Task task = Parser.parseFileLine("T | 0 | read book");
        assertTrue(task instanceof Todo);
        assertEquals("read book", task.getDescription());
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void testParseFileLineTodoDone() throws JettVarkisException {
        Task task = Parser.parseFileLine("T | 1 | read book");
        assertTrue(task instanceof Todo);
        assertEquals("read book", task.getDescription());
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void testParseFileLineDeadline() throws JettVarkisException {
        Task task = Parser.parseFileLine("D | 0 | return book | 2025-08-27");
        assertTrue(task instanceof Deadline);
        assertEquals("return book", task.getDescription());
    }

    @Test
    public void testParseFileLineEvent() throws JettVarkisException {
        Task task = Parser.parseFileLine("E | 0 | meeting | 2025-08-27 1400 | 2025-08-27 1600");
        assertTrue(task instanceof Event);
        assertEquals("meeting", task.getDescription());
    }

    @Test
    public void testParseFileLineCorrupted() {
        assertThrows(AssertionError.class, () -> Parser.parseFileLine("Invalid format"));
        assertThrows(AssertionError.class, () -> Parser.parseFileLine("T | 0"));
        assertThrows(JettVarkisException.class, () -> Parser.parseFileLine("D | 0 | task"));
        assertThrows(JettVarkisException.class, () -> Parser.parseFileLine("E | 0 | task | from"));
    }

    @Test
    public void testParseFileLineWithSpecialCharacters() throws JettVarkisException {
        Task task = Parser.parseFileLine("T | 0 | @#$%^&*() task");
        assertTrue(task instanceof Todo);
        assertEquals("@#$%^&*() task", task.getDescription());
    }

    @Test
    public void testParseFileLineWithUnicodeCharacters() throws JettVarkisException {
        Task task = Parser.parseFileLine("T | 0 | 読書する 📚");
        assertTrue(task instanceof Todo);
        assertEquals("読書する 📚", task.getDescription());
    }

    @Test
    public void testParseFileLineNull() {
        assertThrows(AssertionError.class, () -> Parser.parseFileLine(null));
    }

    @Test
    public void testParseFileLineEmpty() {
        assertThrows(AssertionError.class, () -> Parser.parseFileLine(""));
        assertThrows(AssertionError.class, () -> Parser.parseFileLine("   "));
    }

    @Test
    public void testParseWithExtremelyLongInput() throws JettVarkisException {
        StringBuilder longDescription = new StringBuilder("todo ");
        for (int i = 0; i < 10000; i++) {
            longDescription.append("a");
        }
        Command command = Parser.parse(longDescription.toString());
        assertTrue(command instanceof TodoCommand);
    }

    @Test
    public void testParseWithNewlineCharacters() throws JettVarkisException {
        Command command = Parser.parse("todo task\nwith\nnewlines");
        assertTrue(command instanceof TodoCommand);
    }

    @Test
    public void testParseWithTabCharacters() throws JettVarkisException {
        Command command = Parser.parse("todo task\twith\ttabs");
        assertTrue(command instanceof TodoCommand);
    }

    @Test
    public void testParseCommandsCaseInsensitive() {
        // Commands should be case sensitive in this implementation
        assertThrows(JettVarkisException.class, () -> Parser.parse("TODO task"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("BYE"));
        assertThrows(JettVarkisException.class, () -> Parser.parse("LIST"));
    }
}