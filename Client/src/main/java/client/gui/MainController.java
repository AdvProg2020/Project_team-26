package client.gui;

import client.exception.*;
import client.model.enums.Role;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
    @FXML
    private Button backButton;
    @FXML
    private Button compareButton;
    @FXML
    private ColorPicker colorPicker;

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
        if (Constants.manager.getPages().size() > 1) {
            backButton.setDisable(false);
        } else {
            backButton.setDisable(true);
        }
        if (Constants.manager.getCompareList().size() >= 1) {
            compareButton.setVisible(true);
        } else {
            compareButton.setVisible(false);
        }
    }

    @FXML
    public void changeColor() {
        colorPicker.getBackground();
        Background background = new Background(new BackgroundFill(Paint.valueOf(toHexString(colorPicker.getValue())), null, null));
        mainPane.setBackground(background);
        mainScrollPane.setBackground(background);

    }

    public void back() throws IOException {
        Constants.manager.back();
    }

    public void openHome() throws IOException {
        Constants.manager.openPage("AllProducts", 0);
    }

    public void openCompare() throws IOException {
        Constants.manager.showComparePage();
    }

    public void openCart() throws IOException {
        Constants.manager.openCart();
    }

    public void openLogin() throws IOException {
        Constants.manager.showLoginMenu();
    }

    public void openRegister() throws IOException {
        Constants.manager.showRegisterMenu();
    }

    public void openAccount() throws IOException {
        Constants.manager.openAccountPage();
    }

    public void logout() throws IOException, NotLoggedINException, InvalidTokenException {
        Constants.manager.logout();
    }

    private String toHexString(Color color) {
        int r = ((int) Math.round(color.getRed()     * 255)) << 24;
        int g = ((int) Math.round(color.getGreen()   * 255)) << 16;
        int b = ((int) Math.round(color.getBlue()    * 255)) << 8;
        int a = ((int) Math.round(color.getOpacity() * 255));

        return String.format("#%08X", (r + g + b + a));
    }
}
