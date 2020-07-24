package client.gui;

import client.ControllerContainer;
import client.connectionController.interfaces.IBankController;
import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.Reloadable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.format.TextStyle;

public class BankTokenPageController implements InitializableController {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorLabel;
    @FXML
    private Button button;
    private Reloadable reloadable;
    private IBankController bankController;

    @Override
    public void initialize(int id) throws IOException {
        bankController = (IBankController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.BankController);
        button.setOnAction(e -> {
            if (userName.getText().isBlank() || password.getText().isBlank()) {
                errorLabel.setText("fill all");
                return;
            }
            String message = null;
            try {
                message = bankController.getToken(userName.getText(), password.getText(), Constants.manager.getToken());
            } catch (InvalidTokenException ex) {
                try {
                    Constants.manager.showErrorPopUp(ex.getMessage());
                    Constants.manager.setTokenFromController();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
                Constants.manager.setTokenFromController();
            }
            if (message.equals("invalid username or password")) {
                errorLabel.setText("invalid username or password");
                return;
            }
            try {
                reloadable.reload();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    public void setReloadable(Reloadable reloadable) {
        this.reloadable = reloadable;
    }
}
