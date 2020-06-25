package view.gui;

import controller.SessionController;
import controller.interfaces.account.IShowUserController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.enums.Role;
import model.User;
import view.cli.ControllerContainer;
import view.gui.admin.AdminRegistryController;
import view.gui.authentication.AuthenticationStageManager;
import view.gui.authentication.RegisterMenuController;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.Reloadable;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager implements Reloadable {

    private ControllerContainer controllerContainer;
    private String token;
    private User loggedInUser;
    private boolean isLoggedIn;
    private Role role;
    private List<Pair<String, Integer>> pages;
    private MainController controller;
    private AuthenticationStageManager authenticationStageManager;
    private List<Integer> compareList;
    private Stage popUp;

    public Manager() {
        pages = new ArrayList<>();
        compareList = new ArrayList<>();
    }

    public void openPage(String pageName, int id) throws IOException {
        pages.add(new Pair<>(pageName, id));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/" + pageName + ".fxml"));
        Parent parent = loader.load();
        InitializableController controller = loader.getController();
        try {
            controller.initialize(id);//TODO handle kon
            reloadTop();
        } catch (InvalidTokenException e) {
            Constants.manager.setTokenFromController();
            Constants.manager.showErrorPopUp("Your token was invalid.");
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }
        showNode(parent);
    }

    public void back() throws IOException {
        if (pages.size() > 1) {
            pages.remove(pages.size() - 1);
            Pair<String, Integer> page = pages.remove(pages.size() - 1);
            openPage(page.getKey(), page.getValue());
        }
    }

    public List<Pair<String, Integer>> getPages() {
        return pages;
    }

    public List<Integer> getCompareList() {
        return compareList;
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
                return false;
            return true;
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

    public void openAccountPage() throws IOException {
        try {
            User user = ((IShowUserController) controllerContainer.getController(ControllerContainer.Controller.ShowUserController)).getUserByToken(getToken());
            if (user.getRole() == Role.SELLER) {
                openPage("SellerButtons", user.getId());
            } else if (user.getRole() == Role.CUSTOMER) {
                openPage("CustomersButton", user.getId());
            } else if (user.getRole() == Role.ADMIN) {
                openPage("AdminOptionMenu",user.getId());
            } else {
                //TODO open main page here
            }
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }

    public void openCart() throws IOException {
        openPage("Cart",0);
    }

    public void setTokenFromController() {
        this.token = ((SessionController)controllerContainer.getController(ControllerContainer.Controller.SessionController)).createToken();
    }

    public void showComparePage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ComparePane.fxml"));
        BorderPane borderPane = new BorderPane((Node)loader.load());
        Stage windows = new Stage();
        windows.setScene(new Scene(borderPane));
        ComparePane controller = loader.getController();
        Button exit = controller.getExitButton();
        addProductToComparePane(controller);
        exit.setOnMouseClicked(e -> windows.close());
        windows.initModality(Modality.APPLICATION_MODAL);
        windows.setResizable(false);
        windows.show();
    }

    private void addProductToComparePane(ComparePane controller) {
        compareList.forEach(i -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CompareProduct.fxml"));
            CompareController compareController = loader.getController();
            try {
                compareController.initialize(i);
                controller.addToBox(loader.load());
            } catch (IOException | InvalidIdException e) {
                e.printStackTrace();//todo
            }
        });
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

    public void showLoginMenu() throws IOException {
        popUp = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/RegisterMenu.fxml"));
        VBox vBox = new VBox((Node)loader.load());
        RegisterMenuController controller = loader.getController();
        controller.initialize(2);
        controller.setReloadable(this);
        popUp.setScene(new Scene(vBox));
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setResizable(false);
        controller.redirectToLogin();
        popUp.show();
    }

    public void showRegisterMenu() throws IOException {
        popUp = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/RegisterMenu.fxml"));
        VBox vBox = new VBox((Node)loader.load());
        RegisterMenuController controller = loader.getController();
        controller.initialize(2);
        controller.setReloadable(this);
        popUp.setScene(new Scene(vBox));
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setResizable(false);
        popUp.show();
    }

    public void showAdminRegistryMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/AdminRegistryMenu.fxml"));
        AdminRegistryController controller = loader.getController();
        Stage windows = new Stage(loader.load());
        windows.initModality(Modality.APPLICATION_MODAL);
        windows.setResizable(false);
        windows.show();
    }

    public void showErrorPopUp(String errorMessage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ErrorPage.fxml"));
        BorderPane borderPane = new BorderPane((Node)loader.load());
        Stage windows = new Stage();
        windows.setScene(new Scene(borderPane));
        ErrorPageController controller = loader.getController();
        Button okButton = controller.getButton();
        controller.load(errorMessage);
        okButton.setOnMouseClicked(e -> windows.close());
        windows.initModality(Modality.APPLICATION_MODAL);
        windows.setResizable(false);
        windows.show();
    }

    public void closePopUp() {
        popUp.close();
    }

    public void addToCompareList(int productId) {
        compareList.add(productId);
    }

    @Override
    public void reload() {
        reloadTop();
        Pair<String, Integer> page = pages.remove(pages.size() - 1);
        try {
            openPage(page.getKey(), page.getValue());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


}
