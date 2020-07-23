package client.gui.customer;


import client.ControllerContainer;
import client.connectionController.interfaces.account.*;
import client.connectionController.interfaces.discount.IPromoController;
import client.connectionController.interfaces.order.*;
import client.exception.*;
import client.gui.Constants;
import client.gui.PersonalInfoController;
import client.gui.interfaces.InitializableController;
import client.model.*;
import client.model.enums.MessageType;
import client.model.enums.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


import java.io.IOException;

public class CustomerButtonController implements InitializableController {
    private int userId;
    private PersonalInfoController personalInfoController;
    private IOrderController orderController;
    private IPromoController promoController;
    private IShowUserController showUserController;
    private User customer;
    private Node personalPageNode;
    @FXML
    private ImageView profileImageView;
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
        Constants.manager.sendMessageTOWebSocket("login", new Message(user.getUsername(), "", "", MessageType.JOIN, Role.CUSTOMER));
        Constants.manager.setLoggedInUser(user);
        if (user.getRole() != Role.CUSTOMER || !Constants.manager.isLoggedIn())
            throw new NoAccessException("must be customer");
        this.userId = id;
        this.customer = (User) user;
        load();
    }

    @FXML
    public void orderHandle() throws IOException {
        personalPageHandle();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/TableOfOrders.fxml"));
            Node node = loader.load();
            OrderTableController orderTableControllerForCustomer = (OrderTableController) loader.getController();
            orderTableControllerForCustomer.initialize(userId);
            orderTableControllerForCustomer.load(orderController.getOrdersWithFilter("date", true, 0, 50, Constants.manager.getToken()), personalInfoController);
            personalInfoController.setNodeForTableScrollPane(node);
        } catch (InvalidTokenException ex) {
            Constants.manager.showErrorPopUp(ex.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NoAccessException ex) {
            Constants.manager.showErrorPopUp(ex.getMessage());
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        }
    }

    @FXML
    public void cart() throws IOException {
        Constants.manager.openCart();
    }

    @FXML
    public void personalPageHandle() {
        if (box.getChildren().size() > 1) {
            box.getChildren().remove(1);
        }
        box.getChildren().addAll(personalPageNode);
    }

    @FXML
    public void auctionHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/AuctionPageForCustomer.fxml"));
        Node node = loader.load();
        AuctionPageController auctionPageController = (AuctionPageController) loader.getController();
        try {
            auctionPageController.initialize(userId);
            auctionPageController.load(customer);
            if (box.getChildren().size() > 1) {
                box.getChildren().remove(1);
            }
            box.getChildren().addAll(node);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }
    }

    public void support() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CustomerSupport.fxml"));
        Node node = loader.load();
        SupportPageController supportPageController = (SupportPageController) loader.getController();
        try {
            supportPageController.initialize(userId);
            supportPageController.load(customer);
            if (box.getChildren().size() > 1) {
                box.getChildren().remove(1);
            }
            box.getChildren().addAll(node);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void promoHandle() throws IOException {
        personalPageHandle();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/promoCodesForCustomer.fxml"));
            Node node = loader.load();
            PromoCodesForCustomer promoCodesForCustomer = (PromoCodesForCustomer) loader.getController();
            promoCodesForCustomer.initialize(userId);
            promoCodesForCustomer.load(promoController.getAllPromoCodeForCustomer("maxValidUse", true, 0, 50, Constants.manager.getToken()));
            personalInfoController.updateAllBoxWithSingleNode(node);
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
        personalPageNode = loader.load();
        this.personalInfoController = (PersonalInfoController) loader.getController();
        personalInfoController.initialize(userId);
        personalInfoController.load(customer, profileImageView);
        box.getChildren().addAll(personalPageNode);
    }
}
