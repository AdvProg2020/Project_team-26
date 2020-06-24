package view.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.enums.Role;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainPane;
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private HBox topBox;
    @FXML
    private Button homeButton;
    @FXML
    private Button cartButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button accountButton;
    @FXML
    private Button logoutButton;

    public void setLeft(Node node) {
        mainPane.setLeft(node);
    }

    public void setCenter(Node node) {
        mainScrollPane.setContent(node);
    }

    public void reload() {
        if (Constants.manager.isLoggedIn()) {
            topBox.getChildren().removeAll(loginButton, registerButton, accountButton, logoutButton);
            topBox.getChildren().addAll(accountButton, logoutButton);
            if (Constants.manager.getRole() == Role.CUSTOMER) {
                cartButton.setVisible(true);
            } else {
                cartButton.setVisible(false);
            }
        } else {
            topBox.getChildren().removeAll(loginButton, registerButton, accountButton, logoutButton);
            topBox.getChildren().addAll(loginButton, registerButton);
            cartButton.setVisible(true);
        }
    }

    public void openHome() throws IOException {
        Constants.manager.openPage("AllProducts", 0);
    }

    public void openCart() throws IOException {
        Constants.manager.openCart();
    }

    public void openLogin() throws IOException {
        Constants.manager.showLoginMenu();
    }

    public void openRegister() {
        Constants.manager.showRegisterMenu();
    }

    public void openAccount() throws IOException {
        Constants.manager.openAccountPage();
    }

    public void logout() {
        // TODO: logout
    }
}
