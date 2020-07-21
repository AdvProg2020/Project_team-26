package client.gui.supporter;

import client.ControllerContainer;
import client.connectionController.interfaces.account.IUserInfoController;
import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SupporterInfoController implements InitializableController {
    private int userId;
    private IUserInfoController userInfoController;
    @FXML
    private Button edit;
    @FXML
    private Button passwordChange;
    @FXML
    private TextField userName;
    @FXML
    private TextField role;
    @FXML
    private TextField email;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private PasswordField passwordField;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        userInfoController = (IUserInfoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.UserInfoController);
        userId = userId;
        edit.setText("Edit");
        userName.setEditable(false);
        role.setEditable(false);
        firstName.setEditable(true);
        lastName.setEditable(true);
        email.setEditable(true);
        passwordChange.setText("Change Password");
        passwordField.setText("");
        edit.setOnAction(e->{
            editButtonClicked();
        });
        passwordChange.setOnAction(e->{
            passwordChangeButtonClicked();
        });
    }
    public void load(User user){
        userName.setText(user.getUsername());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());

    }
    private void editButtonClicked(){



    }
    private void passwordChangeButtonClicked(){


    }
}
