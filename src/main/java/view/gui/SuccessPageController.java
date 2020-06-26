package view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SuccessPageController {

    @FXML
    private TextField message;
    @FXML
    private Button okButton;

    public void load(String errorMessage) {
        this.message.setText(errorMessage);
    }

    public Button getButton() {
        return okButton;
    }
}
