package jettvarkis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JettVarkisTest {

    private static final String FILE_PATH = "data/jettvarkis.txt";

    @BeforeEach
    public void setUp() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JettVarkisTest {

    @Test
    public void testJettVarkis() {
        String input = "todo borrow book\n" +
                "list\n" +
                "deadline return book /by Sunday\n" +
                "event project meeting /from Mon 2pm /to 4pm\n" +
                "bye\n";

        String expectedOutput = "Jett Varkis at your service. What can I get for you?\n" +
                "Got it. I've added this task:\n" +
                "  [T][ ] borrow book\n" +
                "Now you have 1 tasks in the list.\n" +
                "Here are the tasks in your list:\n" +
                "1.[T][ ] borrow book\n" +
                "Got it. I've added this task:\n" +
                "  [D][ ] return book (by: Sunday)\n" +
                "Now you have 2 tasks in the list.\n" +
                "Got it. I've added this task:\n" +
                "  [E][ ] project meeting (from: Mon 2pm to: 4pm)\n" +
                "Now you have 3 tasks in the list.\n" +
                "Leaving so soon? See you next time.\n";

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            JettVarkis.main(new String[0]);

            assertEquals(expectedOutput.trim().replaceAll("\r\n", "\n"),
                    baos.toString().trim().replaceAll("\r\n", "\n"));
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
