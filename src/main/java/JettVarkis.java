public class JettVarkis {

    private final Ui ui;
    private TaskList tasks;
    private final Storage storage;

    public JettVarkis(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (JettVarkisException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(ui, tasks, storage);
                isExit = c.isExit();
            } catch (JettVarkisException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new JettVarkis("../data/jettvarkis.txt").run();
    }
}
