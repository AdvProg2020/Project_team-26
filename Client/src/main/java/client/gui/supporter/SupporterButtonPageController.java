package client.gui.supporter;

import client.ControllerContainer;
import client.connectionController.interfaces.account.IShowUserController;
import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.ChatPageController;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;

public class SupporterButtonPageController implements InitializableController {
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
    private HashMap<Integer, Node> chatRooms;

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        chatRooms = new HashMap<>();
        this.userId = id;
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        User user = showUserController.getUserById(id, Constants.manager.getToken());
        this.supporterUser = (User) user;
      /*  if (user.getRole() != Role.SELLER || !Constants.manager.isLoggedIn())
            throw new NoAccessException("must be seller");*/
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

    private void changeToUserChat(){
        UserForTable selectedUser = tableUsers.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            return;
        }
        if (!chatRooms.containsKey(selectedUser.getId())) {
            return;
        }
        box.getChildren().removeAll(box.getChildren());
        box.getChildren().addAll(chatRooms.get(selectedUser.getId()));
    }


    public void addUserToTable(int id) {


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

    public class UserForTable {
        public User user;
        public int id;
        public String name;

        public UserForTable(User user, int id, String name) {
            this.user = user;
            this.id = id;
            this.name = name;
        }

        public User getUser() {
            return user;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
