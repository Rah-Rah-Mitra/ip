package jettvarkis;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import jettvarkis.task.Task;
import jettvarkis.task.Todo;
import jettvarkis.task.Deadline;
import jettvarkis.task.Event;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTodo(String description) {
        tasks.add(new Todo(description));
    }

    public void addDeadline(String description, String by) {
        tasks.add(new Deadline(description, by));
    }

    public void addEvent(String description, String from, String to) {
        tasks.add(new Event(description, from, to));
    }

    public void addDeadline(String description, java.time.LocalDateTime by) {
        tasks.add(new Deadline(description, by));
    }

    public void addEvent(String description, java.time.LocalDateTime from, java.time.LocalDateTime to) {
        tasks.add(new Event(description, from, to));
    }

    public Optional<Task> getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            return Optional.empty();
        }
        return Optional.of(tasks.get(index));
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public List<Task> findTasks(String keyword) {
        ArrayList<Task> foundTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }

    public Task deleteTask(int index) {
        return tasks.remove(index);
    }
}