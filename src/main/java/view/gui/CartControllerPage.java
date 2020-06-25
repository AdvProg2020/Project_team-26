package view.gui;

import controller.interfaces.cart.ICartController;
import exception.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.*;
import view.cli.*;
import view.gui.interfaces.InitializableController;
import view.gui.product.SingleProductOfCartController;

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
    public void initialize(int id) throws IOException, InvalidTokenException {
        cartController = (ICartController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CartController);
        cart = cartController.getCart(Constants.manager.getToken());
        totalPriceText.setText("total price");//todo
        loadCart(cart);
           /* totalPriceLabel.textProperty().bind(
            }cartController.getTotalPrice(cart, Constants.manager.getToken()))//todo*/
    }

    private void loadCart(Cart cart) throws IOException {
        totalPriceText.setText("total price");//todo
        //totalPriceLabel.textProperty().bind(cart.)//todo
        loadProducts(cart);
    }

    private void loadProducts(Cart cart) throws IOException {
        cartProductsVBox.getChildren().removeAll();
        Map<ProductSeller, Integer> products = cart.getProduct();
        for (Map.Entry<ProductSeller, Integer> entry : products.entrySet()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ProductOfCart.fxml"));
            SingleProductOfCartController singleProductOfCartController = (SingleProductOfCartController) loader.getController();
            singleProductOfCartController.load(entry.getKey(), entry.getValue());
            cartProductsVBox.getChildren().add(loader.load());
        }
    }

    @FXML
    public void purchaseButtonClicked() throws IOException {
        try {
            cartController.setAddress(addressTextField.getText(), Constants.manager.getToken());
            if (!promoTextField.getText().isBlank() && !promoTextField.getText().equals(""))
                cartController.usePromoCode(promoTextField.getText(), Constants.manager.getToken());
            cartController.checkout(Constants.manager.getToken());
            //todo load main page
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.showLoginMenu();
        } catch (InvalidPromoCodeException | NoAccessException | PromoNotAvailableException | NotLoggedINException | NotEnoughCreditException | NotEnoughProductsException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        }
        //todo load the purchase page here
    }
}
