package client.gui;

import client.ControllerContainer;
import client.connectionController.interfaces.IBankController;
import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.Reloadable;
import client.model.enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.time.format.TextStyle;

public class BankAccountCreatingController implements InitializableController {
    private int userId;
    private Role role;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField repeatTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button registerButton;
    @FXML
    private Button chargeButton;
    @FXML
    private Button withDrawButton;
    @FXML
    private TextField chargeAccountId;
    @FXML
    private TextField chargeAmount;
    @FXML
    private TextField withDrawAccountId;
    @FXML
    private TextField withDrawAmount;
    @FXML
    private Label getMoneyLabel;

    private IBankController bankController;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        bankController = (IBankController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.BankController);
        this.userId = id;
        withDrawAccountId.setVisible(false);
        withDrawAmount.setVisible(false);
        withDrawButton.setVisible(false);
        getMoneyLabel.setVisible(false);
        chargeButton.setOnMouseClicked(e -> {
            chargeAccount();
        });
        registerButton.setOnMouseClicked(e -> {
            try {
                registerBankAccount();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void chargeAccount() {


    }

    public void load(Role role) {
        if (role == Role.CUSTOMER) {
            withDrawAccountId.setVisible(true);
            withDrawAmount.setVisible(true);
            withDrawButton.setVisible(true);
            getMoneyLabel.setVisible(true);
            withDrawButton.setOnMouseClicked(e -> {
                withDrawButtonClicked();
            });
        }
    }

    private void withDrawButtonClicked() {

    }


    public void registerBankAccount() throws IOException {
        if (isEveryThingOk()) {
            String message = bankController.createAccount(userNameTextField.getText(), passwordTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), repeatTextField.getText());
            if (message.equals(Constants.bankErrorInvalidPassword)) {
                Constants.manager.showErrorPopUp(Constants.bankErrorInvalidPassword);
            } else if (message.equals(Constants.bankErrorRepeatedUserName)) {
                Constants.manager.showErrorPopUp(Constants.bankErrorRepeatedUserName);
            } else {
                Constants.manager.showSuccessPopUp("your account id is : " + message);
            }
        }
    }

    private boolean isEveryThingOk() {
        if (isItWithoutSpace(userNameTextField.getText()) || userNameTextField.getText().isEmpty()) {
            errorLabel.setText("user name should be without space");
            return false;
        }
        if (isItWithoutSpace(passwordTextField.getText()) || passwordTextField.getText().isEmpty()) {
            errorLabel.setText("pass word should be without space");
            return false;
        }
        if (isItWithoutSpace(firstNameTextField.getText()) || firstNameTextField.getText().isEmpty()) {
            errorLabel.setText("first name should be without space");
            return false;
        }
        if (isItWithoutSpace(lastNameTextField.getText()) || lastNameTextField.getText().isEmpty()) {
            errorLabel.setText("last name should be without space");
            return false;
        }
        if (isItWithoutSpace(repeatTextField.getText()) || repeatTextField.getText().isEmpty()) {
            errorLabel.setText("user name should be without space");
            return false;
        }
        if (isItWithoutSpace(userNameTextField.getText()) || userNameTextField.getText().isEmpty()) {
            errorLabel.setText("user name should be without space");
            return false;
        }
        return true;
    }

    private boolean isItWithoutSpace(String input) {
        if (input.split(" ").length == 1) {
            return true;
        }
        return false;
    }
}
