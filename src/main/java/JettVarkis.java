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
            String[] parts = fullCommand.split(" ", 2);
            String command = parts[0];

            switch (command) {
            case "list":
                ui.showTasks(tasks.getTasks());
                break;
            case "mark": {
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                Task task = tasks.getTask(taskIndex);
                task.markAsDone();
                ui.showMarkedTask(task);
                break;
            }
            case "unmark": {
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                Task task = tasks.getTask(taskIndex);
                task.markAsUndone();
                ui.showUnmarkedTask(task);
                break;
            }
            case "todo": {
                tasks.addTodo(parts[1]);
                Task task = tasks.getTask(tasks.getTaskCount() - 1);
                ui.showAddedTask(task, tasks.getTaskCount());
                break;
            }
            case "deadline": {
                String[] deadlineParts = parts[1].split(" /by ");
                tasks.addDeadline(deadlineParts[0], deadlineParts[1]);
                Task task = tasks.getTask(tasks.getTaskCount() - 1);
                ui.showAddedTask(task, tasks.getTaskCount());
                break;
            }
            case "event": {
                String[] eventParts = parts[1].split(" /from ");
                String[] fromToParts = eventParts[1].split(" /to ");
                tasks.addEvent(eventParts[0], fromToParts[0], fromToParts[1]);
                Task task = tasks.getTask(tasks.getTaskCount() - 1);
                ui.showAddedTask(task, tasks.getTaskCount());
                break;
            }
            default:
                break;
            }
            fullCommand = ui.readCommand();
        }
        ui.showGoodbye();
    }

    public static void main(String[] args) {
        new JettVarkis().run();
    }
}
