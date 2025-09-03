package jettvarkis.command;

import java.util.Optional;

import jettvarkis.TaskList;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.task.Task;
import jettvarkis.ui.Ui;

/**
 * Represents a Todo command. This command adds a new Todo task to the list.
 */
public class TodoCommand extends Command {

    private final String description;

    /**
     * Constructs a TodoCommand with the specified description for the Todo task.
     *
     * @param description
     *            The description of the Todo task.
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the Todo command.
     * Adds a new Todo task to the task list, displays a confirmation message to the
     * user,
     * and saves the updated task list to storage.
     *
     * @param ui
     *            The Ui object to interact with the user.
     * @param tasks
     *            The TaskList object to add the task to.
     * @param storage
     *            The Storage object to save the tasks.
     * @throws JettVarkisException
     *             If there is an error during execution (e.g., storage error).
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) throws JettVarkisException {
        tasks.addTodo(description);
        Optional<Task> task = tasks.getTask(tasks.getTaskCount() - 1);
        task.ifPresent(value -> ui.showAddedTask(value, tasks.getTaskCount()));
        storage.save(tasks.getTasks());
    }
}
