package client.gui.admin;
import client.ControllerContainer;
import client.connectionController.interfaces.account.IShowUserController;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageUsersController implements InitializableController {


    @FXML
    private TextField usernameText;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField emailText;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn usernameCol;
    @FXML
    private TableColumn roleCol;

    private IShowUserController controller;
    private List<User> allUsers = new ArrayList<>();


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException {
        controller = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        allUsers = controller.getUsers(Constants.manager.getToken());
        userTable.setItems(FXCollections.observableList(allUsers));
        usernameCol.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        roleCol.setCellValueFactory(new PropertyValueFactory<User,String >("role"));
        userTable.setOnMouseClicked(e -> {
            showInformation();
        });
    }

    private void showInformation() {
        User user = userTable.getSelectionModel().getSelectedItem();
        usernameText.setText(user.getUsername());
        firstNameText.setText(user.getFirstName());
        lastNameText.setText(user.getLastName());
        emailText.setText(user.getEmail());
    }

    public void load() {

    }

}
