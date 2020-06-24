package view.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Role;
import view.cli.ControllerContainer;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager {

    private ControllerContainer controllerContainer;
    private String token;
    private boolean isLoggedIn;
    private Role role;
    private List<Pair<String, Integer>> pages;
    private MainController controller;
    private AuthenticationStageManager authenticationStageManager;

    public Manager() {
        pages = new ArrayList<>();
    }

    public void openPage(String pageName, int id) throws IOException {
        pages.add(new Pair<>(pageName, id));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/" + pageName + ".fxml"));
        Parent parent = loader.load();
        InitializableController controller = loader.getController();
        controller.initialize(id);
        showNode(parent);
    }

    public void back() throws IOException {
        pages.remove(pages.size() - 1);
        Pair<String, Integer> page = pages.remove(pages.size() - 1);
        openPage(page.getKey(), page.getValue());
    }

    private void showNode(Node node) {
        controller.setCenter(node);
    }

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/Main.fxml"));
        Scene scene = new Scene(loader.load());
        controller = loader.getController();
        reloadTop();
        primaryStage.setTitle("Store");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void reloadTop() {
        controller.reload();
    }

    public ControllerContainer getControllerContainer() {
        return controllerContainer;
    }

    public void setControllerContainer(ControllerContainer controllerContainer) {
        this.controllerContainer = controllerContainer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean checkInputIsInt(String input) {
        try {
            if (0 > Integer.parseInt(input))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean checkIsLong(String input) {
        try {
            if (0 > Long.parseLong(input))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void openAccountPage(){


    }

    public void openCart() {
        //TODO set cart and toolbar up
    }

    public void openSingleProductPage() {
        //todo
    }


    public void setTokenFromController(String error) {
        //todo
    }

    public void showCompareStage() {
        //TODO get list then load the compare pane
        //TODO then add the compareProduct with loading it by function andToBox in compare pane controller
    }

    public void setAuthenticationStageManager(AuthenticationStageManager authenticationStageManager) {
        this.authenticationStageManager = authenticationStageManager;
    }

    public void switchScene(String scene) throws IOException {
        if (scene.equals("Login"))
            authenticationStageManager.switchToLogin();
        else
            authenticationStageManager.switchToRegister();
    }

    public boolean checkIsPercent(String input) {
        if (input.matches("\\d+(?:\\.\\d+)%$")) {
            if (input.split("%")[0].length() <= 2)
                return true;
            return false;
        }
        return false;
    }

    public Date getDateFromDatePicker(DatePicker datePicker) throws DateTimeException, IllegalArgumentException {
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        if (date.getTime() < 0)
            throw new IllegalArgumentException("pick closer");
        return date;
    }

    public void showErrorPopUp(String errorMessage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ErrorPage.fxml"));
        ErrorPageController controller = loader.getController();
        Button okButton = controller.getButton();
        controller.setText(errorMessage);
        Stage windows = new Stage(loader.load());
        okButton.setOnMouseClicked(e -> windows.close());
        windows.initModality(Modality.APPLICATION_MODAL);
        windows.setResizable(false);
        windows.show();
    }
}
