package client.gui;

import client.gui.interfaces.InitializableController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ComparePane implements InitializableController {
    @FXML
    private ScrollPane scrollPaneForCompare;
    @FXML
    private HBox box;
    @FXML
    private Button exitButton;

    public Button getExitButton(){
        return exitButton;
    }

    public void addToBox(Node node) {
        box.getChildren().addAll(node);
    }

    @Override
    public void initialize(int id) throws IOException {
        //TODO nothing
    }
}
