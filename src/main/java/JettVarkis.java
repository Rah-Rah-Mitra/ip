import java.util.Optional;

public class JettVarkis {

    private final Ui ui;
    private final TaskList tasks;

    public JettVarkis() {
        ui = new Ui();
        tasks = new TaskList();
    }

    public void run() {
        ui.showWelcome();
        String fullCommand = ui.readCommand();

        while (!fullCommand.equals("bye")) {
            try {
                String[] parts = fullCommand.split(" ", 2);
                String command = parts[0];

                switch (command) {
                case "list":
                    ui.showTasks(tasks.getTasks());
                    break;
                case "mark": {
                    if (parts.length < 2) {
                        throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
                    }
                    int taskIndex = Integer.parseInt(parts[1]) - 1;
                    tasks.getTask(taskIndex).ifPresentOrElse(task -> {
                        task.markAsDone();
                        ui.showMarkedTask(task);
                    }, () -> ui.showError(JettVarkisException.ErrorType.TASK_NOT_FOUND.getMessage()));
                    break;
                }
                case "unmark": {
                    if (parts.length < 2) {
                        throw new JettVarkisException(JettVarkisException.ErrorType.MISSING_TASK_NUMBER);
                    }
                    int taskIndex = Integer.parseInt(parts[1]) - 1;
                    tasks.getTask(taskIndex).ifPresentOrElse(task -> {
                        task.markAsUndone();
                        ui.showUnmarkedTask(task);
                    }, () -> ui.showError(JettVarkisException.ErrorType.TASK_NOT_FOUND.getMessage()));
                    break;
                }
                case "todo": {
                    if (parts.length < 2) {
                        throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_TODO_DESCRIPTION);
                    }
                    tasks.addTodo(parts[1]);
                    Optional<Task> task = tasks.getTask(tasks.getTaskCount() - 1);
                    task.ifPresent(value -> ui.showAddedTask(value, tasks.getTaskCount()));
                    break;
                }
                case "deadline": {
                    if (parts.length < 2) {
                        throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_DEADLINE_DESCRIPTION);
                    }
                    String[] deadlineParts = parts[1].split(" /by ");
                    if (deadlineParts.length < 2) {
                        throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_DEADLINE_BY);
                    }
                    tasks.addDeadline(deadlineParts[0], deadlineParts[1]);
                    Optional<Task> task = tasks.getTask(tasks.getTaskCount() - 1);
                    task.ifPresent(value -> ui.showAddedTask(value, tasks.getTaskCount()));
                    break;
                }
                case "event": {
                    if (parts.length < 2) {
                        throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_EVENT_DESCRIPTION);
                    }
                    String[] eventParts = parts[1].split(" /from ");
                    if (eventParts.length < 2) {
                        throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_EVENT_FROM);
                    }
                    String[] fromToParts = eventParts[1].split(" /to ");
                    if (fromToParts.length < 2) {
                        throw new JettVarkisException(JettVarkisException.ErrorType.EMPTY_EVENT_TO);
                    }
                    tasks.addEvent(eventParts[0], fromToParts[0], fromToParts[1]);
                    Optional<Task> task = tasks.getTask(tasks.getTaskCount() - 1);
                    task.ifPresent(value -> ui.showAddedTask(value, tasks.getTaskCount()));
                    break;
                }
                default:
                    throw new JettVarkisException(JettVarkisException.ErrorType.UNKNOWN_COMMAND);
                }
            } catch (JettVarkisException e) {
                ui.showError(e.getMessage());
            }
            fullCommand = ui.readCommand();
        }
        ui.showGoodbye();
    }

    public static void main(String[] args) {
        new JettVarkis().run();
    }
}