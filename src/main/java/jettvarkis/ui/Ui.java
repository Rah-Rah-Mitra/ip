package jettvarkis.ui;

import java.util.ArrayList;
import java.util.List;

import jettvarkis.task.Task;

/**
 * Represents the User Interface of the JettVarkis application.
 * Handles all interactions with the user, including reading commands and displaying messages.
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
     * @param task The task that was added.
     * @param taskCount The total number of tasks in the list after addition.
     */
    public void showAddedTask(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays all tasks in the list.
     *
     * @param tasks The ArrayList of tasks to be displayed.
     */
    public void showTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showMarkedTask(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Displays a message indicating that a task has been marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showUnmarkedTask(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param taskCount The total number of tasks in the list after deletion.
     */
    public void showDeletedTask(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        System.out.println("    OOPS!!! " + message);
    }

    public void showFoundTasks(List<Task> tasks) {
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
