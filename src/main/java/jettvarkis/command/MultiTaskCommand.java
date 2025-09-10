package jettvarkis.command;

/**
 * Represents a command that operates on multiple tasks identified by their indices.
 * This is an abstract class that provides a common structure for commands that need to
 * process an array of task indices.
 */
public abstract class MultiTaskCommand extends Command {
    protected final int[] taskIndices;

    /**
     * Constructs a MultiTaskCommand with the specified task indices.
     *
     * @param taskIndices The zero-based indices of the tasks to be processed.
     */
    public MultiTaskCommand(int... taskIndices) {
        this.taskIndices = taskIndices;
    }
}
