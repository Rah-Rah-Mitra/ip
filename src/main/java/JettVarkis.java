import java.util.Scanner;

public class JettVarkis implements Bot {

    private final String name = "Jett Varkis";

    @Override
    public String greet() {
        return name + " at your service. What can I get for you?";
    }

    @Override
    public String exit() {
        return "Leaving so soon? See you next time.";
    }

    public static void main(String[] args) {
        JettVarkis bot = new JettVarkis();
        System.out.println(bot.greet());

        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        while (!input.equals("bye")) {
            System.out.println(input);
            input = in.nextLine();
        }

        System.out.println(bot.exit());
        in.close();
    }
}
