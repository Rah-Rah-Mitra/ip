import java.util.ArrayList;

public class TaskList {
    private final ArrayList<String> tasks = new ArrayList<>();

    public void addTask(String task) {
        tasks.add(task);
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }
}
