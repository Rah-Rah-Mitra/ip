package jettvarkis.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import jettvarkis.JettVarkis;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends VBox { // Changed from AnchorPane to VBox
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private JettVarkis jettVarkis;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/JettUser.jpg"));
    private final Image jettVarkisImage = new Image(this.getClass().getResourceAsStream("/images/JettVarkis.jpg"));

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Sets the JettVarkis instance for the controller.
     *
     * @param d The JettVarkis instance.
     */
    public void setJettVarkis(JettVarkis d) {
        assert d != null;
        jettVarkis = d;
        // Add welcome message
        dialogContainer.getChildren().add(
                DialogBox.getJettVarkisDialog(jettVarkis.getWelcomeMessage(), jettVarkisImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * JettVarkis's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        assert userInput != null;
        String input = userInput.getText();
        if (input.isBlank()) {
            return;
        }

        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        assert jettVarkis != null : "JettVarkis instance not set";
        String response = jettVarkis.getResponse(input);

        if (response.startsWith("Error:")) {
            dialogContainer.getChildren().add(DialogBox.getErrorDialog(response, jettVarkisImage));
        } else {
            dialogContainer.getChildren().add(DialogBox.getJettVarkisDialog(response, jettVarkisImage));
        }

        userInput.clear();
    }
}
