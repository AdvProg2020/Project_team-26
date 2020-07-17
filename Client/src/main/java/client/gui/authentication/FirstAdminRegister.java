package client.gui.authentication;

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
import javafx.stage.Stage;
import view.Main;
import view.cli.ControllerContainer;

import java.io.IOException;

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
                JSONObject jsonObject = new JSONObject();
                Map<String,String> test = new HashMap<>();
                test.put("dana","3");
                test.put("dana","bahosh");
                jsonObject.put("dana",test);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(),httpHeaders);
                ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/nigger",httpEntity,String.class);
                System.out.println(responseEntity.getBody());
                controller.register(adminAccount, Constants.manager.getToken());
                primaryStage.setResizable(true);
                main.start(primaryStage);
            } catch (InvalidTokenException e) {
                errorLabel.setText(e.getMessage());
            } catch (AlreadyLoggedInException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidFormatException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidAuthenticationException e) {
                errorLabel.setText(e.getMessage());
            } catch (NoAccessException e) {
                errorLabel.setText(e.getMessage());
            } catch (IOException e) {
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
