package jettvarkis;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jettvarkis.gui.MainWindow;

/**
 * A GUI for Jett Varkis using FXML.
 */
public class Main extends Application {

    private JettVarkis jettVarkis = new JettVarkis("data/jettvarkis.txt");

    @Override
    public void start(Stage stage) {
        assert stage != null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            VBox ap = fxmlLoader.load();
            assert ap != null : "FXML loading failed, VBox is null";
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setJettVarkis(jettVarkis);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
