package jettvarkis.command;

import jettvarkis.ui.Ui;
import jettvarkis.TaskList;
import jettvarkis.storage.Storage;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.task.Task;

public class DeleteCommand extends Command {

    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        try {
            Task deletedTask = tasks.deleteTask(taskIndex);
            ui.showDeletedTask(deletedTask, tasks.getTaskCount());
            storage.save(tasks.getTasks());
        } catch (IndexOutOfBoundsException e) {
            throw new JettVarkisException(JettVarkisException.ErrorType.TASK_NOT_FOUND);
        }
    }
}
