package view.gui.product;

import controller.interfaces.cart.ICartController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NotEnoughProductsException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Cart;
import model.ProductSeller;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.*;
import view.gui.interfaces.*;
import java.io.IOException;

public class SingleProductOfCartController implements InitializableController {
    private ICartController cartController;
    private ProductSeller productSeller;
    private Reloadable reloadable;
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

    @Override
    public void initialize(int id) throws IOException {
        cartController = (ICartController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CartController);
        this.cartId = id;
    }

    public void load(ProductSeller productSeller, int amount) {
        this.reloadable = reloadable;
        //todo add cart
        this.productSeller = productSeller;
        this.amount = amount;
        // productImage.setImage(productSeller.getProduct().getImage());TODO
        priceText.setText("" + productSeller.getPrice());
        priceOffText.setText("" + productSeller.getPriceInOff());
        totalPrice.setText("" + amount * (productSeller.getPriceInOff() == productSeller.getPrice() ? productSeller.getPrice() : productSeller.getPriceInOff()));
        amountTextField.setText("" + amount);
        amountTextField.setEditable(false);
    }

    public void updateButtonClicked() {
        if (editButtonClick.getText().equals("Edit")) {
            amountTextField.setEditable(true);
            editButtonClick.setText("Update");
        } else {
            if (Constants.manager.checkInputIsInt(amountTextField.getText())) {
                try {
                    cartController.addOrChangeProduct(productSeller.getId(), Integer.parseInt(amountTextField.getText()), Constants.manager.getToken());
                    amountTextField.setEditable(false);
                    editButtonClick.setText("Edit");
                } catch (InvalidIdException e) {
                    e.printStackTrace();
                } catch (NotEnoughProductsException e) {
                    //todo change label to red
                } catch (InvalidTokenException e) {
                    e.printStackTrace();//todo
                }
            }
        }
    }
}
