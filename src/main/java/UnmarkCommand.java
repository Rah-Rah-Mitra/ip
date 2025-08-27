import java.util.Optional;

public class UnmarkCommand extends Command {

    private final int taskIndex;

    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        tasks.getTask(taskIndex).ifPresentOrElse(task -> {
            task.markAsUndone();
            ui.showUnmarkedTask(task);
        }, () -> ui.showError(JettVarkisException.ErrorType.TASK_NOT_FOUND.getMessage()));
        storage.save(tasks.getTasks());
    }
}
