package client.gui.admin;

import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.connectionController.interfaces.account.*;
import client.model.*;
import exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import client.ControllerContainer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    @FXML
    private TextField roleText;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView profileImage;
    @FXML
    private TableColumn managerCol;

    private IShowUserController controller;
    private IUserInfoController userController;
    private File imageFile;


    @Override
    public void initialize(int id) throws IOException {
        controller = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        userController = (IUserInfoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.UserInfoController);
        editInfoButton.setOnMouseClicked(e -> editInfo());
        ObservableList<Admin> list = FXCollections.observableList(controller.getManagers(id));
        try {
            User admin = controller.getUserById(id, Constants.manager.getToken());
            usernameText.setText(admin.getUsername());
            emailText.setText(admin.getEmail());
            firstNameText.setText(admin.getFirstName());
            lastNameText.setText(admin.getLastName());
            roleText.setText("Admin");
            managerTable.setItems(list);
            managerCol.setCellValueFactory(new PropertyValueFactory<Admin, String>("username"));
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }

    public void load() {

    }

    @FXML
    public void uploadPhoto() throws IOException {
        setFile();

    }

    private void setFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        this.imageFile = fileChooser.showOpenDialog(stage);
        if (imageFile != null) {
            profileImage.setImage(new Image(new ByteArrayInputStream(Files.readAllBytes(imageFile.toPath()))));
            try {
                userController.changeImage(Files.readAllBytes(imageFile.toPath()), Constants.manager.getToken());
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            } catch (NotLoggedINException e) {
                e.printStackTrace();
            }
        }
    }

    private void editInfo() {
        emailText.setDisable(false);
        firstNameText.setDisable(false);
        lastNameText.setDisable(false);
        editInfoButton.setText("Update");
        editInfoButton.setOnMouseClicked(e -> updateInfo());
    }

    private void updateInfo() {
        Map<String, String> newInfo = new HashMap<>();
        newInfo.put("FirstName", firstNameText.getText());
        newInfo.put("LastName", lastNameText.getText());
        newInfo.put("Email", emailText.getText());
        try {
            userController.changeInfo(newInfo, Constants.manager.getToken());
            if (!passwordText.getText().isBlank())
                userController.changePassword(passwordText.getText(), Constants.manager.getToken());
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
