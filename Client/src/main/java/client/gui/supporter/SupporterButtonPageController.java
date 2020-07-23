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
import client.model.Auction;
import client.model.Message;
import client.model.User;
import client.model.enums.MessageType;
import client.model.enums.Role;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import java.util.*;

public class SupporterButtonPageController implements InitializableController, MessageReceiver {
    private int userId;
    private User supporterUser;
    private IShowUserController showUserController;
    private List<UserForTable> connectedUsers;
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
    @FXML
    private TableColumn<String, UserForTable> userColumns;
    private HashMap<UserForTable, Node> chatRooms;

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        connectedUsers = new ArrayList<>();
        chatRooms = new HashMap<>();
        this.userId = id;
        Constants.manager.getMessageReceivers().add(this::received);
        userColumns.setCellValueFactory(new PropertyValueFactory<>("name"));
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        User user = showUserController.getUserById(id, Constants.manager.getToken());
        if (Constants.manager.getLoggedInUser() == null) {
            Constants.manager.setLoggedInUser(user);
            Constants.manager.sendMessageTOWebSocket("login", new Message(user.getUsername(), "", "", MessageType.JOIN, Role.SUPPORT));
        }
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
        tableUsers.setItems(FXCollections.observableList(Arrays.asList(new UserForTable("init test"))));
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
        System.out.println("will add");
        connectedUsers.add(user);
        // tableUsers.getItems().removeAll(tableUsers.getItems());
        tableUsers.setItems(FXCollections.observableList(connectedUsers));
        System.out.println(tableUsers.getItems().size());
        System.out.println("added");
    }

    public void deleteUserFromTable(UserForTable user) {
        System.out.println("will remove");
        connectedUsers.remove(user);
        // tableUsers.getItems().removeAll(tableUsers.getItems());
        tableUsers.setItems(FXCollections.observableList(connectedUsers));
        System.out.println(tableUsers.getItems().size());
        System.out.println("will be removed");
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
        Platform.runLater(() -> {
            try {
                UserForTable user = new UserForTable(message.getSender());
                if (message.getReceiver().equals(supporterUser.getUsername())) {
                    if (message.getType() == MessageType.JOIN) {
                        if (!chatRooms.containsKey(user)) {
                            chatRooms.put(user, loadChatRoom(message.getSender(), supporterUser.getUsername(), Role.SUPPORT));
                            addUserToTable(user);
                        }
                    } else if (message.getType() == MessageType.LEAVE) {
                        if (!chatRooms.containsKey(message.getSender()))
                            return;
                        chatRooms.remove(user);
                        deleteUserFromTable(user);
                    }
                }
            } catch (IOException e) {
                e.getStackTrace();
            }
        });
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
