package jettvarkis.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import jettvarkis.task.Task;
import jettvarkis.trivia.Trivia;
import jettvarkis.trivia.TriviaList;

/**
 * Represents the User Interface of the JettVarkis application.
 * Handles all interactions with the user, including reading commands and
 * displaying messages.
 */
public class Ui {

    /**
     * Returns the welcome message to the user.
     *
     * @return The welcome message string.
     */
    public String getWelcomeMessage() {
        return "Jett Varkis at your service. What can I get for you?";
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
        IntStream.range(0, tasks.size())
                .forEach(i -> {
                    assert tasks.get(i) != null : "Task at index " + i + " is null";
                    System.out.println((i + 1) + "." + tasks.get(i));
                });
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
        markedTasks.forEach(task -> System.out.println("  " + task));
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
        unmarkedTasks.forEach(task -> System.out.println("  " + task));
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
        deletedTasks.forEach(task -> System.out.println("  " + task));
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
            IntStream.range(0, tasks.size())
                    .forEach(i -> System.out.println("  " + (i + 1) + "." + tasks.get(i)));
        }
    }

    /**
     * Displays the available trivia categories.
     *
     * @param categories
     *            The list of category names.
     */
    public void showTriviaCategories(List<String> categories) {
        assert categories != null;
        if (categories.isEmpty()) {
            System.out.println("No trivia categories found.");
        } else {
            System.out.println("Here are the available trivia categories:");
            for (String category : categories) {
                System.out.println("- " + category);
            }
        }
    }

    /**
     * Displays a message indicating that a trivia has been added.
     *
     * @param trivia
     *            The trivia that was added.
     * @param category
     *            The category to which the trivia was added.
     */
    public void showTriviaAdded(Trivia trivia, String category, int triviaCount) {
        assert trivia != null;
        System.out.println("Got it. I've added this trivia to the '" + category + "' category:");
        System.out.println("  " + trivia);
        System.out.println("Now you have " + triviaCount + " trivia items in the '" + category + "' category.");
    }

    /**
     * Displays a message indicating that a trivia category has been selected.
     *
     * @param category
     *            The selected category.
     */
    public void showTriviaCategorySelected(String category, int triviaCount) {
        System.out.println("Trivia category '" + category + "' selected. It has " + triviaCount + " trivia items.");
    }

    /**
     * Displays a message indicating the start of a trivia quiz.
     *
     * @param category
     *            The category of the quiz.
     */
    public void showTriviaStart(String category) {
        System.out.println("Starting quiz for category '" + category + "'!");
    }

    /**
     * Displays a message indicating the end of a trivia quiz.
     */
    public void showTriviaStop() {
        System.out.println("Trivia quiz stopped.");
    }

    /**
     * Displays a message indicating that a trivia has been deleted.
     *
     * @param trivia
     *            The trivia that was deleted.
     * @param category
     *            The category from which the trivia was deleted.
     */
    public void showTriviaDeleted(Trivia trivia, String category, int triviaCount) {
        assert trivia != null;
        System.out.println("Noted. I've removed the following trivia from the '" + category + "' category:");
        System.out.println("  " + trivia);
        System.out.println("Now you have " + triviaCount + " trivia items in the '" + category + "' category.");
    }

    /**
     * Displays a trivia question.
     *
     * @param trivia
     *            The trivia question to display.
     */
    public void showTriviaQuestion(Trivia trivia) {
        assert trivia != null;
        System.out.println("Question: " + trivia.getQuestion());
    }

    /**
     * Displays a message for a correct answer.
     */
    public void showCorrectAnswer() {
        System.out.println("Correct!");
    }

    /**
     * Displays a message for an incorrect answer, showing the correct answer.
     *
     * @param correctAnswer
     *            The correct answer.
     */
    public void showIncorrectAnswer(String correctAnswer) {
        System.out.println("Incorrect. The correct answer is: " + correctAnswer);
    }

    /**
     * Displays the trivia items in a given list.
     *
     * @param triviaList
     *            The list of trivia items to display.
     * @param category
     *            The category of the trivia list.
     */
    public void showTriviaList(TriviaList triviaList, String category) {
        assert triviaList != null;
        System.out.println("Here are the trivia items in the '" + category + "' category:");
        if (triviaList.size() == 0) {
            System.out.println("  No trivia items in this category.");
        } else {
            IntStream.range(0, triviaList.size())
                    .forEach(i -> System.out.println("  " + (i + 1) + "." + triviaList.get(i)));
        }
    }

    /**
     * Displays a message indicating that a trivia category has been created.
     *
     * @param categoryName
     *            The name of the created category.
     */
    public void showTriviaCategoryCreated(String categoryName) {
        System.out.println("Trivia category '" + categoryName + "' created.");
    }

    /**
     * Displays a message indicating that a trivia category has been deleted.
     *
     * @param categoryName The name of the deleted category.
     */
    public void showTriviaCategoryDeleted(String categoryName) {
        System.out.println("Trivia category '" + categoryName + "' deleted.");
    }

    /**
     * Displays a summary of all trivia commands and their functions.
     */
    public void showTriviaHelp() {
        System.out.println("Here are the available trivia commands:");
        System.out.println("  trivia list - Displays all available trivia categories.");
        System.out.println("    Use 'trivia list /l' to list all questions in the current category.");
        System.out.println("  trivia add <question> | <answer> - Adds a new trivia question to the current category.");
        System.out.println("  trivia select <category_name> - Selects a specific trivia category to work with.");
        System.out.println("  trivia start - Starts a trivia quiz session.");
        System.out.println("  trivia stop - Stops the current trivia quiz session.");
        System.out.println("  trivia delete <index> - Deletes a trivia question from the current category.");
        System.out.println("  trivia delete /c <category_name> - Deletes a trivia category.");
        System.out.println("  trivia create <category_name> - Creates a new, empty trivia category.");
        System.out.println("  trivia help - Displays this help message.");
    }

    /**
     * Displays a summary of all general commands and their functions.
     */
    public void showGeneralHelp() {
        System.out.println("Here are the available commands:");
        System.out.println("  list - Lists all tasks.");
        System.out.println("  todo <description> - Adds a new todo task.");
        System.out.println("  deadline <description> /by <datetime> - Adds a new deadline task.");
        System.out.println("  event <description> /from <datetime> /to <datetime> - Adds a new event task.");
        System.out.println("  mark <task_number> - Marks a task as done.");
        System.out.println("  unmark <task_number> - Marks a task as not done.");
        System.out.println("  delete <task_number> - Deletes a task.");
        System.out.println("  find <keyword> - Finds tasks containing the keyword.");
        System.out.println("  trivia <subcommand> - Accesses trivia commands (e.g., 'trivia help').");
        System.out.println("  bye - Exits the application.");
        System.out.println("  help - Displays this help message.");
    }
}
