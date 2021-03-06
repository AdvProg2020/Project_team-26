package client.gui.authentication;

import client.ControllerContainer;
import client.connectionController.interfaces.account.IAuthenticationController;
import client.connectionController.interfaces.account.IShowUserController;
import client.exception.*;
import client.gui.*;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.Reloadable;
import client.model.enums.*;
import client.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Map;

public class RegisterMenuController implements InitializableController {

    private final ObservableList<String> roles = FXCollections.observableArrayList("Customer", "Seller");

    @FXML
    private TextField usernameText;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField captchaBoxRegister;
    @FXML
    private TextField captchaBoxLogin;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField confirmPasswordText;
    @FXML
    private ChoiceBox<String> roleChoice;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Label errorLabelRegister;
    @FXML
    private Label errorLabelLogin;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextLogin;
    @FXML
    private TextField passwordTextLogin;
    @FXML
    private Hyperlink loginHyperLink;
    @FXML
    private Tab registerTab;
    @FXML
    private Tab loginTab;
    @FXML
    private TabPane tabPane;

    private Button registerButton;
    private TextField textField;
    private IAuthenticationController controller;
    private IShowUserController showUserController;
    private Reloadable reloadable;

    public RegisterMenuController() {
        controller = (IAuthenticationController) Constants.manager.getControllerContainer().
                getController(ControllerContainer.Controller.AuthenticationController);
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
    }

    public void completeTheMenu() {
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
        Account account = createAccount();
        if (isAnyEmpty()) {
            errorLabelRegister.setText("Please fill all the boxes");
        } else if (itIsntANumber() && account.getRole() == Role.CUSTOMER) {
            errorLabelRegister.setText("Please Enter a Number for your credit");
        } else if (!passwordText.getText().equals(confirmPasswordText.getText())) {
            errorLabelRegister.setText("Your passwords don't match");
        } else {
            try {
                controller.register(account, Constants.manager.getToken());
                redirectToLogin();
            } catch (NoAccessException e) {
                errorLabelRegister.setText(e.getMessage());
            } catch (InvalidFormatException e) {
                errorLabelRegister.setText(e.getMessage());
            } catch (InvalidTokenException e) {
                errorLabelRegister.setText(e.getMessage());
            } catch (InvalidAuthenticationException e) {
                errorLabelRegister.setText(e.getMessage());
            } catch (AlreadyLoggedInException e) {
                errorLabelRegister.setText(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void login() {
        String username = usernameTextLogin.getText();
        String password = passwordTextLogin.getText();
        if (username.isBlank() || password.isBlank() || captchaBoxLogin.getText().isBlank()) {
            errorLabelLogin.setText("Please Fill all of the boxes.");
        } else {
            try {
                controller.login(username, password, Constants.manager.getToken(), captchaBoxLogin.getText());
                Constants.manager.setLoggedIn(true);
                Constants.manager.setRole(showUserController.getUserByName(username, Constants.manager.getToken()).getRole());
                reloadable.reload();
                Constants.manager.showSuccessPopUp("You have logged in.");
                Constants.manager.showRandomPromoIfUserGet();
                Constants.manager.closePopUp();
                return;
            } catch (InvalidTokenException e) {
                Constants.manager.setTokenFromController();
                errorLabelLogin.setText(e.getMessage());
                return;
            } catch (InvalidFormatException e) {
                errorLabelLogin.setText(e.getMessage());
            } catch (InvalidAuthenticationException e) {
                errorLabelLogin.setText(e.getMessage());
                reload();
            } catch (PasswordIsWrongException e) {
                errorLabelLogin.setText("Your password is Wrong.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void reload() {
        String captcha = controller.getCaptcha();
        captchaBoxLogin.setPromptText(captcha);
        captchaBoxRegister.setPromptText(captcha);
    }

    @FXML
    public void redirectToRegister() throws IOException {
        tabPane.getSelectionModel().select(0);
    }

    public void redirectToLogin() throws IOException {
        tabPane.getSelectionModel().select(1);
    }

    private void fillManager() {
        anchor.getChildren().remove(registerButton);
        anchor.getChildren().remove(textField);
        anchor.getChildren().addAll(registerButton);
    }

    private void fillSeller() {
        anchor.getChildren().remove(registerButton);
        if (anchor.getChildren().contains(textField)) {
            textField.setPromptText("Company Name");
            anchor.getChildren().addAll(registerButton);
        } else {
            textField.setPromptText("Company Name");
            anchor.getChildren().addAll(textField, registerButton);
        }
    }

    private void fillCustomer() {
        anchor.getChildren().remove(registerButton);
        if (anchor.getChildren().contains(textField)) {
            textField.setPromptText("Customer Credit");
            anchor.getChildren().addAll(registerButton);
        } else {
            textField.setPromptText("Customer Credit");
            anchor.getChildren().addAll(textField, registerButton);
        }
    }

    private boolean isAnyEmpty() {
        return (usernameText.getText().isEmpty() || passwordText.getText().isBlank() || firstNameText.getText().isBlank()
                || lastNameText.getText().isBlank() || confirmPasswordText.getText().isBlank() || emailText.getText().isBlank()
                || textField.getText().isBlank());
    }

    public void setReloadable(Reloadable reloadable) {
        this.reloadable = reloadable;
    }

    private boolean itIsntANumber() {
        if (textField.getPromptText().equals("Company Name")) {
            return true;
        } else return textField.getPromptText().matches("[0-9]+");

    }

    private Account createAccount() {
        Account account = new Account(usernameText.getText(), passwordText.getText(), Role.getRole(roleChoice.getValue()), emailText.getText());
        account.setFirstName(firstNameText.getText());
        account.setLastName(lastNameText.getText());
        switch (roleChoice.getValue()) {
            case "Customer":
                account.setCredit(Long.parseLong(textField.getText()));
                account.setRole(Role.CUSTOMER);
                break;
            case "Seller":
                account.setCompanyName(textField.getText());
                account.setRole(Role.SELLER);
        }
        return account;
    }


    @Override
    public void initialize(int id) throws IOException {
        controller = (IAuthenticationController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.AuthenticationController);
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        String captcha = controller.getCaptcha();
        captchaBoxRegister.setPromptText(captcha);
        captchaBoxLogin.setPromptText(captcha);
        roleChoice.setItems(roles);
        registerButton = new Button("Register");
        registerButton.setLayoutX(255);
        registerButton.setLayoutY(355);
        registerButton.setOnMouseClicked(e -> register());
        textField = new TextField();
        textField.setLayoutX(330);
        textField.setLayoutY(287);
        loginButton.setOnMouseClicked(e -> login());
        roleChoice.setOnAction(e -> completeTheMenu());
        loginHyperLink.setOnAction(e -> {
            try {
                redirectToRegister();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }


}
