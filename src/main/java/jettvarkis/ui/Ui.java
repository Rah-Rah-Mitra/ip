package jettvarkis.ui;

import java.util.ArrayList;
import java.util.List;

import jettvarkis.task.Task;

/**
 * Represents the User Interface of the JettVarkis application.
 * Handles all interactions with the user, including reading commands and
 * displaying messages.
 */
public class Ui {

    /**
     * Displays a welcome message to the user.
     */
    public void showWelcome() {
        System.out.println("Jett Varkis at your service. What can I get for you?");
    }

    /**
     * Displays a goodbye message to the user.
     */
    public void showGoodbye() {
        System.out.println("Leaving so soon? See you next time.");
    }

    /**
     * Displays a message indicating that a task has been added.
     *
     * @param task
     *            The task that was added.
     * @param taskCount
     *            The total number of tasks in the list after addition.
     */
    public void showAddedTask(Task task, int taskCount) {
        assert task != null;
        assert taskCount >= 0 : "Task count cannot be negative";
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays all tasks in the list.
     *
     * @param tasks
     *            The ArrayList of tasks to be displayed.
     */
    public void showTasks(ArrayList<Task> tasks) {
        assert tasks != null;
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            assert tasks.get(i) != null : "Task at index " + i + " is null";
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param markedTasks
     *            The list of tasks that were marked.
     */
    public void showMarkedTasks(List<Task> markedTasks) {
        assert markedTasks != null;
        System.out.println("Nice! I've marked the following tasks as done:");
        for (Task task : markedTasks) {
            System.out.println("  " + task);
        }
    }

    /**
     * Displays a message indicating that a task has been marked as not done.
     *
     * @param unmarkedTasks
     *            The list of tasks that were unmarked.
     */
    public void showUnmarkedTasks(List<Task> unmarkedTasks) {
        assert unmarkedTasks != null;
        System.out.println("OK, I've marked the following tasks as not done yet:");
        for (Task task : unmarkedTasks) {
            System.out.println("  " + task);
        }
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param deletedTasks
     *            The list of tasks that were deleted.
     * @param taskCount
     *            The total number of tasks in the list after deletion.
     */
    public void showDeletedTasks(List<Task> deletedTasks, int taskCount) {
        assert deletedTasks != null;
        assert taskCount >= 0 : "Task count cannot be negative";
        System.out.println("Noted. I've removed the following tasks:");
        for (Task task : deletedTasks) {
            System.out.println("  " + task);
        }
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message
     *            The error message to be displayed.
     */
    public void showError(String message) {
        assert message != null;
        System.out.println("    OOPS!!! " + message);
    }

    /**
     * Displays the list of tasks found by the find command.
     *
     * @param tasks
     *            The list of tasks found.
     */
    public void showFoundTasks(List<Task> tasks) {
        assert tasks != null;
        if (tasks.isEmpty()) {
            System.out.println("No matching tasks found in your list: Jett Varkis is sad.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("  " + (i + 1) + "." + tasks.get(i));
            }
        }
    }
}
