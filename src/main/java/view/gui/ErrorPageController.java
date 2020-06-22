package view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ErrorPageController {

    @FXML
    private TextField errorMessage;
    @FXML
    private Button okButton;


    public void setText(String errorMessage) {
        this.errorMessage.setText(errorMessage);
    }

    public Button getButton() {
        return okButton;
    }

}
