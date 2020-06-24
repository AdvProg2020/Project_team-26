package view.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import view.gui.interfaces.InitializableController;

import java.io.IOException;

public class ComparePane implements InitializableController {
    @FXML
    private ScrollPane scrollPaneForCompare;
    @FXML
    private HBox box;

    public void addToBox(Node node) {
        box.getChildren().addAll(node);
    }

    @Override
    public void initialize(int id) throws IOException {
        //TODO nothing
    }
}
