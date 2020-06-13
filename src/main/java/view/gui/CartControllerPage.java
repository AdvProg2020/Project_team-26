package view.gui;

import controller.interfaces.cart.ICartController;
import exception.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.*;
import view.cli.*;
import view.gui.*;

import java.io.IOException;
import java.util.*;

public class CartControllerPage implements InitializableController {
    private Cart cart;
    private ICartController cartController;
    @FXML
    VBox cartProductsVBox;
    @FXML
    Button purchaseButton;
    @FXML
    Text totalPriceText;
    @FXML
    Label promoLabel;
    @FXML
    TextField addressTextField;
    @FXML
    TextField promoTextField;


    @Override
    public void initialize(int id) throws IOException {
        cartController = (ICartController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CartController);
        try {
            cart = cartController.getCart(Constants.manager.getToken());
            totalPriceText.setText("total price");//todo
           /* totalPriceLabel.textProperty().bind(
            }cartController.getTotalPrice(cart, Constants.manager.getToken()))//todo*/
        } catch (InvalidTokenException e) {
            e.printStackTrace();//todo
        }


    }

    private void loadCart(Cart cart) {
        try {
            cart = cartController.getCart(Constants.manager.getToken());
            totalPriceText.setText("total price");//todo
            //totalPriceLabel.textProperty().bind(cart.)//todo
        } catch (InvalidTokenException e) {
            e.printStackTrace();//todo
        }
    }

    private void loadProducts(Cart cart) throws IOException {
        cartProductsVBox.getChildren().removeAll();
        Map<ProductSeller, Integer> products = cart.getProduct();
        for (Map.Entry<ProductSeller, Integer> entry : products.entrySet()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ProductOFCart.fxml"));
            SingleProductOfCartController singleProductOfCartController = (SingleProductOfCartController) loader.getController();
            singleProductOfCartController.load(entry.getKey(), entry.getValue());
            cartProductsVBox.getChildren().add(loader.load());
        }
    }

    @FXML
    public void purchaseButtonClicked() {
        try {
            cartController.setAddress(addressTextField.getText(), Constants.manager.getToken());
            cartController.usePromoCode(promoTextField.getText(), Constants.manager.getToken());
            cartController.checkout(Constants.manager.getToken());
            //todo load main page
        } catch (InvalidTokenException e) {
            e.printStackTrace();//todo
        } catch (InvalidPromoCodeException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();//todo access exception
        } catch (PromoNotAvailableException e) {
            e.printStackTrace();//todo red label
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        } catch (NotEnoughCreditException e) {
            e.printStackTrace();//todo
        } catch (NotEnoughProductsException e) {
            e.printStackTrace();//todo tell
        }


        //todo load the purchase page here
    }
}
