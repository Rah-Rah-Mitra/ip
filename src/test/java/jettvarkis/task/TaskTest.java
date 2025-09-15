package jettvarkis.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

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

    // Todo Task Tests
    @Test
    public void testTodoWithSpecialCharacters() {
        Todo todo = new Todo("task with @#$%^&*()");
        assertEquals("[T][ ] task with @#$%^&*()", todo.toString());
        assertEquals("T | 0 | task with @#$%^&*()", todo.toFileString());
    }

    @Test
    public void testTodoWithUnicodeCharacters() {
        Todo todo = new Todo("読書する 📚 τέλος");
        assertEquals("[T][ ] 読書する 📚 τέλος", todo.toString());
        assertEquals("T | 0 | 読書する 📚 τέλος", todo.toFileString());
    }

    @Test
    public void testTodoWithSqlInjectionString() {
        Todo todo = new Todo("'; DROP TABLE tasks; --");
        assertEquals("[T][ ] '; DROP TABLE tasks; --", todo.toString());
        assertEquals("T | 0 | '; DROP TABLE tasks; --", todo.toFileString());
    }

    @Test
    public void testTodoWithEmptyString() {
        // This should cause an assertion error since Task constructor asserts non-empty description
        assertThrows(AssertionError.class, () -> new Todo(""));
    }

    @Test
    public void testTodoWithWhitespaceOnly() {
        // This should cause an assertion error since Task constructor asserts non-empty description
        assertThrows(AssertionError.class, () -> new Todo("   "));
    }

    @Test
    public void testTodoWithNewlines() {
        Todo todo = new Todo("task\nwith\nnewlines");
        assertEquals("[T][ ] task\nwith\nnewlines", todo.toString());
        assertEquals("T | 0 | task\nwith\nnewlines", todo.toFileString());
    }

    @Test
    public void testTodoWithVeryLongDescription() {
        StringBuilder longDesc = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longDesc.append("a");
        }
        Todo todo = new Todo(longDesc.toString());
        assertTrue(todo.toString().contains(longDesc.toString()));
        assertTrue(todo.toFileString().contains(longDesc.toString()));
    }

    @Test
    public void testTodoFileString() {
        Todo todo = new Todo("test task");
        assertEquals("T | 0 | test task", todo.toFileString());
        todo.markAsDone();
        assertEquals("T | 1 | test task", todo.toFileString());
    }

    // Deadline Task Tests
    @Test
    public void testDeadlineWithLocalDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 27, 14, 0);
        Deadline deadline = new Deadline("submit report", dateTime);
        assertTrue(deadline.toString().contains("submit report"));
        assertTrue(deadline.toString().contains("Aug 27 2025"));
        assertTrue(deadline.toString().contains("2:00PM"));
    }

    @Test
    public void testDeadlineWithString() {
        Deadline deadline = new Deadline("submit report", "2025-08-27");
        assertEquals("[D][ ] submit report (by: 2025-08-27)", deadline.toString());
    }

    @Test
    public void testDeadlineWithSpecialCharacters() {
        Deadline deadline = new Deadline("@#$%^&*()", "2025-08-27");
        assertEquals("[D][ ] @#$%^&*() (by: 2025-08-27)", deadline.toString());
    }

    @Test
    public void testDeadlineWithUnicodeCharacters() {
        Deadline deadline = new Deadline("読書する 📚", "2025-08-27");
        assertTrue(deadline.toString().contains("読書する 📚"));
    }

    @Test
    public void testDeadlineFileString() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 27, 14, 0);
        Deadline deadline = new Deadline("submit report", dateTime);
        assertEquals("D | 0 | submit report | " + dateTime, deadline.toFileString());
        deadline.markAsDone();
        assertEquals("D | 1 | submit report | " + dateTime, deadline.toFileString());
    }

    @Test
    public void testDeadlineFileStringWithStringDate() {
        Deadline deadline = new Deadline("submit report", "2025-08-27");
        assertEquals("D | 0 | submit report | 2025-08-27", deadline.toFileString());
    }

    @Test
    public void testDeadlineEquals() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 27, 14, 0);
        Deadline deadline1 = new Deadline("submit report", dateTime);
        Deadline deadline2 = new Deadline("submit report", dateTime);
        Deadline deadline3 = new Deadline("different task", dateTime);

        assertEquals(deadline1, deadline2);
        assertNotEquals(deadline1, deadline3);
    }

    @Test
    public void testDeadlineEqualsWithStringDates() {
        Deadline deadline1 = new Deadline("submit report", "2025-08-27");
        Deadline deadline2 = new Deadline("submit report", "2025-08-27");
        Deadline deadline3 = new Deadline("submit report", "2025-08-28");

        assertEquals(deadline1, deadline2);
        assertNotEquals(deadline1, deadline3);
    }

    @Test
    public void testDeadlineEqualsMixedTypes() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 27, 14, 0);
        Deadline deadline1 = new Deadline("submit report", dateTime);
        Deadline deadline2 = new Deadline("submit report", "2025-08-27");

        assertNotEquals(deadline1, deadline2);
    }

    // Event Task Tests
    @Test
    public void testEventWithLocalDateTime() {
        LocalDateTime from = LocalDateTime.of(2025, 8, 27, 14, 0);
        LocalDateTime to = LocalDateTime.of(2025, 8, 27, 16, 0);
        Event event = new Event("meeting", from, to);
        assertTrue(event.toString().contains("meeting"));
        assertTrue(event.toString().contains("Aug 27 2025"));
        assertTrue(event.toString().contains("2:00PM"));
        assertTrue(event.toString().contains("4:00PM"));
    }

    @Test
    public void testEventWithString() {
        Event event = new Event("meeting", "2025-08-27 1400", "2025-08-27 1600");
        assertEquals("[E][ ] meeting (from: 2025-08-27 1400 to: 2025-08-27 1600)", event.toString());
    }

    @Test
    public void testEventWithSpecialCharacters() {
        Event event = new Event("@#$%^&*()", "2025-08-27", "2025-08-28");
        assertEquals("[E][ ] @#$%^&*() (from: 2025-08-27 to: 2025-08-28)", event.toString());
    }

    @Test
    public void testEventWithUnicodeCharacters() {
        Event event = new Event("読書する 📚", "2025-08-27", "2025-08-28");
        assertTrue(event.toString().contains("読書する 📚"));
    }

    @Test
    public void testEventFileString() {
        LocalDateTime from = LocalDateTime.of(2025, 8, 27, 14, 0);
        LocalDateTime to = LocalDateTime.of(2025, 8, 27, 16, 0);
        Event event = new Event("meeting", from, to);
        assertEquals("E | 0 | meeting | " + from + " | " + to, event.toFileString());
        event.markAsDone();
        assertEquals("E | 1 | meeting | " + from + " | " + to, event.toFileString());
    }

    @Test
    public void testEventFileStringWithStringDates() {
        Event event = new Event("meeting", "2025-08-27 1400", "2025-08-27 1600");
        assertEquals("E | 0 | meeting | 2025-08-27 1400 | 2025-08-27 1600", event.toFileString());
    }

    @Test
    public void testEventEquals() {
        LocalDateTime from = LocalDateTime.of(2025, 8, 27, 14, 0);
        LocalDateTime to = LocalDateTime.of(2025, 8, 27, 16, 0);
        Event event1 = new Event("meeting", from, to);
        Event event2 = new Event("meeting", from, to);
        Event event3 = new Event("different meeting", from, to);

        assertEquals(event1, event2);
        assertNotEquals(event1, event3);
    }

    @Test
    public void testEventEqualsWithStringDates() {
        Event event1 = new Event("meeting", "2025-08-27 1400", "2025-08-27 1600");
        Event event2 = new Event("meeting", "2025-08-27 1400", "2025-08-27 1600");
        Event event3 = new Event("meeting", "2025-08-27 1400", "2025-08-27 1700");

        assertEquals(event1, event2);
        assertNotEquals(event1, event3);
    }

    @Test
    public void testEventEqualsMixedTypes() {
        LocalDateTime from = LocalDateTime.of(2025, 8, 27, 14, 0);
        LocalDateTime to = LocalDateTime.of(2025, 8, 27, 16, 0);
        Event event1 = new Event("meeting", from, to);
        Event event2 = new Event("meeting", "2025-08-27 1400", "2025-08-27 1600");

        assertNotEquals(event1, event2);
    }

    // Task equality tests across types
    @Test
    public void testTaskEqualityAcrossTypes() {
        Todo todo = new Todo("task");
        Deadline deadline = new Deadline("task", "2025-08-27");
        Event event = new Event("task", "2025-08-27", "2025-08-28");

        assertNotEquals(todo, deadline);
        assertNotEquals(todo, event);
        assertNotEquals(deadline, event);
    }

    @Test
    public void testTaskEqualityWithNull() {
        Todo todo = new Todo("task");
        assertNotEquals(todo, null);
        assertFalse(todo.equals(null));
    }

    @Test
    public void testTaskEqualityWithDifferentClass() {
        Todo todo = new Todo("task");
        String notATask = "not a task";
        assertNotEquals(todo, notATask);
        assertFalse(todo.equals(notATask));
    }

    @Test
    public void testTaskDescriptionAccess() {
        Todo todo = new Todo("test description");
        assertEquals("test description", todo.getDescription());

        Deadline deadline = new Deadline("deadline description", "2025-08-27");
        assertEquals("deadline description", deadline.getDescription());

        Event event = new Event("event description", "2025-08-27", "2025-08-28");
        assertEquals("event description", event.getDescription());
    }

    @Test
    public void testTaskStatusToggling() {
        Todo todo = new Todo("test task");
        assertEquals(" ", todo.getStatusIcon());

        todo.markAsDone();
        assertEquals("X", todo.getStatusIcon());

        todo.markAsUndone();
        assertEquals(" ", todo.getStatusIcon());

        // Test multiple toggles
        todo.markAsDone();
        todo.markAsDone(); // Should still be done
        assertEquals("X", todo.getStatusIcon());

        todo.markAsUndone();
        todo.markAsUndone(); // Should still be undone
        assertEquals(" ", todo.getStatusIcon());
    }
}
