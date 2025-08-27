package jettvarkis.command;

import java.util.Optional;

import jettvarkis.ui.Ui;
import jettvarkis.TaskList;
import jettvarkis.storage.Storage;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.task.Task;

public class TodoCommand extends Command {

    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        tasks.addTodo(description);
        Optional<Task> task = tasks.getTask(tasks.getTaskCount() - 1);
        task.ifPresent(value -> ui.showAddedTask(value, tasks.getTaskCount()));
        storage.save(tasks.getTasks());
    }
}
