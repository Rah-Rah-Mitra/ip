package jettvarkis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import jettvarkis.exception.JettVarkisException;
import jettvarkis.task.Deadline;
import jettvarkis.task.Event;
import jettvarkis.task.Task;

public class TaskListTest {

    @Test
    public void testAddTodo() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addTodo("read book");
        assertEquals(1, taskList.getTaskCount());
        assertEquals("[T][ ] read book", taskList.getTask(0).get().toString());
    }

    @Test
    public void testAddDeadline() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addDeadline("return book", "2025-08-27");
        assertEquals(1, taskList.getTaskCount());
        assertTrue(taskList.getTask(0).get() instanceof Deadline);
        assertEquals("[D][ ] return book (by: 2025-08-27)", taskList.getTask(0).get().toString());
    }

    @Test
    public void testAddEvent() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addEvent("project meeting", "2025-08-27 1400", "2025-08-27 1600");
        assertEquals(1, taskList.getTaskCount());
        assertTrue(taskList.getTask(0).get() instanceof Event);
        assertEquals("[E][ ] project meeting (from: 2025-08-27 1400 to: 2025-08-27 1600)",
                taskList.getTask(0).get().toString());
    }

    @Test
    public void testGetTask() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addTodo("test task");
        Optional<Task> task = taskList.getTask(0);
        assertTrue(task.isPresent());
        assertEquals("[T][ ] test task", task.get().toString());

        Optional<Task> invalidTask = taskList.getTask(1);
        assertFalse(invalidTask.isPresent());
    }

    @Test
    public void testDeleteTask() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addTodo("task to delete");
        Task deletedTask = taskList.deleteTask(0);
        assertEquals(0, taskList.getTaskCount());
        assertEquals("[T][ ] task to delete", deletedTask.toString());
    }

    @Test
    public void testGetTaskCount() throws JettVarkisException {
        TaskList taskList = new TaskList();
        assertEquals(0, taskList.getTaskCount());
        taskList.addTodo("task1");
        assertEquals(1, taskList.getTaskCount());
        taskList.addTodo("task2");
        assertEquals(2, taskList.getTaskCount());
    }

    // Additional comprehensive tests
    @Test
    public void testAddTodoWithSpecialCharacters() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addTodo("task with @#$%^&*()");
        assertEquals(1, taskList.getTaskCount());
        assertEquals("[T][ ] task with @#$%^&*()", taskList.getTask(0).get().toString());
    }

    @Test
    public void testAddTodoWithUnicodeCharacters() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addTodo("読書する 📚 τέλος");
        assertEquals(1, taskList.getTaskCount());
        assertEquals("[T][ ] 読書する 📚 τέλος", taskList.getTask(0).get().toString());
    }

    @Test
    public void testAddTodoWithSqlInjectionString() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addTodo("'; DROP TABLE tasks; --");
        assertEquals(1, taskList.getTaskCount());
        assertEquals("[T][ ] '; DROP TABLE tasks; --", taskList.getTask(0).get().toString());
    }

    @Test
    public void testAddEmptyTodo() {
        TaskList taskList = new TaskList();
        assertThrows(AssertionError.class, () -> taskList.addTodo(""));
        assertThrows(AssertionError.class, () -> taskList.addTodo("   "));
    }

    @Test
    public void testAddDeadlineWithSpecialCharacters() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addDeadline("@#$%^&*()", "2025-12-25");
        assertEquals(1, taskList.getTaskCount());
        assertTrue(taskList.getTask(0).get().toString().contains("@#$%^&*()"));
    }

    @Test
    public void testAddDeadlineWithUnicodeCharacters() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addDeadline("読書する 📚", "2025-12-25");
        assertEquals(1, taskList.getTaskCount());
        assertTrue(taskList.getTask(0).get().toString().contains("読書する 📚"));
    }

    @Test
    public void testAddEventWithSpecialCharacters() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addEvent("meeting @#$%", "2025-08-27", "2025-08-28");
        assertEquals(1, taskList.getTaskCount());
        assertTrue(taskList.getTask(0).get().toString().contains("meeting @#$%"));
    }

    @Test
    public void testAddEventWithUnicodeCharacters() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addEvent("会議 🏢", "2025-08-27", "2025-08-28");
        assertEquals(1, taskList.getTaskCount());
        assertTrue(taskList.getTask(0).get().toString().contains("会議 🏢"));
    }

    @Test
    public void testGetTaskWithNegativeIndex() {
        TaskList taskList = new TaskList();
        Optional<Task> task = taskList.getTask(-1);
        assertFalse(task.isPresent());
    }

    @Test
    public void testGetTaskWithLargeIndex() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addTodo("single task");
        Optional<Task> task = taskList.getTask(1000);
        assertFalse(task.isPresent());
    }

    @Test
    public void testDeleteTaskFromEmptyList() {
        TaskList taskList = new TaskList();
        assertThrows(AssertionError.class, () -> taskList.deleteTask(0));
    }

    @Test
    public void testDeleteTaskWithNegativeIndex() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addTodo("test task");
        assertThrows(AssertionError.class, () -> taskList.deleteTask(-1));
    }

    @Test
    public void testDeleteTaskWithLargeIndex() throws JettVarkisException {
        TaskList taskList = new TaskList();
        taskList.addTodo("test task");
        assertThrows(AssertionError.class, () -> taskList.deleteTask(1000));
    }

    @Test
    public void testMultipleOperations() throws JettVarkisException {
        TaskList taskList = new TaskList();

        // Add various types of tasks
        taskList.addTodo("todo task");
        taskList.addDeadline("deadline task", "2025-08-27");
        taskList.addEvent("event task", "2025-08-27 1400", "2025-08-27 1600");

        assertEquals(3, taskList.getTaskCount());

        // Verify each task
        assertEquals("[T][ ] todo task", taskList.getTask(0).get().toString());
        assertTrue(taskList.getTask(1).get() instanceof Deadline);
        assertTrue(taskList.getTask(2).get() instanceof Event);

        // Delete middle task
        Task deletedTask = taskList.deleteTask(1);
        assertTrue(deletedTask instanceof Deadline);
        assertEquals(2, taskList.getTaskCount());

        // Verify remaining tasks
        assertEquals("[T][ ] todo task", taskList.getTask(0).get().toString());
        assertTrue(taskList.getTask(1).get() instanceof Event);
    }

    @Test
    public void testAddVeryLongTaskDescription() throws JettVarkisException {
        TaskList taskList = new TaskList();
        StringBuilder longDescription = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            longDescription.append("a");
        }

        taskList.addTodo(longDescription.toString());
        assertEquals(1, taskList.getTaskCount());
        assertTrue(taskList.getTask(0).get().toString().contains(longDescription.toString()));
    }

    @Test
    public void testAddManyTasks() throws JettVarkisException {
        TaskList taskList = new TaskList();

        // Add 1000 tasks
        for (int i = 0; i < 1000; i++) {
            taskList.addTodo("task " + i);
        }

        assertEquals(1000, taskList.getTaskCount());
        assertEquals("[T][ ] task 0", taskList.getTask(0).get().toString());
        assertEquals("[T][ ] task 999", taskList.getTask(999).get().toString());
        assertFalse(taskList.getTask(1000).isPresent());
    }

    @Test
    public void testDeleteAllTasks() throws JettVarkisException {
        TaskList taskList = new TaskList();

        // Add multiple tasks
        taskList.addTodo("task1");
        taskList.addTodo("task2");
        taskList.addTodo("task3");
        assertEquals(3, taskList.getTaskCount());

        // Delete all tasks one by one
        taskList.deleteTask(2); // Delete task3
        assertEquals(2, taskList.getTaskCount());

        taskList.deleteTask(1); // Delete task2
        assertEquals(1, taskList.getTaskCount());

        taskList.deleteTask(0); // Delete task1
        assertEquals(0, taskList.getTaskCount());

        // Verify empty list
        assertFalse(taskList.getTask(0).isPresent());
    }

    @Test
    public void testDeadlineWithInvalidDateFormat() throws JettVarkisException {
        TaskList taskList = new TaskList();
        // Should not throw exception even with invalid date format
        taskList.addDeadline("task with invalid date", "invalid-date-format");
        assertEquals(1, taskList.getTaskCount());
        assertTrue(taskList.getTask(0).get().toString().contains("invalid-date-format"));
    }

    @Test
    public void testEventWithInvalidDateFormat() throws JettVarkisException {
        TaskList taskList = new TaskList();
        // Should not throw exception even with invalid date format
        taskList.addEvent("event with invalid dates", "invalid-from", "invalid-to");
        assertEquals(1, taskList.getTaskCount());
        assertTrue(taskList.getTask(0).get().toString().contains("invalid-from"));
        assertTrue(taskList.getTask(0).get().toString().contains("invalid-to"));
    }

    @Test
    public void testTaskOrderPreservation() throws JettVarkisException {
        TaskList taskList = new TaskList();

        taskList.addTodo("first");
        taskList.addTodo("second");
        taskList.addTodo("third");

        assertEquals("[T][ ] first", taskList.getTask(0).get().toString());
        assertEquals("[T][ ] second", taskList.getTask(1).get().toString());
        assertEquals("[T][ ] third", taskList.getTask(2).get().toString());

        // Delete middle task
        taskList.deleteTask(1);

        // Verify order is preserved
        assertEquals("[T][ ] first", taskList.getTask(0).get().toString());
        assertEquals("[T][ ] third", taskList.getTask(1).get().toString());
    }
}

