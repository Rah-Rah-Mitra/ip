import java.util.ArrayList;
import java.util.Optional;

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
}
