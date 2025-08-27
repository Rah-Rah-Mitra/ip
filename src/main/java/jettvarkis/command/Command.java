
public abstract class Command {
    public abstract void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException;

    public boolean isExit() {
        return false;
    }
}
