package view.gui.seller;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.discount.IOffController;
import controller.interfaces.order.IOrderController;
import controller.interfaces.product.IProductController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import model.Seller;
import model.User;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.PersonalInfoController;
import view.gui.customer.OrderTableController;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerButtonsController implements InitializableController {
    private IOffController offController;
    private IProductController productController;
    private IOrderController orderController;
    private IShowUserController showUserController;
    private int userId;
    private PersonalInfoController personalInfoController;
    private Seller seller;
    @FXML
    private Button createOff;
    @FXML
    private Button createProduct;
    @FXML
    private Button history;
    @FXML
    private Button manageOff;
    @FXML
    private Button manageProduct;
    @FXML
    private HBox box;

    @Override
    public void initialize(int id) throws IOException, NoAccessException, InvalidTokenException {
        offController = (IOffController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OffController);
        productController = (IProductController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ProductController);
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        orderController = (IOrderController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OrderController);
        User user = showUserController.getUserById(id, Constants.manager.getToken());
        this.seller = (Seller) user;
        this.userId = id;
        load();
        createOff.setOnMouseClicked(e -> {
            try {
                createOffHandle();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        createProduct.setOnMouseClicked(e -> {
            try {
                createProductHandle();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        manageOff.setOnMouseClicked(e -> {
            try {
                manageOffHandle();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        manageProduct.setOnMouseClicked(e -> {
            try {
                manageProductHandle();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        history.setOnMouseClicked(e -> {
            try {
                historyHandle();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void load() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/PersonalInfoPage.fxml"));
        Node node = loader.load();
        this.personalInfoController = (PersonalInfoController) loader.getController();
        personalInfoController.initialize(userId);
        personalInfoController.load(seller);
        box.getChildren().addAll(node);
    }

    private void createOffHandle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CreateOffForSeller.fxml"));
        Node node = loader.load();
        CreateOfForSellerController createOfForSellerController = (CreateOfForSellerController) loader.getController();
        createOfForSellerController.initialize(userId);
        personalInfoController.updateAllBoxWithSingleNode(node);
    }

    private void createProductHandle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CreateSingleProductForSeller.fxml"));
        Node node = loader.load();
        CreateSingleProductForSellerController createSingleProductForSellerController = (CreateSingleProductForSellerController) loader.getController();
        createSingleProductForSellerController.initialize(userId);
        createSingleProductForSellerController.setPersonalInfoController(this.personalInfoController);
        personalInfoController.updateAllBoxWithSingleNode(node);
    }

    private void historyHandle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/TableOfOrders.fxml"));
        Node node = loader.load();
        OrderTableController orderTableController = (OrderTableController) loader.getController();
        orderTableController.initialize(userId);
        try {
            orderTableController.load(orderController.getOrderHistoryForSeller(null, true, 0, 50, Constants.manager.getToken()), this.personalInfoController);
            personalInfoController.setNodeForTableScrollPane(node);
        } catch (NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        }
    }

    private void manageOffHandle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/OffTable.fxml"));
        Node node = loader.load();
        OffTableController offTableController = (OffTableController) loader.getController();
        offTableController.initialize(userId);
        try {
            offTableController.load(offController.getAllOfForSellerWithFilter(null, true, 0, 50, Constants.manager.getToken()), this.personalInfoController);
            personalInfoController.setNodeForTableScrollPane(node);
        } catch (NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        }
    }

    private void manageProductHandle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ProductTableForSeller.fxml"));
        Node node = loader.load();
        ProductTableForSellerController productTableForSellerController = (ProductTableForSellerController) loader.getController();
        productTableForSellerController.initialize(userId);
        try {
            productTableForSellerController.load(productController.getAllProductWithFilterForSellerId((Map<String, String>) new HashMap<>().put(null, "sd")/*todo saljf*/, "startDate", true, 0, 50, Constants.manager.getToken()), this.personalInfoController);
            personalInfoController.setNodeForTableScrollPane(node);

        } catch (NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        }

    }
}
