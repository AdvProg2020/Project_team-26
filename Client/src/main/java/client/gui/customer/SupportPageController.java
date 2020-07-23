package client.gui.customer;

import client.ControllerContainer;
import client.connectionController.interfaces.account.IShowUserController;
import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.ChatPageController;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.Message;
import client.model.Off;
import client.model.User;
import client.model.enums.MessageType;
import client.model.enums.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.List;

public class SupportPageController implements InitializableController {
    private IShowUserController showUserController;
    private User thisUser;

    @FXML
    private TableColumn<String, TableUser> supporterColumns;
    @FXML
    private TableView<TableUser> supporterUsers;
    @FXML
    private Button refresh;
    @FXML
    private VBox chatBox;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        supporterColumns.setCellValueFactory(new PropertyValueFactory<>("username"));
        supporterUsers.setOnMouseClicked(e -> {
            ObservableList<TableUser> objects = supporterUsers.getSelectionModel().getSelectedItems();
            if (objects == null || objects.size() == 0)
                return;
            try {
                loadChatRoom(objects.get(0).getUsername());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        refresh.setOnAction(e -> {
            reloadTable();
        });

    }

    public void load(User thisUser) {
        this.thisUser = thisUser;
        reloadTable();

    }

    private void loadChatRoom(String user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ChatPage.fxml"));
        Node node = loader.load();
        ChatPageController chatPageController = (ChatPageController) loader.getController();
        chatPageController.initialize(thisUser.getId());
        Constants.manager.sendMessageTOWebSocket(user, new Message(thisUser.getUsername(), "im here", user, MessageType.JOIN, Role.CUSTOMER));
        chatPageController.load(thisUser.getUsername(), user, Role.CUSTOMER, true);
        chatBox.getChildren().removeAll(chatBox.getChildren());
        chatBox.getChildren().addAll(node);
    }


    private void reloadTable() {
        supporterUsers.getItems().removeAll(supporterUsers.getItems());
        List<String> userNames = showUserController.getOnlineSupporter(Constants.manager.getToken());
        List<TableUser> tableUsers = new ArrayList<>();
        userNames.forEach(i -> tableUsers.add(new TableUser(i)));
        supporterUsers.getItems().removeAll(supporterUsers.getItems());
        supporterUsers.setItems(FXCollections.observableList(tableUsers));
    }

    public class TableUser {
        public String username;

        public TableUser(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
