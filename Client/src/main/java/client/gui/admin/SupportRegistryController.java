package client.gui.admin;

import client.ControllerContainer;
import client.connectionController.interfaces.account.IAuthenticationController;
import client.exception.*;
import client.gui.Constants;
import client.model.Account;
import client.model.enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.hibernate.boot.model.naming.ImplicitAnyDiscriminatorColumnNameSource;

import java.io.IOException;

public class SupportRegistryController {

    private IAuthenticationController authenticationController;

    @FXML
    private Label errorLabel;
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button registerButton;


    public SupportRegistryController() {
        authenticationController = (IAuthenticationController) Constants.manager.getControllerContainer().getController(
                ControllerContainer.Controller.AuthenticationController
        );
        registerButton.setOnMouseClicked(e -> registerSupport());
    }

    private void registerSupport() {
        String username = usernameText.getText();
        String password = passwordText.getText();
        if(username.isBlank() || password.isBlank()) {
            errorLabel.setText("Please fill all the boxes.");
        } else {
            Account support = new Account();
            support.setUsername(username);
            support.setPassword(password);
            support.setRole(Role.SUPPORT);
            try {
                authenticationController.register(support, Constants.manager.getToken());
                Constants.manager.showSuccessPopUp("Registry was Successful.");
                reload();
            } catch (InvalidTokenException e) {
                errorLabel.setText(e.getMessage());
            } catch (AlreadyLoggedInException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidFormatException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidAuthenticationException e) {
                errorLabel.setText(e.getMessage());
            } catch (NoAccessException e) {
                errorLabel.setText(e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void reload() {
        usernameText.clear();
        passwordText.clear();
        errorLabel.setText("");
    }

}
