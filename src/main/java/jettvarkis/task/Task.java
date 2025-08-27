package jettvarkis.task;

/**
 * Represents a generic task. This is an abstract class that provides
 * common functionalities for all types of tasks (Todo, Deadline, Event).
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     * The task is initially not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     * "X" if the task is done, " " (space) otherwise.
     *
     * @return The status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task for display.
     * Includes the status icon and the description.
     *
     * @return A string representing the task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns a string representation of the task for saving to a file.
     * This method must be implemented by concrete task classes.
     *
     * @return A string representing the task in file format.
     */
    public abstract String toFileString();
}