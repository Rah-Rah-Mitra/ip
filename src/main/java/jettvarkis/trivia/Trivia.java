package jettvarkis.trivia;

/**
 * Represents a single trivia item, with a question and an answer.
 */
public class Trivia {
    private String question;
    private String answer;

    /**
     * Constructs a Trivia object.
     *
     * @param question The question for the trivia item.
     * @param answer The answer to the question.
     */
    public Trivia(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Gets the question of the trivia item.
     *
     * @return The question.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Gets the answer of the trivia item.
     *
     * @return The answer.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns a string representation of the trivia item for saving to a file.
     * The format is "question | answer".
     *
     * @return A formatted string for file storage.
     */
    public String toFileFormat() {
        return question + " | " + answer;
    }

    /**
     * Returns a string representation of the trivia item.
     *
     * @return A string in the format "Question: [question] Answer: [answer]".
     */
    @Override
    public String toString() {
        return "Question: " + question + " Answer: " + answer;
    }
}
