package client.gui.authentication;

import client.gui.Account;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.Reloadable;
import client.model.enums.Role;
import exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import view.cli.ControllerContainer;

import java.io.IOException;

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
    private Reloadable reloadable;
    private final String registerAddress = Constants.manager.getHostPort()+"/register";
    private final String loginAddress = Constants.manager.getHostPort()+"/login";


    public RegisterMenuController() {
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
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("account", account);
                jsonObject.put("token", Constants.manager.getToken());
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(registerAddress, httpEntity, String.class);
                redirectToLogin();
            } catch (HttpClientErrorException  e) {
                errorLabelRegister.setText(e.getMessage());//TODO
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void login() {
        String username = usernameTextLogin.getText();
        String password = passwordTextLogin.getText();
        if (username.isBlank() || password.isBlank()) {
            errorLabelLogin.setText("Please Fill all of the boxes.");
        } else {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", username);
                jsonObject.put("password", password);
                jsonObject.put("token", Constants.manager.getToken());
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(loginAddress, httpEntity, String.class);
                // responseEntity.getBody();
                Constants.manager.setLoggedIn(true);
                //TODO change the return type to role in server
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
            } catch (PasswordIsWrongException e) {
                errorLabelLogin.setText("Your password is Wrong.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoAccessException e) {
                e.printStackTrace();
            }
        }
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
