package view.gui.customer;

import controller.discount.PromoController;
import controller.interfaces.discount.IPromoController;
import controller.interfaces.order.IOrderController;
import controller.order.OrderController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import model.Customer;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.InitializableController;
import view.gui.PersonalInfoController;

import java.io.IOException;

public class CustomerButtonController implements InitializableController {
    private int userId;
    private PersonalInfoController personalInfoController;
    private IOrderController orderController;
    private IPromoController promoController;
    private Customer customer;
    @FXML
    private Button promo;
    @FXML
    private Button orders;
    @FXML
    private HBox box;

    @Override
    public void initialize(int id) throws IOException {
        orderController = (IOrderController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OrderController);
        promoController = (IPromoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.PromoController);
        this.userId = id;
        orders.setOnMouseClicked(e -> {
            orderHandle();
        });
        promo.setOnMouseClicked(e -> {
            promoHandle();
        });
    }

    private void orderHandle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/TableOfOrders.fxml"));
            OrderTableController orderTableControllerForCustomer = (OrderTableController) loader.getController();
            orderTableControllerForCustomer.initialize(userId);
            orderTableControllerForCustomer.load(orderController.getOrdersWithFilter("date", true, 0, 50, Constants.manager.getToken()), personalInfoController);
            personalInfoController.setNodeForTableScrollPane(loader.load());
        } catch (IOException ex) {
            //todo end task
        } catch (InvalidTokenException ex) {
            ex.printStackTrace();
        } catch (NoAccessException ex) {
            ex.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }
    }

    private void promoHandle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/promoCodesForCustomer.fxml"));
            PromoCodesForCustomer promoCodesForCustomer = (PromoCodesForCustomer) loader.getController();
            promoCodesForCustomer.initialize(userId);
            promoCodesForCustomer.load(promoController.getAllPromoCodeForCustomer("maxValidUse", true, 0, 50, Constants.manager.getToken()));
            personalInfoController.updateAllBox(loader.load());
        } catch (IOException ex) {
            //todo end task
        } catch (InvalidTokenException ex) {
            ex.printStackTrace();
        } catch (NoAccessException ex) {
            ex.printStackTrace();
        } catch (NotLoggedINException ex) {
            ex.printStackTrace();
        }
    }

    public void load(Customer customer) throws IOException {
        this.customer = customer;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/PersonalInfoPage.fxml"));
        this.personalInfoController = (PersonalInfoController) loader.getController();
        personalInfoController.initialize(userId);
        personalInfoController.load(customer);
        box.getChildren().addAll((Node) loader.load());
    }
}
