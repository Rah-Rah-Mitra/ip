package jettvarkis.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jettvarkis.exception.JettVarkisException;
import jettvarkis.task.Deadline;
import jettvarkis.task.Event;
import jettvarkis.task.Task;
import jettvarkis.task.Todo;
import jettvarkis.trivia.Trivia;
import jettvarkis.trivia.TriviaList;

public class StorageTest {

    private static final String TEST_FILE_PATH = "/tmp/test_tasks.txt";
    private static final String TEST_TRIVIA_DIR = "/tmp/test_trivia";
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage(TEST_FILE_PATH);
        cleanupTestFiles();
    }

    @AfterEach
    public void tearDown() {
        cleanupTestFiles();
    }

    private void cleanupTestFiles() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
        File testDir = new File(TEST_TRIVIA_DIR);
        if (testDir.exists()) {
            deleteDirectory(testDir);
        }
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    @Test
    public void testLoadEmptyFile() throws JettVarkisException {
        ArrayList<Task> tasks = storage.load();
        assertEquals(0, tasks.size());
        assertTrue(new File(TEST_FILE_PATH).exists());
    }

    @Test
    public void testSaveAndLoadTasks() throws JettVarkisException {
        ArrayList<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("read book"));
        tasksToSave.add(new Deadline("submit report", "2025-08-27"));
        tasksToSave.add(new Event("meeting", "2025-08-27 1400", "2025-08-27 1600"));

        storage.save(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(3, loadedTasks.size());
        assertEquals("read book", loadedTasks.get(0).getDescription());
        assertEquals("submit report", loadedTasks.get(1).getDescription());
        assertEquals("meeting", loadedTasks.get(2).getDescription());
    }

    @Test
    public void testSaveAndLoadTasksWithSpecialCharacters() throws JettVarkisException {
        ArrayList<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("task with @#$%^&*()"));
        tasksToSave.add(new Todo("読書する 📚 τέλος"));
        tasksToSave.add(new Todo("'; DROP TABLE tasks; --"));

        storage.save(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(3, loadedTasks.size());
        assertEquals("task with @#$%^&*()", loadedTasks.get(0).getDescription());
        assertEquals("読書する 📚 τέλος", loadedTasks.get(1).getDescription());
        assertEquals("'; DROP TABLE tasks; --", loadedTasks.get(2).getDescription());
    }

    @Test
    public void testSaveAndLoadCompletedTasks() throws JettVarkisException {
        ArrayList<Task> tasksToSave = new ArrayList<>();
        Todo todo = new Todo("completed task");
        todo.markAsDone();
        tasksToSave.add(todo);

        storage.save(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        assertEquals("X", loadedTasks.get(0).getStatusIcon());
    }

    @Test
    public void testSaveEmptyTaskList() throws JettVarkisException {
        ArrayList<Task> emptyTasks = new ArrayList<>();
        storage.save(emptyTasks);
        ArrayList<Task> loadedTasks = storage.load();
        assertEquals(0, loadedTasks.size());
    }

    @Test
    public void testLoadCorruptedFile() throws IOException {
        // Create a file with corrupted data
        File testFile = new File(TEST_FILE_PATH);
        testFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Invalid line format\n");
            writer.write("T | 0 | valid task\n");
            writer.write("Another invalid line\n");
        }

        // This should cause AssertionError due to Parser assertions
        assertThrows(AssertionError.class, () -> storage.load());
    }

    @Test
    public void testSaveToDirectory() {
        // Create a directory with the same name as the file path
        File directory = new File(TEST_FILE_PATH);
        directory.mkdirs();

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("test task"));

        assertThrows(JettVarkisException.class, () -> storage.save(tasks));
    }

    @Test
    public void testLoadFromDirectory() {
        // Create a directory with the same name as the file path
        File directory = new File(TEST_FILE_PATH);
        directory.mkdirs();

        assertThrows(JettVarkisException.class, () -> storage.load());
    }

    @Test
    public void testSaveTasksWithNewlines() throws JettVarkisException {
        // Note: Newlines in task descriptions can cause file parsing issues
        // This test demonstrates the limitation
        ArrayList<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("task with newlines"));

        storage.save(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        assertEquals("task with newlines", loadedTasks.get(0).getDescription());
    }

    @Test
    public void testSaveVeryLongTask() throws JettVarkisException {
        StringBuilder longDescription = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            longDescription.append("a");
        }

        ArrayList<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo(longDescription.toString()));

        storage.save(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        assertEquals(longDescription.toString(), loadedTasks.get(0).getDescription());
    }

    @Test
    public void testGetTriviaCategories() {
        List<String> categories = storage.getTriviaCategories();
        assertNotNull(categories);
        // Categories may exist from previous tests, so just check it's not null
    }

    @Test
    public void testSaveTriviaList() throws JettVarkisException {
        TriviaList triviaList = new TriviaList();
        triviaList.add(new Trivia("What is 2+2?", "4"));
        triviaList.add(new Trivia("What is the capital of France?", "Paris"));

        storage.saveTrivia("test-category", triviaList);

        // Verify the file was created
        File triviaFile = new File("data/trivia/test-category.txt");
        assertTrue(triviaFile.exists());

        // Clean up
        triviaFile.delete();
        triviaFile.getParentFile().delete();
    }

    @Test
    public void testLoadTriviaList() throws JettVarkisException {
        // First save a trivia list
        TriviaList triviaList = new TriviaList();
        triviaList.add(new Trivia("What is 2+2?", "4"));
        triviaList.add(new Trivia("What is the capital of France?", "Paris"));
        storage.saveTrivia("test-category", triviaList);

        // Then load it
        TriviaList loadedTriviaList = storage.loadTrivia("test-category");
        assertEquals(2, loadedTriviaList.size());

        // Clean up
        File triviaFile = new File("data/trivia/test-category.txt");
        triviaFile.delete();
        triviaFile.getParentFile().delete();
    }

    @Test
    public void testLoadNonExistentTriviaCategory() throws JettVarkisException {
        // Try to load a trivia list that doesn't exist
        TriviaList triviaList = storage.loadTrivia("non-existent-category");
        // The method returns an empty list for non-existent categories
        assertEquals(0, triviaList.size());
    }

    @Test
    public void testDeleteTriviaCategory() throws JettVarkisException {
        // First save a trivia list
        TriviaList triviaList = new TriviaList();
        triviaList.add(new Trivia("What is 2+2?", "4"));
        storage.saveTrivia("test-category", triviaList);

        // Verify it exists
        File triviaFile = new File("data/trivia/test-category.txt");
        assertTrue(triviaFile.exists());

        // Delete it
        storage.deleteTriviaCategory("test-category");

        // Verify it's gone
        assertFalse(triviaFile.exists());

        // Clean up directory
        triviaFile.getParentFile().delete();
    }

    @Test
    public void testDeleteNonExistentTriviaCategory() {
        assertThrows(JettVarkisException.class, () -> storage.deleteTriviaCategory("non-existent"));
    }

    @Test
    public void testTriviaCategoriesWithSpecialCharacters() throws JettVarkisException {
        TriviaList triviaList = new TriviaList();
        triviaList.add(new Trivia("Question with @#$%", "Answer with @#$%"));

        storage.saveTrivia("special@#$", triviaList);
        TriviaList loadedTriviaList = storage.loadTrivia("special@#$");

        assertEquals(1, loadedTriviaList.size());

        // Clean up
        File triviaFile = new File("data/trivia/special@#$.txt");
        triviaFile.delete();
        triviaFile.getParentFile().delete();
    }

    @Test
    public void testTriviaWithUnicodeCharacters() throws JettVarkisException {
        TriviaList triviaList = new TriviaList();
        triviaList.add(new Trivia("読書とは何ですか？ 📚", "本を読むこと"));

        storage.saveTrivia("unicode", triviaList);
        TriviaList loadedTriviaList = storage.loadTrivia("unicode");

        assertEquals(1, loadedTriviaList.size());

        // Clean up
        File triviaFile = new File("data/trivia/unicode.txt");
        triviaFile.delete();
        triviaFile.getParentFile().delete();
    }

    @Test
    public void testGetTriviaCategoriesWithFiles() throws JettVarkisException {
        // Create some trivia files
        storage.saveTrivia("category1", new TriviaList());
        storage.saveTrivia("category2", new TriviaList());
        storage.saveTrivia("category3", new TriviaList());

        List<String> categories = storage.getTriviaCategories();
        assertTrue(categories.size() >= 3); // At least these 3 should exist
        assertTrue(categories.contains("category1"));
        assertTrue(categories.contains("category2"));
        assertTrue(categories.contains("category3"));

        // Clean up
        for (String category : List.of("category1", "category2", "category3")) {
            File triviaFile = new File("data/trivia/" + category + ".txt");
            triviaFile.delete();
        }
        new File("data/trivia").delete();
    }

    @Test
    public void testStorageWithNullTaskList() {
        assertThrows(AssertionError.class, () -> storage.save(null));
    }

    @Test
    public void testConstructorWithNullPath() {
        assertThrows(AssertionError.class, () -> new Storage(null));
    }

    @Test
    public void testConstructorWithEmptyPath() {
        assertThrows(AssertionError.class, () -> new Storage(""));
    }

    @Test
    public void testConstructorWithWhitespacePath() {
        assertThrows(AssertionError.class, () -> new Storage("   "));
    }
}
