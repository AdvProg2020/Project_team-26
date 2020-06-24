package view.gui.seller;

import controller.interfaces.discount.IOffController;
import controller.interfaces.order.IOrderController;
import controller.interfaces.product.IProductController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import model.Seller;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.PersonalInfoController;
import view.gui.customer.OrderTableController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SellerButtonsController implements InitializableController {
    private IOffController offController;
    private IProductController productController;
    private IOrderController orderController;
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

    @Override
    public void initialize(int id) throws IOException {
        offController = (IOffController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OffController);
        productController = (IProductController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ProductController);
        orderController = (IOrderController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OrderController);
        this.userId = id;
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

    public void load(PersonalInfoController personalInfoController, Seller seller) {
        this.seller = seller;
        this.personalInfoController = personalInfoController;
    }

    private void createOffHandle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CreateOffForSeller.fxml"));
        CreateOfForSellerController createOfForSellerController = (CreateOfForSellerController) loader.getController();
        createOfForSellerController.initialize(userId);
        personalInfoController.updateAllBox(loader.load());
    }

    private void createProductHandle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CreateSingleProductForSeller.fxml"));
        CreateSingleProductForSellerController createSingleProductForSellerController = (CreateSingleProductForSellerController) loader.getController();
        createSingleProductForSellerController.initialize(userId);
        personalInfoController.updateAllBox(loader.load());
    }

    private void historyHandle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/TableOfOrders.fxml"));
        OrderTableController orderTableController = (OrderTableController) loader.getController();
        orderTableController.initialize(userId);
        try {
            orderTableController.load(orderController.getOrderHistoryForSeller("startDate", true, 0, 50, Constants.manager.getToken()), this.personalInfoController);
            personalInfoController.setNodeForTableScrollPane(loader.load());
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {//todo
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }
    }

    private void manageOffHandle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/OffTable.fxml"));
        OffTableController offTableController = (OffTableController) loader.getController();
        offTableController.initialize(userId);
        try {
            offTableController.load(offController.getAllOfForSellerWithFilter("startDate", true, 0, 50, Constants.manager.getToken()), this.personalInfoController);
            personalInfoController.setNodeForTableScrollPane(loader.load());
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {//todo
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }
    }

    private void manageProductHandle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ProductTableForSeller.fxml"));
        ProductTableForSellerController productTableForSellerController = (ProductTableForSellerController) loader.getController();
        productTableForSellerController.initialize(userId);
        try {
            productTableForSellerController.load(productController.getAllProductWithFilterForSellerId((Map<String, String>) new HashMap<>().put("deafult", "sd")/*todo saljf*/, "startDate", true, 0, 50, Constants.manager.getToken()), this.personalInfoController);
            personalInfoController.setNodeForTableScrollPane(loader.load());
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {//todo
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }

    }
}
