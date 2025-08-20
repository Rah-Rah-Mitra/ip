public class JettVarkis {

    private final Ui ui;
    private final TaskList tasks;

    public JettVarkis() {
        ui = new Ui();
        tasks = new TaskList();
    }

    public void run() {
        ui.showWelcome();
        String input = ui.readCommand();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                ui.showTasks(tasks.getTasks());
            } else {
                tasks.addTask(input);
                ui.showAddedTask(input);
            }
            input = ui.readCommand();
        }
        ui.showGoodbye();
    }

    public static void main(String[] args) {
        new JettVarkis().run();
    }
}