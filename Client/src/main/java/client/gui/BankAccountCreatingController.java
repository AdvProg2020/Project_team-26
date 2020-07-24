package client.gui;

import client.ControllerContainer;
import client.connectionController.interfaces.IBankController;
import client.exception.*;
import client.gui.interfaces.InitializableController;
import client.model.enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class BankAccountCreatingController implements InitializableController {
    private int userId;
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
            try {
                chargeAccount();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        registerButton.setOnMouseClicked(e -> {
            try {
                registerBankAccount();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void chargeAccount() throws IOException {
        if (Constants.manager.checkInputIsInt(chargeAccountId.getText()) && Constants.manager.checkIsLong(chargeAmount.getText())) {
            int accountId = Integer.parseInt(chargeAccountId.getText());
            long amount = Long.parseLong(chargeAmount.getText());
            String description = "charge";
            try {
                String message = bankController.chargeAccount(accountId, description, amount, Constants.manager.getToken());
                if (message.equals(Constants.bankErrorInvalidٍSource)) {
                    errorLabel.setText("your bank account id is invalid");
                    return;
                }
                if (message.equals(Constants.bankErrorInvalidToken) || message.equals(Constants.bankErrorExpiredToken)) {
                    errorLabel.setText("sorry error happened from server enter your bank account info");
                    Constants.manager.setTokenFromBankForBankTransaction();
                    return;
                }
                if(message.equals(Constants.bankErrorNotEnoughInSource)){
                    Constants.manager.showErrorPopUp(Constants.bankErrorNotEnoughInSource);
                    return;
                }
                Constants.manager.showSuccessPopUp(message);
            } catch (InvalidTokenException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
                Constants.manager.setTokenFromController();
            } catch (NoAccessException | NotLoggedINException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
            }
        } else {
            errorLabel.setText("invalid input format");
        }
    }

    public void load(Role role) {
        if (role == Role.CUSTOMER) {
            withDrawAccountId.setVisible(true);
            withDrawAmount.setVisible(true);
            withDrawButton.setVisible(true);
            getMoneyLabel.setVisible(true);
            withDrawButton.setOnMouseClicked(e -> {
                try {
                    withDrawButtonClicked();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    private void withDrawButtonClicked() throws IOException {
        if (Constants.manager.checkInputIsInt(withDrawAccountId.getText()) && Constants.manager.checkIsLong(withDrawAmount.getText())) {
            int accountId = Integer.parseInt(withDrawAccountId.getText());
            long amount = Long.parseLong(withDrawAmount.getText());
            String description = "withDraw";
            try {
                String message = bankController.withdrawFromAccount(accountId, description, amount, Constants.manager.getToken());
                if (message.equals(Constants.bankErrorInvalidٍSource)) {
                    errorLabel.setText("your bank account id is invalid");
                    return;
                }
                if (message.equals(Constants.bankErrorInvalidToken)) {
                    errorLabel.setText("sorry error happened from server enter your bank account info");
                    Constants.manager.setTokenFromBankForBankTransaction();
                    return;
                }
                if(message.equals(Constants.bankErrorNotEnoughInSource)){
                    Constants.manager.showErrorPopUp(Constants.bankErrorNotEnoughInSource);
                    return;
                }
                Constants.manager.showSuccessPopUp(message);
            } catch (InvalidTokenException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
                Constants.manager.setTokenFromController();
            } catch (NoAccessException | NotLoggedINException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
            } catch (NotEnoughCreditException e) {
                errorLabel.setText(e.getMessage());
            }
        } else {
            errorLabel.setText("invalid input format");
        }
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
        if (!isItWithoutSpace(userNameTextField.getText()) || userNameTextField.getText().isEmpty()) {
            errorLabel.setText("user name should be without space or empty");
            return false;
        }
        if (!isItWithoutSpace(passwordTextField.getText()) || passwordTextField.getText().isEmpty()) {
            errorLabel.setText("pass word should be without space or empty");
            return false;
        }
        if (!isItWithoutSpace(firstNameTextField.getText()) || firstNameTextField.getText().isEmpty()) {
            errorLabel.setText("first name should be without space  or empty");
            return false;
        }
        if (!isItWithoutSpace(lastNameTextField.getText()) || lastNameTextField.getText().isEmpty()) {
            errorLabel.setText("last name should be without space or empty");
            return false;
        }
        if (!isItWithoutSpace(repeatTextField.getText()) || repeatTextField.getText().isEmpty()) {
            errorLabel.setText("user name should be without space or empty");
            return false;
        }
        if (!isItWithoutSpace(userNameTextField.getText()) || userNameTextField.getText().isEmpty()) {
            errorLabel.setText("user name should be without space or empty");
            return false;
        }
        return true;
    }

    private boolean isItWithoutSpace(String input) {
        if (input.contains(" ")) {
            return false;
        }
        return true;
    }
}
