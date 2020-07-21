package client.gui.supporter;

import client.ControllerContainer;
import client.connectionController.interfaces.account.IShowUserController;
import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.ChatPageController;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.MessageReceiver;
import client.model.Message;
import client.model.User;
import client.model.enums.MessageType;
import client.model.enums.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;

public class SupporterButtonPageController implements InitializableController, MessageReceiver {
    private int userId;
    private User supporterUser;
    private IShowUserController showUserController;
    @FXML
    private Button personalPage;
    @FXML
    private ImageView profileImageView;
    @FXML
    private HBox pageBox;
    @FXML
    private TableView<UserForTable> tableUsers;
    @FXML
    private VBox box;
    private HashMap<UserForTable, Node> chatRooms;
    private TableColumn<UserForTable, UserForTable> users;

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        chatRooms = new HashMap<>();
        this.userId = id;
        users = new TableColumn<>("name");
        users.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableUsers.getColumns().addAll(users);
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        User user = showUserController.getUserById(id, Constants.manager.getToken());
        this.supporterUser = (User) user;
        if (user.getRole() != Role.SUPPORT || !Constants.manager.isLoggedIn())
            throw new NoAccessException("must be Supporter");
        personalPage.setOnAction(e -> {
            try {
                loadUserInfo();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        tableUsers.setOnMouseClicked(e -> {
            changeToUserChat();
        });
    }

    private void changeToUserChat() {
        UserForTable selectedUser = tableUsers.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            return;
        }
        if (!chatRooms.containsKey(selectedUser)) {
            return;
        }
        box.getChildren().removeAll(box.getChildren());
        box.getChildren().addAll(chatRooms.get(selectedUser));
    }


    public void addUserToTable(UserForTable user) {
        tableUsers.getItems().add(user);
    }

    public void deleteUserFromTable(UserForTable user) {
        tableUsers.getItems().remove(user);
    }

    private void loadUserInfo() throws IOException {
        box.getChildren().removeAll(box.getChildren());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/UserSupporterPage.fxml"));
        Node node = loader.load();
        SupporterInfoController supporterInfoController = (SupporterInfoController) loader.getController();
        try {
            supporterInfoController.initialize(userId);
            supporterInfoController.load(this.supporterUser);
            box.getChildren().removeAll(box.getChildren());
            box.getChildren().addAll(node);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void received(Message message) throws IOException {
        UserForTable user = new UserForTable(message.getSender());
        if (message.getReceiver().equals(supporterUser.getUsername())) {
            if (message.getType() == MessageType.JOIN) {
                chatRooms.put(user, loadChatRoom(message.getSender(), supporterUser.getUsername(), Role.SUPPORT));
                addUserToTable(user);
            } else if (message.getType() == MessageType.LEAVE) {
                if (!chatRooms.containsKey(message.getSender()))
                    return;
                chatRooms.remove(user);
                deleteUserFromTable(user);
            }
        } else if (message.getReceiver().equals("") || message.getReceiver().isBlank() || message.getReceiver().isEmpty()) {
            if (!chatRooms.containsKey(user))
                return;
            chatRooms.remove(user);
            deleteUserFromTable(user);
        }
    }

    private Node loadChatRoom(String receiver, String sender, Role role) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ChatPage.fxml"));
        Node node = loader.load();
        ChatPageController chatPageController = (ChatPageController) loader.getController();
        chatPageController.initialize(userId);
        chatPageController.load(sender, receiver, role, true);
        return node;
    }

    public class UserForTable {
        private String name;

        public UserForTable(String name) {
            this.name = name;
        }

        public String getName() {
            return name;

        }

        @Override
        public boolean equals(Object obj) {
            if (!this.getClass().equals(obj.getClass()))
                return false;
            UserForTable userForTable = (UserForTable) obj;
            if (userForTable.getName().equals(this.name))
                return true;
            return false;
        }
    }
}
