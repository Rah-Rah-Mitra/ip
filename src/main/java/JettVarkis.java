import java.util.ArrayList;
import java.util.Scanner;

public class JettVarkis implements Bot {

    private final String name = "Jett Varkis";
    private final ArrayList<String> tasks = new ArrayList<>();

    @Override
    public String greet() {
        return name + " at your service. What can I get for you?";
    }

    @Override
    public String exit() {
        return "Leaving so soon? See you next time.";
    }

    public void addTask(String task) {
        tasks.add(task);
        System.out.println("added: " + task);
    }

    public void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public static void main(String[] args) {
        JettVarkis bot = new JettVarkis();
        System.out.println(bot.greet());

        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                bot.listTasks();
            } else {
                bot.addTask(input);
            }
            input = in.nextLine();
        }

        System.out.println(bot.exit());
        in.close();
    }
}
