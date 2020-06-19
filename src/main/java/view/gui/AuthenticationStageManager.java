package view.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthenticationStageManager {

    private Stage stage;
    private BorderPane root;

    public void  setStage(Stage stage) {
        this.stage = stage;
    }

    public void switchToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/LoginPage.fxml"));
        root = loader.load();
        stage.setScene(new Scene(root));
    }

    public void switchToRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/RegisterMenu.fxml"));
        root = loader.load();
        stage.setScene(new Scene(root));
    }

}
