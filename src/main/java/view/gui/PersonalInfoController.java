package view.gui;

import controller.interfaces.account.IUserInfoController;
import exception.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Role;
import model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PersonalInfoController implements InitializableController {
    private int id;
    private User user;
    private IUserInfoController userInfoController;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label shopLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField role;
    @FXML
    private TextField shopTextField;
    @FXML
    private TextField email;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField balance;
    @FXML
    private TextField password;
    @FXML
    private Button cancelButton;
    @FXML
    private Button editButton;
    @FXML
    private ScrollPane tableScrollPane;
    @FXML
    private ScrollPane detailScrollPane;
    @FXML
    private VBox detailVBox;

    @Override
    public void initialize(int id) throws IOException {
        this.id = id;
        editButton.setText("Edit");
        cancelButton.setText("Cancel");
        cancelButton.setVisible(false);
        shopTextField.setVisible(false);
        shopLabel.setVisible(false);
        role.setEditable(false);
        password.setEditable(false);
        usernameTextField.setEditable(false);
        setEditable(false);
        editButton.setOnMouseClicked(e->{
            handleEditButton();
        });
    }
    private void handleEditButton(){
        if(editButton.getText().equals("Edit")){
            setEditable(true);
            editButton.setText("Submit");
            cancelButton.setVisible(true);
        }else{
            HashMap<String ,String > changedInfo = new HashMap<>();
            changedInfo.put("",)//todo
            if() {
                try {
                    userInfoController.changeInfo(changedInfo,Constants.manager.getToken());
                } catch (NotLoggedINException e) {
                    e.printStackTrace();//wating for pooya respond //todo
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
    }
    private void handleCancelButton(){
        setEditable(false);
        load(this.user);
        editButton.setText("Edit");
        cancelButton.setVisible(false);
    }
    private boolean isEveryThingOk(){

    }


    public void load(User user) {
        this.user = user;
        role.setText(Role.getRoleType(user.getRole()));
        usernameTextField.setText(user.getUsername());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        balance.setText("" + user.getCredit());
        shopTextField.setText("");
        if (user.getRole() == Role.SELLER) {
            shopTextField.setText(user.getCompanyName());
            shopTextField.setVisible(true);
            shopLabel.setVisible(true);
        }
    }

    private void setEditable(boolean type) {
        firstName.setEditable(type);
        lastName.setEditable(type);
        email.setEditable(type);
        balance.setEditable(type);
        shopTextField.setEditable(type);
    }

    public void updateAllBox(List<Node> nodes) {
        detailVBox.getChildren().removeAll();
        detailVBox.getChildren().addAll(nodes);
    }

    public void addSingleItemToBox(Node node) {
        detailVBox.getChildren().add(node);
    }

    public void clearBox() {
        detailVBox.getChildren().removeAll();
    }
}
