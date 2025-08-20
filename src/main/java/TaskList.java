import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public void addTodo(String description) {
        tasks.add(new Todo(description));
    }

    public void addDeadline(String description, String by) {
        tasks.add(new Deadline(description, by));
    }

    public void addEvent(String description, String from, String to) {
        tasks.add(new Event(description, from, to));
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getTaskCount() {
        return tasks.size();
    }
}
