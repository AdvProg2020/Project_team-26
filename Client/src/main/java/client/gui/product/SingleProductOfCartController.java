package client.gui.product;

import client.ControllerContainer;
import client.connectionController.interfaces.cart.ICartController;
import client.connectionController.interfaces.product.IProductController;
import client.exception.*;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.Reloadable;
import client.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SingleProductOfCartController implements InitializableController {
    private ICartController cartController;
    private ProductSeller productSeller;
    private IProductController productController;
    private int amount;
    private Cart cart;
    private int cartId;

    @FXML
    private ImageView productImage;
    @FXML
    private Text priceText;
    @FXML
    private Text priceOffText;
    @FXML
    private Text totalPrice;
    @FXML
    private TextField amountTextField;
    @FXML
    private Button editButtonClick;
    @FXML
    private Label amountLabel;
    private Reloadable reloadable;

    @Override
    public void initialize(int id) throws IOException {
        cartController = (ICartController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CartController);
        productController = (IProductController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ProductController);
        this.cartId = id;
    }

    public void load(ProductSeller productSeller, int amount, Reloadable reloadable) {
        this.reloadable = reloadable;
        //todo add cart
        this.productSeller = productSeller;
        this.amount = amount;
        Product product = productController.getProductByProductSellerId(productSeller.getId());
        if (product.getImage() != null)
            productImage.setImage(new Image(new ByteArrayInputStream(product.getImage())));
        priceText.setText("" + productSeller.getPrice());
        priceOffText.setText(productSeller.getSeller().getFullName());//TODO
        totalPrice.setText("" + (amount * (productSeller.getPriceInOff() == productSeller.getPrice() ? productSeller.getPrice() : productSeller.getPriceInOff())));
        amountTextField.setText("" + amount);
        amountTextField.setEditable(false);
    }

    public void updateButtonClicked() throws IOException {
        if (editButtonClick.getText().equals("edit")) {
            amountTextField.setEditable(true);
            editButtonClick.setText("Update");
        } else {
            if (Constants.manager.checkInputIsInt(amountTextField.getText())) {
                try {
                    cartController.addOrChangeProduct(productSeller.getId(), Integer.parseInt(amountTextField.getText()), Constants.manager.getToken());
                    amountTextField.setEditable(false);
                    editButtonClick.setText("edit");
                    reloadable.reload();
                } catch (InvalidIdException | NotEnoughProductsException e) {
                    Constants.manager.showErrorPopUp(e.getMessage());
                } catch (InvalidTokenException e) {
                    Constants.manager.showErrorPopUp(e.getMessage());
                    Constants.manager.setTokenFromController();
                }
            }
        }
    }
}
