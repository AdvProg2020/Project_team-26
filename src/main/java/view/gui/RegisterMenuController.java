package view.gui;

import controller.account.Account;
import controller.account.AuthenticationController;
import controller.interfaces.account.IAuthenticationController;
import exception.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Role;
import view.cli.ControllerContainer;

public class RegisterMenuController {

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

    private Button loginButton;
    private TextField textField;
    private IAuthenticationController controller;


    private void initialize() {
        roleChoice.setItems(roles);
        loginButton = new Button("Login");
        loginButton.setLayoutX(255);
        loginButton.setLayoutY(355);
        loginButton.setOnMouseClicked(e -> register());
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

    private void fillManager() {
        anchor.getChildren().addAll(loginButton);
    }

    private void fillSeller() {
        if (anchor.getChildren().contains(textField))
            textField.setPromptText("Company Name");
        else {
            textField.setPromptText("Company Name");
            anchor.getChildren().addAll(textField, loginButton);
        }
    }

    private void fillCustomer() {
        if (anchor.getChildren().contains(textField))
            textField.setPromptText("Customer Credit");
        else {
            textField.setPromptText("Customer Credit");
            anchor.getChildren().addAll(textField, loginButton);
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


}
