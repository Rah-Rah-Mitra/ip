public class JettVarkis implements Bot {
    private final String name = "Jett Varkis";

    @Override
    public String greet() {
        return "Hello! I'm " + name + ".\n" +
                "How can I assist you today?";
    }

    @Override
    public String exit() {
        return "Bye. Hope to see you again soon!";
    }

    public static void main(String[] args) {
        JettVarkis bot = new JettVarkis();
        System.out.println(bot.greet());
        System.out.println(bot.exit());
    }
}
