package jettvarkis.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import jettvarkis.JettVarkis;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private JettVarkis jettVarkis;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/JettUser.jpg"));
    private Image jettVarkisImage = new Image(this.getClass().getResourceAsStream("/images/JettVarkis.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setJettVarkis(JettVarkis d) {
        assert d != null;
        jettVarkis = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Jett Varkis's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        assert userInput != null;
        String input = userInput.getText();
        assert jettVarkis != null : "JettVarkis instance not set";
        String response = jettVarkis.getResponse(input);
        assert dialogContainer != null : "Dialog container is null";
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJettVarkisDialog(response, jettVarkisImage));
        userInput.clear();
    }
}
