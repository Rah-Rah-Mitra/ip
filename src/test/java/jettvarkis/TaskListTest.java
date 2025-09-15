package jettvarkis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
}
