package client.gui.supporter;

import client.ControllerContainer;
import client.connectionController.interfaces.account.IUserInfoController;
import client.exception.*;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;

public class SupporterInfoController implements InitializableController {
    private int userId;
    private IUserInfoController userInfoController;
    @FXML
    private Button edit;
    @FXML
    private Button passwordChange;
    @FXML
    private TextField userName;
    @FXML
    private TextField role;
    @FXML
    private TextField email;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private PasswordField passwordField;
    private String oldPassword;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        userInfoController = (IUserInfoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.UserInfoController);
        userId = userId;
        edit.setText("Edit");
        userName.setEditable(false);
        role.setEditable(false);
        firstName.setEditable(true);
        lastName.setEditable(true);
        email.setEditable(true);
        passwordChange.setText("Change Password");
        passwordField.setPromptText("old password");
        passwordField.setText("");
        edit.setOnAction(e -> {
            try {
                editButtonClicked();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        passwordChange.setOnAction(e -> {
            try {
                passwordChangeButtonClicked();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void load(User user) {
        userName.setText(user.getUsername());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());

    }

    private void editButtonClicked() throws IOException {
        HashMap<String, String> changedInfo = new HashMap<>();
        if (isEveryThingOk()) {
            changedInfo.put("FirstName", firstName.getText());
            changedInfo.put("LastName", lastName.getText());
            changedInfo.put("Email", email.getText());
            try {
                userInfoController.changeInfo(changedInfo, Constants.manager.getToken());
            } catch (NotLoggedINException e) {
                Constants.manager.showLoginMenu();
            } catch (InvalidTokenException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
                Constants.manager.setTokenFromController();
            } catch (InvalidFormatException | NoSuchField | InvalidAuthenticationException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
            }
        }
    }

    private void passwordChangeButtonClicked() throws IOException {
        if (passwordChange.getText().equals("Change Password")) {
            oldPassword = passwordField.getText();
            if (oldPassword == null || oldPassword.isBlank() || oldPassword.isEmpty()) {
                return;
            }
            passwordChange.setText("Save");
            passwordField.setPromptText("new Password");
        } else {
            String newPassword = passwordField.getText();
            if (newPassword == null || newPassword.isBlank() || newPassword.isEmpty()) {
                return;
            }
            try {
                userInfoController.changePassword(oldPassword, newPassword, Constants.manager.getToken());
                passwordChange.setText("Change Password");
                passwordField.setPromptText("old password");
            } catch (InvalidTokenException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
                Constants.manager.setTokenFromController();
            } catch (NoAccessException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
            } catch (NotLoggedINException e) {
                Constants.manager.showLoginMenu();
            }
        }


    }

    private boolean isEveryThingOk() throws IOException {
        boolean result = true;
        if (firstName.getText().isBlank()) {
            Constants.manager.showErrorPopUp("first name shouldn't be empty");
            result = false;
        }
        if (lastName.getText().isBlank()) {
            Constants.manager.showErrorPopUp("last name shouldn't be empty");
            result = false;
        }
        if (email.getText().isBlank() || !email.getText().matches("^\\S+@\\S+.(?i)com(?-i)")) {
            Constants.manager.showErrorPopUp("invalid email format");
            result = false;
        }
        return result;
    }
}
