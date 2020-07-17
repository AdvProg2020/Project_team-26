package client.gui.admin;

import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.Admin;
import client.model.User;
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
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import view.cli.ControllerContainer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
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

    private File imageFile;


    @Override
    public void initialize(int id) throws IOException {
        editInfoButton.setOnMouseClicked(e -> editInfo());
        RestTemplate restTemplate = new RestTemplate();
        try {
            Admin[] responseObject = restTemplate.getForObject(Constants.getManagersAddress + id, Admin[].class);
            ObservableList<Admin> list = (ObservableList<Admin>) FXCollections.observableList(Arrays.asList(responseObject));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("token", Constants.manager.getToken());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
            restTemplate = new RestTemplate();
            ResponseEntity<User> responseEntity = restTemplate.postForEntity(Constants.getUserByIdAddress, httpEntity, User.class);
            User admin = responseEntity.getBody();
            usernameText.setText(admin.getUsername());
            emailText.setText(admin.getEmail());
            firstNameText.setText(admin.getFirstName());
            lastNameText.setText(admin.getLastName());
            roleText.setText("Admin");
            managerTable.setItems(list);
            managerCol.setCellValueFactory(new PropertyValueFactory<Admin, String>("username"));
        } catch (HttpClientErrorException e) {
            //todo
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
            try {//TODO prodile pic what to do
                //userController.changeImage(Files.readAllBytes(imageFile.toPath()), Constants.manager.getToken());
            } catch (HttpClientErrorException e) {
                //TODO
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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("newInfo", newInfo);
            jsonObject.put("token", Constants.manager.getToken());
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.changeInfoAddress);
            if (!passwordText.getText().isBlank()) {
                jsonObject.remove("newInfo");
                jsonObject.put("newPassword", passwordText.getText());
                Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.changePasswordAddress);
            }
            revertBack();
        } catch (HttpClientErrorException e){
            //TODO
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
