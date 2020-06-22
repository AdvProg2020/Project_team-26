package view.gui;

import controller.account.Account;
import controller.account.AuthenticationController;
import controller.interfaces.account.IAuthenticationController;
import exception.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Role;
import view.cli.ControllerContainer;

import java.io.IOException;

public class AdminRegistryController implements InitializableController {

    @FXML
    private Button submitButton;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField emailText;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField confirmText;

    private IAuthenticationController controller;

    public void register() {
        controller = (AuthenticationController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.AuthenticationController);
        if(isAnyEmpty()) {
            errorLabel.setText("Please fill all the boxes");
        }  else if (!passwordText.getText().equals(confirmText.getText())) {
            errorLabel.setText("Your passwords don't match");
        } else {
            Account account = createAccount();
            try {
                controller.register(account,Constants.manager.getToken());
            } catch (NoAccessException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            } catch (InvalidAuthenticationException e) {
                e.printStackTrace();
            } catch (AlreadyLoggedInException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAnyEmpty() {
        return (usernameText.getText().isEmpty() || passwordText.getText().isBlank() || firstNameText.getText().isBlank()
                || lastNameText.getText().isBlank() || confirmText.getText().isBlank() || emailText.getText().isBlank());
    }

    private Account createAccount() {
        Account account = new  Account(usernameText.getText(),passwordText.getText(), Role.ADMIN,emailText.getText());
        account.setFirstName(firstNameText.getText());
        account.setLastName(lastNameText.getText());
        return account;
    }


    @Override
    public void initialize(int id) throws IOException {

    }
}
