package view.gui.authentication;

import controller.account.Account;
import controller.account.AuthenticationController;
import controller.interfaces.account.IAuthenticationController;
import exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Role;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.Reloadable;

import java.io.IOException;

public class RegisterMenuController implements InitializableController {

    private final ObservableList<String> roles = FXCollections.observableArrayList("Manager", "Customer", "Seller");

    @FXML
    private TextField usernameText;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField lastNameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField confirmPasswordText;
    @FXML
    private ChoiceBox<String> roleChoice;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Label errorLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextLogin;
    @FXML
    private TextField passwordTextLogin;
    @FXML
    private Tab registerTab;
    @FXML
    private Tab loginTab;
    @FXML
    private TabPane tabPane;

    private Button registerButton;
    private TextField textField;
    private IAuthenticationController controller;
    private Reloadable reloadable;


    private void initialize() {
        roleChoice.setItems(roles);
        registerButton = new Button("Register");
        registerButton.setLayoutX(255);
        registerButton.setLayoutY(355);
        registerButton.setOnMouseClicked(e -> register());
        textField = new TextField();
        textField.setLayoutX(330);
        textField.setLayoutY(287);
    }


    public void completeTheMenu(ActionEvent actionEvent) {
        switch (roleChoice.getValue()) {
            case "Manager":
                fillManager();
                break;
            case "Seller":
                fillSeller();
                break;
            case "Customer":
                fillCustomer();
        }
    }

    public void register() {
        controller = (AuthenticationController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.AuthenticationController);
        if(isAnyEmpty()) {
            errorLabel.setText("Please fill all the boxes");
        } else if (itIsntANumber()) {
            errorLabel.setText("Please Enter a Number for your credit");
        } else if (!passwordText.getText().equals(confirmPasswordText.getText())) {
            errorLabel.setText("Your passwords don't match");
        } else {
            Account account = createAccount();
            try {
                controller.register(account,Constants.manager.getToken());
            } catch (NoAccessException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidFormatException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidTokenException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidAuthenticationException e) {
                errorLabel.setText(e.getMessage());
            } catch (AlreadyLoggedInException e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    public void login(ActionEvent actionEvent) {
        controller = (AuthenticationController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.AuthenticationController);
        String username = usernameTextLogin.getText();
        String password = passwordTextLogin.getText();
        if(username.isBlank() || password.isBlank()) {
            errorLabel.setText("Please Fill all of the boxes.");
        } else {
            try {
                controller.login(username, password, Constants.manager.getToken());
                Constants.manager.setLoggedIn(true);
                return;
            } catch (InvalidTokenException e) {
                Constants.manager.setTokenFromController();
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
        tabPane.getSelectionModel().select(registerTab);
    }

    private void fillManager() {
        anchor.getChildren().addAll(registerButton);
    }

    private void fillSeller() {
        if (anchor.getChildren().contains(textField))
            textField.setPromptText("Company Name");
        else {
            textField.setPromptText("Company Name");
            anchor.getChildren().addAll(textField, registerButton);
        }
    }

    private void fillCustomer() {
        if (anchor.getChildren().contains(textField))
            textField.setPromptText("Customer Credit");
        else {
            textField.setPromptText("Customer Credit");
            anchor.getChildren().addAll(textField, registerButton);
        }
    }

    private boolean isAnyEmpty() {
        return (usernameText.getText().isEmpty() || passwordText.getText().isBlank() || firstNameText.getText().isBlank()
        || lastNameText.getText().isBlank() || confirmPasswordText.getText().isBlank() || emailText.getText().isBlank()
        || textField.getText().isBlank());
    }

    private boolean itIsntANumber() {
        if(textField.getPromptText().equals("Company Name")) {
            return true;
        } else return textField.getPromptText().matches("[0-9]+");

    }

    private Account createAccount() {
        Account account = new  Account(usernameText.getText(),passwordText.getText(),Role.getRole(roleChoice.getValue()),emailText.getText());
        account.setFirstName(firstNameText.getText());
        account.setLastName(lastNameText.getText());
        return account;
    }


    @Override
    public void initialize(int id) throws IOException {

    }
}