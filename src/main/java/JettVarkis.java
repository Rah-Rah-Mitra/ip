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
            default: {
                tasks.addTask(fullCommand);
                Task task = tasks.getTask(tasks.getTaskCount() - 1);
                ui.showAddedTask(task, tasks.getTaskCount());
                break;
            }
            }
            fullCommand = ui.readCommand();
        }
        ui.showGoodbye();
    }

    public static void main(String[] args) {
        new JettVarkis().run();
    }
}
