import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private final Scanner in = new Scanner(System.in);

    public String readCommand() {
        return in.nextLine();
    }

    public void showWelcome() {
        System.out.println("Jett Varkis at your service. What can I get for you?");
    }

    public void showGoodbye() {
        System.out.println("Leaving so soon? See you next time.");
    }

    public void showAddedTask(String task) {
        System.out.println("added: " + task);
    }

    public void showTasks(ArrayList<String> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}
