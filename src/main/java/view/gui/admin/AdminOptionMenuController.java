package view.gui.admin;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.category.ICategoryController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import model.Admin;
import view.cli.ControllerContainer;
import view.gui.CategoryListController;
import view.gui.Constants;
import view.gui.customer.OrderTableController;
import view.gui.interfaces.*;

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

    private int userId;
    private Admin admin;
    private IShowUserController showUserController;
    private ICategoryController categoryController;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException {
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        categoryController = (ICategoryController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CategoryController);
        this.userId = id;
        this.admin = (Admin) showUserController.getUserById(id,Constants.manager.getToken());
        categoryButton.setOnMouseClicked(e -> {
            try {
                handleCategory();
            } catch (IOException | InvalidIdException ioException) {
                ioException.printStackTrace();
            }
        });
        promoButton.setOnMouseClicked(e -> {
            try {
                handlePromo();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (NoAccessException noAccessException) {
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
    }

    private void handleRequest() throws NoAccessException, InvalidTokenException, IOException, NotLoggedINException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ManagerRequest.fxml"));
        ManagerRequestController managerRequestController = loader.getController();
        managerRequestController.initialize(2);
        managerRequestController.load();
    }

    private void handlePersonalPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/AdminMenu.fxml"));
        AdminMenuController adminMenuController = loader.getController();
        adminMenuController.initialize(admin.getId());
        adminMenuController.load();
    }

    private void handleCategory() throws IOException, InvalidIdException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CategoryList.fxml"));
            CategoryListController categoryListController = (CategoryListController) loader.getController();
            categoryListController.initialize(1);
            categoryListController.load(categoryController.getCategory(1,Constants.manager.getToken()));
    }

    private void handlePromo() throws NoAccessException, InvalidTokenException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/PromoManager.fxml")); {
            PromoManagerController promoManagerController = (PromoManagerController) loader.getController();
            promoManagerController.initialize(1);
            promoManagerController.load();
        }
    }
}
