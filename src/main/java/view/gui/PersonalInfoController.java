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
    private String newPassword = "";
    private String oldPassword = "";
    private String confirmPassword = "";
    //todo hashmap for labels

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
    private PasswordField password;
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
    @FXML
    private Button changePasswordButton;

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
        changePasswordButton.setText("Change Password");
        editButton.setOnMouseClicked(e -> {
            handleEditButton();
        });
        cancelButton.setOnMouseClicked(e -> {
                    handleCancelButton();
                }
        );
        changePasswordButton.setOnMouseClicked(e -> {
            handleChangePasswordButton();
        });
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

    private void handleChangePasswordButton() {
        if (changePasswordButton.getText().matches("Change Password")) {
            changePasswordButton.setText("Old Password");
            password.setEditable(true);
        } else if (changePasswordButton.getText().matches("Old Password")) {
            oldPassword = password.getText();
            password.setText("");
            changePasswordButton.setText("Change");
        } else if (changePasswordButton.getText().matches("Change")) {
            newPassword = password.getText();
            if (!oldPassword.matches(newPassword)) {
                //todo red
                return;
            }
            password.setText("");
            changePasswordButton.setText("Confirm");
        } else {
            confirmPassword = password.getText();
            if (!confirmPassword.matches(newPassword)) {
                //todo red
                return;
            }
            try {
                userInfoController.changePassword(oldPassword, newPassword, Constants.manager.getToken());
                initPasswordButton();
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            } catch (NoAccessException e) {
                e.printStackTrace();
            } catch (NotLoggedINException e) {
                e.printStackTrace();
            }

        }

    }

    private void initPasswordButton() {
        oldPassword = "";
        newPassword = "";
        confirmPassword = "";
        changePasswordButton.setText("Change Password");
        password.setEditable(false);
        password.setText("");
    }

    private void handleEditButton() {
        if (editButton.getText().equals("Edit")) {
            setEditable(true);
            editButton.setText("Submit");
            cancelButton.setVisible(true);
        } else {
            HashMap<String, String> changedInfo = new HashMap<>();
            /* changedInfo.put("",)//todo*/
            if (isEveryThingOk()) {
                try {
                    userInfoController.changeInfo(changedInfo, Constants.manager.getToken());
                    setEditable(false);
                    editButton.setText("Edit");
                    cancelButton.setVisible(false);
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

    private void handleCancelButton() {
        setEditable(false);
        load(this.user);
        editButton.setText("Edit");
        cancelButton.setVisible(false);
        initPasswordButton();
    }

    private boolean isEveryThingOk() {
        boolean result = true;
        if (firstName.getText().isBlank()) {
            //todo red the label
            result = false;
        }
        if (lastName.getText().isBlank()) {
            //todo red the label
            result = false;
        }
        if (email.getText().isBlank() || !email.getText().matches("^\\S+@\\S+.(?i)com(?-i)")) {
            //todo red the label
            result = false;
        }
        if (balance.getText().isBlank() || !Constants.manager.checkIsLong(balance.getText())) {
            //todo red the label
            result = false;
        }
        return result;
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

    public void setTableScrollPane(TableView newTableView) {
        tableScrollPane.getChildrenUnmodifiable().removeAll();
        tableScrollPane.getChildrenUnmodifiable().add(newTableView);
    }
    public void setNodeForTableScrollPane(Node node) {
        tableScrollPane.getChildrenUnmodifiable().removeAll();
        tableScrollPane.getChildrenUnmodifiable().add(node);
    }

    public void addSingleItemToBox(Node node) {
        detailVBox.getChildren().add(node);
    }

    public void clearBox() {
        detailVBox.getChildren().removeAll();
    }

    public ScrollPane getTableScrollPane() {
        return tableScrollPane;
    }

    public ScrollPane getDetailScrollPane() {
        return getDetailScrollPane();
    }
}
