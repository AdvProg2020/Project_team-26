package view.gui.customer;

import controller.interfaces.discount.IPromoController;
import controller.interfaces.order.IOrderController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import model.Customer;
import view.gui.Constants;
import view.gui.InitializableController;
import view.gui.PersonalInfoController;

import java.io.IOException;

public class CustomerButtonController implements InitializableController {
    private int userId;
    private PersonalInfoController personalInfoController;
    private IOrderController orderController;
    private Customer customer;
    private IPromoController promoController;
    @FXML
    private Button promo;
    @FXML
    private Button orders;

    @Override
    public void initialize(int id) throws IOException {
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
            // orderController.getOrdersWithFilter()//todo
            orderTableControllerForCustomer.load(orderController.getOrders(Constants.manager.getToken()), personalInfoController);
            personalInfoController.setNodeForTableScrollPane(loader.load());
        } catch (IOException ex) {
            //todo end task
        } catch (InvalidTokenException ex) {
            ex.printStackTrace();
        } catch (NoAccessException ex) {
            ex.printStackTrace();
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

    public void load(PersonalInfoController personalInfoController, Customer customer) {
        this.customer = customer;
        this.personalInfoController = personalInfoController;
    }
}
