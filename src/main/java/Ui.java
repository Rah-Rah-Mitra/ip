import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private final Scanner in = new Scanner(System.in);

    public String readCommand() {
        if (in.hasNextLine()) {
            return in.nextLine();
        } else {
            return "bye";
        }
    }

    public void showWelcome() {
        System.out.println("Jett Varkis at your service. What can I get for you?");
    }

    public void showGoodbye() {
        System.out.println("Leaving so soon? See you next time.");
    }

    public void showAddedTask(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showMarkedTask(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showUnmarkedTask(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showError(String message) {
        System.out.println("    OOPS!!! " + message);
    }
}