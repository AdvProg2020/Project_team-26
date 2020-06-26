package view.gui;

import controller.interfaces.account.IUserInfoController;
import exception.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.enums.Role;
import model.User;
import view.cli.ControllerContainer;
import view.gui.interfaces.InitializableController;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    private VBox tableScrollPane;
    @FXML
    private ScrollPane detailScrollPane;
    @FXML
    private VBox detailVBox;
    @FXML
    private Button changePasswordButton;
    private Role thisUserRole;
    private File imageFile;
    private ImageView profileImage;

    @Override
    public void initialize(int id) throws IOException {
        userInfoController = (IUserInfoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.UserInfoController);
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
            try {
                handleEditButton();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cancelButton.setOnMouseClicked(e -> {
                    handleCancelButton();
                }
        );
        changePasswordButton.setOnMouseClicked(e -> {
            try {
                handleChangePasswordButton();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void load(User user, ImageView profileImage) {
        this.profileImage = profileImage;
        this.user = user;
        thisUserRole = user.getRole();
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
        if (user.getImage() != null)
            profileImage.setImage(new Image(new ByteArrayInputStream(user.getImage())));
    }

    private void handleChangePasswordButton() throws IOException {
        if (changePasswordButton.getText().matches("Change Password")) {
            changePasswordButton.setText("Old Password");
            password.setEditable(true);
        } else if (changePasswordButton.getText().matches("Old Password")) {
            oldPassword = password.getText();
            password.setText("");
            changePasswordButton.setText("Change");
        } else if (changePasswordButton.getText().matches("Change")) {
            newPassword = password.getText();
            password.setText("");
            changePasswordButton.setText("Confirm");
        } else {
            confirmPassword = password.getText();
            if (!confirmPassword.matches(newPassword)) {
                Constants.manager.showErrorPopUp("confirm doesnt match");
                return;
            }
            try {
                userInfoController.changePassword(oldPassword, newPassword, Constants.manager.getToken());
                initPasswordButton();
            } catch (InvalidTokenException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
                Constants.manager.setTokenFromController();
            } catch (NoAccessException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
            } catch (NotLoggedINException e) {
                Constants.manager.showLoginMenu();
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

    @FXML
    public void changePhoto() throws IOException {
        setFile();
    }

    private void setFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        this.imageFile = fileChooser.showOpenDialog(stage);
        if (imageFile != null) {
            profileImage.setImage(new Image(new ByteArrayInputStream(Files.readAllBytes(imageFile.toPath()))));
            userInfoController.changeImage(Files.readAllBytes(imageFile.toPath()), Constants.manager.getToken());
        }
    }

    private void handleEditButton() throws IOException {
        if (editButton.getText().equals("Edit")) {
            setEditable(true);
            editButton.setText("Submit");
            cancelButton.setVisible(true);
        } else {
            HashMap<String, String> changedInfo = new HashMap<>();
            if (isEveryThingOk()) {
                changedInfo.put("Username", usernameTextField.getText());
                changedInfo.put("FirstName", firstName.getText());
                changedInfo.put("LastName", lastName.getText());
                changedInfo.put("Email", email.getText());
                if (thisUserRole == Role.SELLER)
                    changedInfo.put("Company Name", usernameTextField.getText());
                changedInfo.put("Balance", balance.getText());
                try {
                    userInfoController.changeInfo(changedInfo, Constants.manager.getToken());
                    setEditable(false);
                    editButton.setText("Edit");
                    cancelButton.setVisible(false);
                } catch (NotLoggedINException e) {
                    Constants.manager.showLoginMenu();
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
        load(this.user, this.profileImage);
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
        if (!Constants.manager.checkIsLong(balance.getText())) {
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

    public void updateAllBoxWithSingleNode(Node node) {
        detailVBox.getChildren().removeAll(detailVBox.getChildren());
        detailVBox.getChildren().addAll(node);
    }


    public void updateAllBox(List<Node> nodes) {
        detailVBox.getChildren().removeAll(detailVBox.getChildren());
        detailVBox.getChildren().addAll(nodes);
    }

    public void setTableScrollPane(TableView newTableView) {
        tableScrollPane.getChildren().removeAll(tableScrollPane.getChildren());
        tableScrollPane.getChildren().add(newTableView);
    }

    public void setNodeForTableScrollPane(Node node) {
        tableScrollPane.getChildren().removeAll(tableScrollPane.getChildren());
        tableScrollPane.getChildren().add(node);
    }

    public void addSingleItemToBox(Node node) {
        detailVBox.getChildren().add(node);
    }

    public void clearBox() {
        detailVBox.getChildren().removeAll(detailVBox.getChildren());
    }

    public VBox getTableScrollPane() {
        return tableScrollPane;
    }

    public ScrollPane getDetailScrollPane() {
        return getDetailScrollPane();
    }
}
