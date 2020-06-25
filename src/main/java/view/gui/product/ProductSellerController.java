package view.gui.product;

import controller.interfaces.cart.ICartController;
import controller.interfaces.category.ICategoryController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotEnoughProductsException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.ProductSeller;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
