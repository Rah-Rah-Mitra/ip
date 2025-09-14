package jettvarkis.trivia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages a list of trivia items.
 */
public class TriviaList {
    private List<Trivia> triviaItems;

    /**
     * Constructs an empty TriviaList.
     */
    public TriviaList() {
        this.triviaItems = new ArrayList<>();
    }

    /**
     * Constructs a TriviaList with a given list of trivia items.
     *
     * @param triviaItems The initial list of trivia items.
     */
    public TriviaList(List<Trivia> triviaItems) {
        this.triviaItems = triviaItems;
    }

    /**
     * Adds a trivia item to the list.
     *
     * @param trivia The trivia item to add.
     */
    public void add(Trivia trivia) {
        triviaItems.add(trivia);
    }

    /**
     * Deletes a trivia item from the list by index.
     *
     * @param index The index of the trivia item to delete.
     * @return The deleted trivia item.
     */
    public Trivia delete(int index) {
        return triviaItems.remove(index);
    }

    /**
     * Gets a trivia item from the list by index.
     *
     * @param index The index of the trivia item to get.
     * @return The trivia item at the given index.
     */
    public Trivia get(int index) {
        return triviaItems.get(index);
    }

    /**
     * Gets the number of trivia items in the list.
     *
     * @return The size of the list.
     */
    public int size() {
        return triviaItems.size();
    }

    /**
     * Gets a random trivia item from the list and removes it.
     *
     * @return A random trivia item, or null if the list is empty.
     */
    public Trivia getRandomTrivia() {
        if (triviaItems.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(triviaItems.size());
        return triviaItems.remove(index); // Remove and return the item
    }

    /**
     * Returns the list of all trivia items.
     *
     * @return The list of trivia items.
     */
    public List<Trivia> getAllTrivia() {
        return triviaItems;
    }
}
