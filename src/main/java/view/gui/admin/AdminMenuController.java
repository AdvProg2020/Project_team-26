package view.gui.admin;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.account.IUserInfoController;
import exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Admin;
import model.User;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    private IShowUserController controller;
    private IUserInfoController userController;


    @Override
    public void initialize(int id) throws IOException {
        controller = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        userController = (IUserInfoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.UserInfoController);
        ObservableList<Admin> list = FXCollections.observableList(controller.getManagers(id));
        try {
            User admin = controller.getUserById(id,Constants.manager.getToken());
            usernameText.setText(admin.getUsername());
            emailText.setText(admin.getEmail());
            firstNameText.setText(admin.getFirstName());
            lastNameText.setText(admin.getLastName());
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editInfo() {
        usernameText.setEditable(true);
        emailText.setEditable(true);
        firstNameText.setEditable(true);
        lastNameText.setEditable(true);
        passwordText.setEditable(true);
        editInfoButton.setText("Update");
        editInfoButton.setOnMouseClicked(e -> updateInfo());
    }

    @FXML
    private void updateInfo() {
        Map<String,String> newInfo = new HashMap<>();
        newInfo.put("FirstName",firstNameText.getText());
        newInfo.put("LastName",lastNameText.getText());
        newInfo.put("Email",emailText.getText());
        try {
            userController.changeInfo(newInfo,Constants.manager.getToken());
            userController.changePassword(passwordText.getText(),Constants.manager.getToken());
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (InvalidAuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (NoSuchField noSuchField) {
            noSuchField.printStackTrace();
        }
    }
}
