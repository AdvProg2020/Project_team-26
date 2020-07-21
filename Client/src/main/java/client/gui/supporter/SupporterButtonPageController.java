package client.gui.supporter;

import client.ControllerContainer;
import client.connectionController.interfaces.account.IShowUserController;
import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.User;
import client.model.enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

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
    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        User user = showUserController.getUserById(id, Constants.manager.getToken());
        this.supporterUser = (User) user;
      /*  if (user.getRole() != Role.SELLER || !Constants.manager.isLoggedIn())
            throw new NoAccessException("must be seller");*/



    }


    public void addUserToTable(int id){

    }

    public class UserForTable{
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
