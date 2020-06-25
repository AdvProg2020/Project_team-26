package view.gui.admin;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.account.IUserInfoController;
import exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Admin;
import model.User;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import view.gui.interfaces.*;
public class AdminMenuController implements InitializableController {

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
    private TableView<Admin> managerTable;
    @FXML
    private Button editInfoButton;
    @FXML
    private TextField roleText;
    @FXML
    private Label errorLabel;

    private IShowUserController controller;
    private IUserInfoController userController;


    @Override
    public void initialize(int id) throws IOException {
        controller = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        userController = (IUserInfoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.UserInfoController);
        editInfoButton.setOnMouseClicked(e -> editInfo());
        ObservableList<Admin> list = FXCollections.observableList(controller.getManagers(id));
        try {
            User admin = controller.getUserById(id,Constants.manager.getToken());
            usernameText.setText(admin.getUsername());
            emailText.setText(admin.getEmail());
            firstNameText.setText(admin.getFirstName());
            lastNameText.setText(admin.getLastName());
            roleText.setText("Admin");
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }

    public void load() {

    }

    private void editInfo() {
        emailText.setDisable(false);
        firstNameText.setDisable(false);
        lastNameText.setDisable(false);
        editInfoButton.setText("Update");
        editInfoButton.setOnMouseClicked(e -> updateInfo());
    }

    private void updateInfo() {
        Map<String,String> newInfo = new HashMap<>();
        newInfo.put("FirstName",firstNameText.getText());
        newInfo.put("LastName",lastNameText.getText());
        newInfo.put("Email",emailText.getText());
        try {
            userController.changeInfo(newInfo,Constants.manager.getToken());
            userController.changePassword(passwordText.getText(),Constants.manager.getToken());
            revertBack();
        } catch (NotLoggedINException e) {
            errorLabel.setText(e.getMessage());
        } catch (InvalidTokenException e) {
            errorLabel.setText(e.getMessage());
        } catch (InvalidAuthenticationException e) {
            errorLabel.setText(e.getMessage());
        } catch (InvalidFormatException e) {
            errorLabel.setText(e.getMessage());
        } catch (NoSuchField noSuchField) {
            errorLabel.setText(noSuchField.getMessage());
        }
    }

    private void revertBack() {
        emailText.setDisable(true);
        firstNameText.setDisable(true);
        lastNameText.setDisable(true);
        editInfoButton.setText("Edit Info");
        editInfoButton.setOnMouseClicked(e -> editInfo());
    }
}
