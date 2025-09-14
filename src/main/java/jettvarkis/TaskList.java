package jettvarkis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jettvarkis.task.Deadline;
import jettvarkis.task.Event;
import jettvarkis.task.Task;
import jettvarkis.task.Todo;

/**
 * Represents a list of tasks in the JettVarkis application.
 * Manages operations such as adding, deleting, and retrieving tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks
     *            The ArrayList of tasks to initialize the TaskList with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new Todo task to the list.
     *
     * @param description
     *            The description of the Todo task.
     */
    public void addTodo(String description) {
        tasks.add(new Todo(description));
    }

    /**
     * Adds a new Deadline task to the list.
     *
     * @param description
     *            The description of the Deadline task.
     * @param by
     *            The deadline of the task (e.g., "2/12/2019 1800").
     */
    public void addDeadline(String description, String by) {
        tasks.add(new Deadline(description, by));
    }

    /**
     * Adds a new Deadline task to the list with a LocalDateTime object.
     *
     * @param description
     *            The description of the Deadline task.
     * @param by
     *            The deadline of the task as a LocalDateTime object.
     */
    public void addDeadline(String description, java.time.LocalDateTime by) {
        tasks.add(new Deadline(description, by));
    }

    /**
     * Adds a new Event task to the list.
     *
     * @param description
     *            The description of the Event task.
     * @param from
     *            The start time of the event.
     * @param to
     *            The end time of the event.
     */
    public void addEvent(String description, String from, String to) {
        tasks.add(new Event(description, from, to));
    }

    /**
     * Adds a new Event task to the list with LocalDateTime objects.
     *
     * @param description
     *            The description of the Event task.
     * @param from
     *            The start time of the event as a LocalDateTime object.
     * @param to
     *            The end time of the event as a LocalDateTime object.
     */
    public void addEvent(String description, java.time.LocalDateTime from, java.time.LocalDateTime to) {
        tasks.add(new Event(description, from, to));
    }

    /**
     * Retrieves a task from the list based on its index.
     *
     * @param index
     *            The zero-based index of the task to retrieve.
     * @return An Optional containing the Task if found, or an empty Optional if the
     *         index is out of bounds.
     */
    public Optional<Task> getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            return Optional.empty();
        }
        return Optional.of(tasks.get(index));
    }

    /**
     * Returns the entire list of tasks.
     *
     * @return An ArrayList containing all tasks in the list.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The total number of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Finds and returns a list of tasks that contain the given keyword in their
     * string representation.
     * The search is case-insensitive.
     *
     * @param keyword
     *            The keyword to search for.
     * @return A list of tasks that match the keyword.
     */
    public List<Task> findTasks(String keyword) {
        ArrayList<Task> foundTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }

    /**
     * Deletes a task from the list based on its index.
     *
     * @param index
     *            The zero-based index of the task to delete.
     * @return The deleted Task object.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }
}
