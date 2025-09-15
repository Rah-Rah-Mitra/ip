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
        return "Ah, another soul seeking organization in this fleeting existence. "
                + "I am Jett Varkis, keeper of tasks and memories. "
                + "What burdens shall we catalog today?";
    }

    /**
     * Displays a goodbye message to the user.
     */
    public void showGoodbye() {
        System.out.println("Time flows onward, as it always does. Until our paths cross again in this endless journey, "
                + "may your tasks be completed and your memories be cherished. Farewell for now.");
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
        System.out.println("Another entry inscribed in the grimoire of existence:");
        System.out.println("  " + task);
        System.out.println("Your collection of mortal endeavors now contains " + taskCount + " items. "
                + "How quickly they accumulate...");
    }

    /**
     * Displays all tasks in the list.
     *
     * @param tasks
     *            The ArrayList of tasks to be displayed.
     */
    public void showTasks(ArrayList<Task> tasks) {
        assert tasks != null;
        System.out.println("Behold, the chronicles of your endeavors, preserved through time:");
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
        System.out.println("Excellent. These chapters of your story reach their conclusion:");
        markedTasks.forEach(task -> System.out.println("  " + task));
        System.out.println("Another small victory against the passage of time.");
    }

    /**
     * Displays a message indicating that a task has been marked as not done.
     *
     * @param unmarkedTasks
     *            The list of tasks that were unmarked.
     */
    public void showUnmarkedTasks(List<Task> unmarkedTasks) {
        assert unmarkedTasks != null;
        System.out.println("Ah, it seems these tales require more time to unfold:");
        unmarkedTasks.forEach(task -> System.out.println("  " + task));
        System.out.println("Sometimes, the journey takes longer than expected. This I understand well.");
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
        System.out.println("These memories fade back into the void, as all things eventually do:");
        deletedTasks.forEach(task -> System.out.println("  " + task));
        System.out.println("Your grimoire now holds " + taskCount + " remaining endeavors. "
                + "Perhaps it's for the best - some burdens are meant to be released.");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message
     *            The error message to be displayed.
     */
    public void showError(String message) {
        assert message != null;
        System.out.println("    *sigh* Even after all these centuries... " + message);
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
            System.out.println("The mists of time have obscured such memories from your collection. "
                    + "Even I, with my long years, sometimes forget where I placed things...");
        } else {
            System.out.println("Ah, these fragments of memory surface from the depths of your grimoire:");
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
            System.out.println("The archives appear empty... In time, knowledge will accumulate here.");
        } else {
            System.out.println("These are the realms of knowledge preserved in the archives:");
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
        System.out.println("Another fragment of wisdom has been preserved in the '" + category + "' archives:");
        System.out.println("  " + trivia);
        System.out.println("The collection now holds " + triviaCount + " pieces of knowledge. "
                + "Slowly, understanding accumulates across the centuries.");
    }

    /**
     * Displays a message indicating that a trivia category has been selected.
     *
     * @param category
     *            The selected category.
     */
    public void showTriviaCategorySelected(String category, int triviaCount) {
        System.out.println("Your focus now rests upon the '" + category + "' archive, "
                + "which contains " + triviaCount + " fragments of ancient wisdom. "
                + "Knowledge awaits your contemplation.");
    }

    /**
     * Displays a message indicating the start of a trivia quiz.
     *
     * @param category
     *            The category of the quiz.
     */
    public void showTriviaStart(String category) {
        System.out.println("The trial of wisdom begins for the '" + category + "' archive! "
                + "Let us see what knowledge time has preserved in your memory...");
    }

    /**
     * Displays a message indicating the end of a trivia quiz.
     */
    public void showTriviaStop() {
        System.out.println("The trial concludes. Knowledge tested, understanding measured. "
                + "Until curiosity stirs again, rest well.");
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
        System.out.println("This knowledge fades from the '" + category + "' archive, returning to silence:");
        System.out.println("  " + trivia);
        System.out.println("The archive now preserves " + triviaCount + " remaining fragments. "
                + "Some wisdom, it seems, was not meant to endure.");
    }

    /**
     * Displays a trivia question.
     *
     * @param trivia
     *            The trivia question to display.
     */
    public void showTriviaQuestion(Trivia trivia) {
        assert trivia != null;
        System.out.println("From the depths of memory, a query emerges: " + trivia.getQuestion());
    }

    /**
     * Displays a message for a correct answer.
     */
    public void showCorrectAnswer() {
        System.out.println("Indeed, your memory serves you well. Time has not dulled this knowledge.");
    }

    /**
     * Displays a message for an incorrect answer, showing the correct answer.
     *
     * @param correctAnswer
     *            The correct answer.
     */
    public void showIncorrectAnswer(String correctAnswer) {
        System.out.println("Alas, even the sharpest minds occasionally stumble. "
                + "The answer dwells in: " + correctAnswer + ". Fear not, for learning is eternal.");
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
        System.out.println("Behold, the accumulated wisdom within the '" + category + "' archive:");
        if (triviaList.size() == 0) {
            System.out.println("  This archive rests empty, awaiting the patient accumulation of knowledge.");
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
        System.out.println("A new realm of knowledge '" + categoryName + "' has been manifested. "
                + "In time, wisdom shall fill this empty vessel.");
    }

    /**
     * Displays a message indicating that a trivia category has been deleted.
     *
     * @param categoryName The name of the deleted category.
     */
    public void showTriviaCategoryDeleted(String categoryName) {
        System.out.println("The '" + categoryName + "' archive dissolves into the void, "
                + "its knowledge released back to the eternal silence. Such is the way of all things.");
    }

    /**
     * Displays a summary of all trivia commands and their functions.
     */
    public void showTriviaHelp() {
        System.out.println("The ancient archives of knowledge await your exploration:");
        System.out.println("  trivia list - Unveils all repositories of wisdom in the collection.");
        System.out.println("    Use 'trivia list /l' to examine all queries within the current archive.");
        System.out.println("  trivia add <question> | <answer> - Inscribes new knowledge into the current archive.");
        System.out.println("  trivia select <category_name> - Focuses your attention on a specific knowledge realm.");
        System.out.println("  trivia start - Begins a trial of memory and wisdom.");
        System.out.println("  trivia stop - Concludes the trial, returning to peaceful contemplation.");
        System.out.println("  trivia delete <index> - Allows knowledge to fade from the current archive.");
        System.out.println("  trivia delete /c <category_name> - Dissolves an entire realm of knowledge.");
        System.out.println("  trivia create <category_name> - Manifests a new, empty archive of wisdom.");
        System.out.println("  trivia help - Reveals these mystical instructions once more.");
    }

    /**
     * Displays a summary of all general commands and their functions.
     */
    public void showGeneralHelp() {
        System.out.println("Behold, the ancient incantations for managing your mortal endeavors:");
        System.out.println("  list - Reveals all chronicles within your grimoire.");
        System.out.println("  todo <description> - Inscribes a simple task into the tome of time.");
        System.out.println("  deadline <description> /by <datetime> - Binds a task to temporal flow.");
        System.out.println("  event <description> /from <datetime> /to <datetime> - Records a moment in time.");
        System.out.println("  mark <task_number> - Seals a task as completed, preserving its memory.");
        System.out.println("  unmark <task_number> - Reopens a sealed task, for time allows revisions.");
        System.out.println("  delete <task_number> - Releases a task back to the void.");
        System.out.println("  find <keyword> - Searches the depths of memory for forgotten traces.");
        System.out.println("  trivia <subcommand> - Accesses the ancient knowledge archives (try 'trivia help').");
        System.out.println("  bye - Concludes our session, until time brings us together again.");
        System.out.println("  help - Displays these mystical instructions once more.");
    }
}
