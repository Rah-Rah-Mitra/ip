
public class ByeCommand extends Command {

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
