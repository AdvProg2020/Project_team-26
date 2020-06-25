package view.gui.customer;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.discount.IPromoController;
import controller.interfaces.order.IOrderController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import model.Customer;
import model.User;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.PersonalInfoController;

import java.io.IOException;

public class CustomerButtonController implements InitializableController {
    private int userId;
    private PersonalInfoController personalInfoController;
    private IOrderController orderController;
    private IPromoController promoController;
    private IShowUserController showUserController;
    private Customer customer;
    @FXML
    private Button promo;
    @FXML
    private Button orders;
    @FXML
    private HBox box;

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException {
        orderController = (IOrderController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OrderController);
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        promoController = (IPromoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.PromoController);
        User user = showUserController.getUserById(id, Constants.manager.getToken());
        this.customer = (Customer) user;
        this.userId = id;
        load();
        orders.setOnMouseClicked(e -> {
            try {
                orderHandle();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        promo.setOnMouseClicked(e -> {
            try {
                promoHandle();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void orderHandle() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/TableOfOrders.fxml"));
            OrderTableController orderTableControllerForCustomer = (OrderTableController) loader.getController();
            orderTableControllerForCustomer.initialize(userId);
            orderTableControllerForCustomer.load(orderController.getOrdersWithFilter("date", true, 0, 50, Constants.manager.getToken()), personalInfoController);
            personalInfoController.setNodeForTableScrollPane(loader.load());
        } catch (InvalidTokenException ex) {
            Constants.manager.showErrorPopUp(ex.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NoAccessException ex) {
            Constants.manager.showErrorPopUp(ex.getMessage());
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        }
    }

    private void promoHandle() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/promoCodesForCustomer.fxml"));
            PromoCodesForCustomer promoCodesForCustomer = (PromoCodesForCustomer) loader.getController();
            promoCodesForCustomer.initialize(userId);
            promoCodesForCustomer.load(promoController.getAllPromoCodeForCustomer("maxValidUse", true, 0, 50, Constants.manager.getToken()));
            personalInfoController.updateAllBox(loader.load());
        } catch (InvalidTokenException ex) {
            Constants.manager.showErrorPopUp(ex.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NoAccessException ex) {
            Constants.manager.showErrorPopUp(ex.getMessage());
        } catch (NotLoggedINException ex) {
            Constants.manager.showLoginMenu();
        }
    }

    public void load() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/PersonalInfoPage.fxml"));
        Node node = loader.load();
        this.personalInfoController = (PersonalInfoController) loader.getController();
        personalInfoController.initialize(userId);
        personalInfoController.load(customer);
        box.getChildren().addAll(node);
    }
}
