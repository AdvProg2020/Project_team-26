package view.gui;

import controller.account.AuthenticationController;
import controller.interfaces.account.IAuthenticationController;
import exception.InvalidAuthenticationException;
import exception.InvalidFormatException;
import exception.InvalidTokenException;
import exception.PasswordIsWrongException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import view.cli.ControllerContainer;

import java.io.IOException;

public class LoginPageController implements InitializableController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink registerHyperLink;
    @FXML
    private Label errorLabel;

    private IAuthenticationController controller;



    public void login(ActionEvent actionEvent) {
        controller = (AuthenticationController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.AuthenticationController);
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(username.isBlank() || password.isBlank()) {
            errorLabel.setText("Please Fill all of the boxes.");
        } else {
            try {
                controller.login(username, password, Constants.manager.getToken());
                Constants.manager.setLoggedIn(true);
                return;
            } catch (InvalidTokenException e) {
                Constants.manager.setTokenFromController(e.getMessage() + "\nnew token will be set try again");
                return;
            } catch (InvalidFormatException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidAuthenticationException e) {
                errorLabel.setText(e.getMessage());
            } catch (PasswordIsWrongException e) {
                errorLabel.setText("Your password is Wrong.");
            }
        }
    }

    @FXML
    private void redirectToRegister(ActionEvent actionEvent) throws IOException {
        Constants.manager.switchScene("Register");
    }


    @Override
    public void initialize(int id) throws IOException {

    }
}
