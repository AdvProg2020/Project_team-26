package view.gui.admin;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.category.ICategoryController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Admin;
import view.cli.ControllerContainer;
import view.gui.CategoryListController;
import view.gui.Constants;
import view.gui.interfaces.*;

import javax.crypto.Cipher;
import java.io.IOException;

public class AdminOptionMenuController implements InitializableController {

    @FXML
    private Button promoButton;
    @FXML
    private Button categoryButton;
    @FXML
    private Button usersButton;
    @FXML
    private Button newManagerButton;
    @FXML
    private Button requestsButton;
    @FXML
    private Button personalPage;
    @FXML
    private HBox hbox;


    private int userId;
    private Admin admin;
    private IShowUserController showUserController;
    private ICategoryController categoryController;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException {
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        categoryController = (ICategoryController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CategoryController);
        this.userId = id;
        this.admin = (Admin) showUserController.getUserById(id, Constants.manager.getToken());
        categoryButton.setOnMouseClicked(e -> {
            try {
                handleCategory();
            } catch (IOException | InvalidIdException ioException) {
                ioException.printStackTrace();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            }
        });
        promoButton.setOnMouseClicked(e -> {
            try {
                handlePromo();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (NoAccessException | IOException noAccessException) {
                noAccessException.printStackTrace();
            }
        });
        personalPage.setOnMouseClicked(e -> {
            try {
                handlePersonalPage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        requestsButton.setOnMouseClicked(e -> {
            try {
                handleRequest();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            } catch (NotLoggedINException notLoggedINException) {
                notLoggedINException.printStackTrace();
            }
        });

        newManagerButton.setOnMouseClicked(e -> {
            try {
                newManagerRegistry();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        usersButton.setOnMouseClicked(e -> {
            try {
                handleUser();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        handlePersonalPage();
    }

    private void newManagerRegistry() throws IOException {
        System.out.println("nigga");
        Constants.manager.showAdminRegistryMenu();
    }

    private void handleRequest() throws NoAccessException, InvalidTokenException, IOException, NotLoggedINException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ManagerRequest.fxml"));
        Node node = loader.load();
        ManagerRequestController managerRequestController = loader.getController();
        managerRequestController.initialize(2);
        managerRequestController.load();
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }

    private void handlePersonalPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/AdminMenu.fxml"));
        Node node = loader.load();
        AdminMenuController adminMenuController = loader.getController();
        adminMenuController.initialize(admin.getId());
        adminMenuController.load();
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }

    private void handleCategory() throws IOException, InvalidIdException, NoAccessException, InvalidTokenException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CategoryAdminOption.fxml"));
        Node node = loader.load();
        CategoryOptionController categoryOptionController = loader.getController();
        categoryOptionController.initialize(1);
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }

    private void handlePromo() throws NoAccessException, InvalidTokenException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/PromoManager.fxml"));
        Node node = loader.load();
        PromoManagerController promoManagerController = (PromoManagerController) loader.getController();
        promoManagerController.initialize(1);
        promoManagerController.load();
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }

    private void handleUser() throws NoAccessException, InvalidTokenException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ManageUsers.fxml"));
        Node node = loader.load();
        ManageUsersController manageUsersController = loader.getController();
        manageUsersController.initialize(2);
        manageUsersController.load();
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }
}
