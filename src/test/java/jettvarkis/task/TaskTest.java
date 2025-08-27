package jettvarkis.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    @Test
    public void testGetStatusIconNotDone() {
        Task task = new Todo("test todo");
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void testGetStatusIconDone() {
        Task task = new Todo("test todo");
        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void testMarkAsDone() {
        Task task = new Todo("test todo");
        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void testMarkAsUndone() {
        Task task = new Todo("test todo");
        task.markAsDone(); // Mark as done first
        task.markAsUndone();
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void testToString() {
        Task task = new Todo("test todo");
        assertEquals("[T][ ] test todo", task.toString());
        task.markAsDone();
        assertEquals("[T][X] test todo", task.toString());
    }
}
