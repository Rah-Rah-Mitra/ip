package jettvarkis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jettvarkis.command.Command;
import jettvarkis.command.trivia.TriviaAddCommand;
import jettvarkis.command.trivia.TriviaCreateCommand;
import jettvarkis.command.trivia.TriviaDeleteCommand;
import jettvarkis.command.trivia.TriviaSelectCommand;
import jettvarkis.exception.JettVarkisException;
import jettvarkis.storage.Storage;
import jettvarkis.trivia.Trivia;
import jettvarkis.trivia.TriviaList;
import jettvarkis.ui.Ui;

public class TriviaCommandsTest {

    private Storage storage;
    private Ui ui;
    private JettVarkis jettVarkis;

    @BeforeEach
    public void setUp() {
        // Set up a temporary storage for testing
        storage = new Storage("data/test_tasks.txt");
        ui = new Ui();
        jettVarkis = new JettVarkis("data/test_tasks.txt");
        // Clean up trivia files before each test
        File triviaDir = new File("data/trivia");
        if (triviaDir.exists()) {
            for (File file : triviaDir.listFiles()) {
                file.delete();
            }
        }
    }

    @Test
    public void testCreateAndSelectTriviaCategory() throws JettVarkisException {
        // 1. Create a new category
        Command createCommand = new TriviaCreateCommand("test_category");
        createCommand.execute(ui, null, storage, jettVarkis);
        List<String> categories = storage.getTriviaCategories();
        assertTrue(categories.contains("test_category"), "Test Category should exist after creation");

        // 2. Select the new category
        Command selectCommand = new TriviaSelectCommand("test_category");
        selectCommand.execute(ui, null, storage, jettVarkis);
        assertEquals("test_category", jettVarkis.getCurrentTriviaCategory(),
                "Current category should be 'test_category'");
    }

    @Test
    public void testAddTriviaToSelectedCategory() throws JettVarkisException {
        // 1. Create and select a category
        new TriviaCreateCommand("custom_category").execute(ui, null, storage, jettVarkis);
        new TriviaSelectCommand("custom_category").execute(ui, null, storage, jettVarkis);

        // 2. Add a trivia question
        Command addCommand = new TriviaAddCommand("What is the capital of France?", "Paris");
        addCommand.execute(ui, null, storage, jettVarkis);

        // 3. Verify the trivia was added to the correct category
        TriviaList triviaList = storage.loadTrivia("custom_category");
        assertEquals(1, triviaList.size(), "Trivia list for 'custom_category' should have 1 item");
        Trivia trivia = triviaList.get(0);
        assertEquals("What is the capital of France?", trivia.getQuestion());
        assertEquals("Paris", trivia.getAnswer());
    }

    @Test
    public void testAddTriviaToDefaultCategory() throws JettVarkisException {
        // Add a trivia question without selecting a category
        Command addCommand = new TriviaAddCommand("What is 2 + 2?", "4");
        addCommand.execute(ui, null, storage, jettVarkis);

        // Verify the trivia was added to the default category
        TriviaList triviaList = storage.loadTrivia("default");
        assertEquals(1, triviaList.size(), "Trivia list for 'default' should have 1 item");
        Trivia trivia = triviaList.get(0);
        assertEquals("What is 2 + 2?", trivia.getQuestion());
        assertEquals("4", trivia.getAnswer());
    }

    @Test
    public void testDeleteTriviaQuestion() throws JettVarkisException {
        // 1. Add a trivia question to the default category
        new TriviaAddCommand("Question 1", "Answer 1").execute(ui, null, storage, jettVarkis);
        new TriviaAddCommand("Question 2", "Answer 2").execute(ui, null, storage, jettVarkis);

        // 2. Delete the first trivia question
        Command deleteCommand = new TriviaDeleteCommand(0); // Delete the first item (index 0)
        deleteCommand.execute(ui, null, storage, jettVarkis);

        // 3. Verify the question was deleted
        TriviaList triviaList = storage.loadTrivia("default");
        assertEquals(1, triviaList.size(), "Trivia list should have 1 item after deletion");
        assertEquals("Question 2", triviaList.get(0).getQuestion(),
                "The remaining question should be 'Question 2'");
    }

    @Test
    public void testDeleteTriviaCategory() throws JettVarkisException {
        // 1. Create a category and add a question
        new TriviaCreateCommand("temp_category").execute(ui, null, storage, jettVarkis);
        new TriviaSelectCommand("temp_category").execute(ui, null, storage, jettVarkis);
        new TriviaAddCommand("Temp Question", "Temp Answer").execute(ui, null, storage, jettVarkis);

        // 2. Delete the category
        Command deleteCategoryCommand = new TriviaDeleteCommand("temp_category");
        deleteCategoryCommand.execute(ui, null, storage, jettVarkis);

        // 3. Verify the category is deleted
        List<String> categories = storage.getTriviaCategories();
        assertFalse(categories.contains("temp_category"), "Category 'temp_category' should be deleted");

        // 4. Verify that the current category is reset to default
        assertEquals("default", jettVarkis.getCurrentTriviaCategory(),
                "Current category should be reset to 'default'");
    }

    @Test
    public void testDeleteTriviaWithInvalidIndex() {
        // Add a trivia question
        try {
            new TriviaAddCommand("Question", "Answer").execute(ui, null, storage, jettVarkis);
        } catch (JettVarkisException e) {
            // Should not happen
        }

        // Attempt to delete with an invalid index
        Command deleteCommand = new TriviaDeleteCommand(10); // Index 10 is out of bounds
        JettVarkisException exception = org.junit.jupiter.api.Assertions.assertThrows(JettVarkisException.class, () -> {
            deleteCommand.execute(ui, null, storage, jettVarkis);
        });

        assertEquals(JettVarkisException.ErrorType.INVALID_TRIVIA_INDEX, exception.getErrorType());
    }

    @Test
    public void testDeleteNonExistentTriviaCategory() {
        // Attempt to delete a category that does not exist
        Command deleteCommand = new TriviaDeleteCommand("non_existent_category");
        JettVarkisException exception = org.junit.jupiter.api.Assertions.assertThrows(JettVarkisException.class, () -> {
            deleteCommand.execute(ui, null, storage, jettVarkis);
        });

        assertEquals(JettVarkisException.ErrorType.TRIVIA_CATEGORY_NOT_FOUND, exception.getErrorType());
    }

    @Test
    public void testSelectNonExistentTriviaCategory() throws JettVarkisException {
        // Select a category that does not exist
        Command selectCommand = new TriviaSelectCommand("new_category");
        selectCommand.execute(ui, null, storage, jettVarkis);

        // Verify that the category is created and selected
        assertEquals("new_category", jettVarkis.getCurrentTriviaCategory());
        List<String> categories = storage.getTriviaCategories();
        assertTrue(categories.contains("new_category"));
    }

    @Test
    public void testCreateExistingTriviaCategory() throws JettVarkisException {
        // Create a category
        new TriviaCreateCommand("existing_category").execute(ui, null, storage, jettVarkis);

        // Attempt to create the same category again
        Command createCommand = new TriviaCreateCommand("existing_category");
        JettVarkisException exception = org.junit.jupiter.api.Assertions.assertThrows(JettVarkisException.class, () -> {
            createCommand.execute(ui, null, storage, jettVarkis);
        });

        assertEquals(JettVarkisException.ErrorType.TRIVIA_CATEGORY_ALREADY_EXISTS, exception.getErrorType());
    }
}
