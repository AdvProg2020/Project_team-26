package view.gui;

import controller.interfaces.cart.ICartController;
import exception.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.*;
import view.cli.*;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.Reloadable;
import view.gui.product.SingleProductOfCartController;

import java.io.IOException;
import java.util.*;

public class CartControllerPage implements InitializableController, Reloadable {
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
    }

    private void loadCart(Cart cart) throws IOException {
        try {
            totalPriceText.setText("" + cartController.getTotalPrice(this.cart, Constants.manager.getToken()));
        } catch (InvalidTokenException e) {
            e.printStackTrace();//TODO
        }

        loadProducts(cart);
    }

    private void loadProducts(Cart cart) throws IOException {
        cartProductsVBox.getChildren().removeAll(cartProductsVBox.getChildren());
        Map<ProductSeller, Integer> products = cart.getProduct();
        products.forEach((key, value) -> {
            if (value > 0) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ProductOfCart.fxml"));
                try {
                    Node node = loader.load();
                    SingleProductOfCartController singleProductOfCartController = (SingleProductOfCartController) loader.getController();
                    singleProductOfCartController.initialize(key.getId());
                    singleProductOfCartController.load(key, value, this::reload);
                    cartProductsVBox.getChildren().add(node);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void purchaseButtonClicked() throws IOException {
        try {
            cartController.setAddress(addressTextField.getText(), Constants.manager.getToken());
            if (!promoTextField.getText().isBlank() && !promoTextField.getText().equals(""))
                cartController.usePromoCode(promoTextField.getText(), Constants.manager.getToken());
            cartController.checkout(Constants.manager.getToken());
            Constants.manager.showRandomPromoIfUserGet();
            Constants.manager.openPage("AllProducts", 0);
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.showLoginMenu();
        } catch (InvalidPromoCodeException | NoAccessException | PromoNotAvailableException | NotLoggedINException | NotEnoughCreditException | NotEnoughProductsException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        }
    }

    @Override
    public void reload() throws IOException {
        cartProductsVBox.getChildren().removeAll(cartProductsVBox.getChildren());
        loadCart(this.cart);
    }
}
