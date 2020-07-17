package client.gui.admin;

import client.gui.Account;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.enums.Role;
import controller.interfaces.account.IAuthenticationController;
import exception.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.minidev.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;
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

    public void register() {
        if(isAnyEmpty()) {
            errorLabel.setText("Please fill all the boxes");
        }  else if (!passwordText.getText().equals(confirmText.getText())) {
            errorLabel.setText("Your passwords don't match");
        } else {
            Account account = createAccount();
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("account", account);
                jsonObject.put("token", Constants.manager.getToken());
                Constants.manager.postRegisterLoginRequest(jsonObject,Constants.registerAddress,false);
            } catch (HttpClientErrorException e ){
                //TODO
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
        submitButton.setOnMouseClicked(e -> register());
    }
}
