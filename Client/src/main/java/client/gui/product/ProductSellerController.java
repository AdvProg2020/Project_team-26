package client.gui.product;

import client.ControllerContainer;
import client.connectionController.interfaces.cart.ICartController;
import client.exception.*;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.ProductSeller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ProductSellerController implements InitializableController {
    ICartController cartController;
    private int productSellerId;
    @FXML
    private Label sellerName;
    @FXML
    private Label brand;
    @FXML
    private Label priceInOff;
    @FXML
    private Label price;
    @FXML
    private Button addToCart;
    @FXML
    private TextField amount;

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException {
        cartController = (ICartController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CartController);
        amount.setEditable(true);
        addToCart.setText("add To Cart");
    }

    public void load(ProductSeller productSeller) {
        this.productSellerId = productSeller.getId();
        sellerName.setText(productSeller.getSeller().getFullName());
        brand.setText(productSeller.getSeller().getCompanyName());
        priceInOff.setText(productSeller.getPrice() == productSeller.getPriceInOff() ? "" : "" + productSeller.getPrice());
        price.setText("" + productSeller.getPrice());
        amount.setText("1");
    }

    @FXML
    private void handleAdd() throws IOException {
        System.out.println(amount.getText());
        if (Constants.manager.checkInputIsInt(amount.getText())) {
            if (addToCart.getText().equals("add To Cart")) {
                try {
                    cartController.addOrChangeProduct(productSellerId, Integer.parseInt(amount.getText()), Constants.manager.getToken());
                    addToCart.setText("Added");
                    amount.setEditable(false);
                } catch (InvalidIdException | NotEnoughProductsException e) {
                    Constants.manager.showErrorPopUp(e.getMessage());
                } catch (InvalidTokenException e) {
                    Constants.manager.showErrorPopUp(e.getMessage());
                }
            }
        } else {
            Constants.manager.showErrorPopUp("enter int for amount");
        }
    }
}
