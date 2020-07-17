package client.gui.authentication;

import client.gui.Account;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.enums.Role;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import exception.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import view.Main;
import view.cli.ControllerContainer;

import java.io.IOException;
import java.lang.module.Configuration;

public class FirstAdminRegister implements InitializableController {

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

    Stage primaryStage;
    Main main;
    private final String registerAddress = Constants.manager.getHostPort()+"/register";


    private void register() {
        String username = usernameText.getText();
        String email = emailText.getText();
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String password = passwordText.getText();
        if (isAnyBlank()) {
            errorLabel.setText("Please fill all of the boxes.");
        } else if (!password.equals(confirmText.getText())) {
            errorLabel.setText("Your password don't match");
        } else {
            try {
                Account adminAccount = createAccount(username, email, firstName, lastName, password);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("account", adminAccount);
                jsonObject.put("token", Constants.manager.getToken());
                Constants.manager.postRegisterLoginRequest(jsonObject,registerAddress,false);
                primaryStage.setResizable(true);
                main.start(primaryStage);
            } catch (HttpClientErrorException e) {
                errorLabel.setText(e.getMessage());
            } catch (Exception e) {
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

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        registerButton.setOnMouseClicked(e -> register());
    }

    public void load(Stage primaryStage) {
        this.primaryStage = primaryStage;
        main = new Main();
    }
}
