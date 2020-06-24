package view.gui.authentication;

import controller.account.Account;
import controller.interfaces.account.IAuthenticationController;
import exception.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Role;
import view.cli.ControllerContainer;
import view.gui.Constants;

public class FirstAdminRegister {

    @FXML
    private TextField usernameText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField confirmText;
    @FXML
    private Button registerButton;
    @FXML
    private Label errorLabel;

    private IAuthenticationController controller;


    private void register() {
        controller = (IAuthenticationController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.AuthenticationController);
        String username = usernameText.getText();
        String email = emailText.getText();
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String password = passwordText.getText();
        if(isAnyBlank()) {
            errorLabel.setText("Please fill all of the boxes.");
        } else if(!password.equals(confirmText.getText())) {
            errorLabel.setText("Your password don't match");
        } else {
            try {
                Account adminAccount = createAccount(username,email,firstName,lastName,password);
                controller.register(adminAccount,Constants.manager.getToken());
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            } catch (AlreadyLoggedInException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            } catch (InvalidAuthenticationException e) {
                e.printStackTrace();
            } catch (NoAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Account createAccount(String username, String email, String firstName, String lastName, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPassword(password);
        account.setRole(Role.ADMIN);
        return account;
    }

    private boolean isAnyBlank() {
        return (usernameText.getText().isBlank() || emailText.getText().isBlank() || firstNameText.getText().isBlank()
        || lastNameText.getText().isBlank() || passwordText.getText().isBlank() || confirmText.getText().isBlank());
    }
}
