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
    private Button passwordChange;
    @FXML
    private TextField userName;
    @FXML
    private TextField role;
    @FXML
    private PasswordField passwordField;
    private String oldPassword;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        userInfoController = (IUserInfoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.UserInfoController);
        userId = id;
        userName.setEditable(false);
        role.setEditable(false);
        role.setText("Supporter");
        passwordChange.setText("Change Password");
        passwordField.setPromptText("old password");
        passwordField.setText("");
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
}
