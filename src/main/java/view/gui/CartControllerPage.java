package view.gui;

import controller.interfaces.cart.ICartController;
import exception.InvalidTokenException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Cart;
import model.Product;
import model.ProductSeller;
import view.cli.ControllerContainer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartControllerPage implements InitializableController {
    private Cart cart;
    private ICartController cartController;
    @FXML
    VBox cartProductsVBox;
    @FXML
    Button purchaseButton;
    @FXML
    Label totalPriceLabel;


    @Override
    public void initialize(int id) throws IOException {
        cartController = (ICartController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CartController);
        try {
            cart = cartController.getCart(Constants.manager.getToken());
            totalPriceLabel.setText("total price");//todo
        //    totalPriceLabel.textProperty().bind(cart.)//todo
        } catch (InvalidTokenException e) {
            e.printStackTrace();//todo
        }


    }

    private void loadCart(Cart cart) {
        try {
            cart = cartController.getCart(Constants.manager.getToken());
            totalPriceLabel.setText("total price");//todo
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
        //todo load the purchase page here
    }
}
