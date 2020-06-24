package view.gui.product;

import exception.InvalidTokenException;
import exception.NoAccessException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.ProductSeller;
import view.gui.interfaces.InitializableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductSellerController implements InitializableController {
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

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException {
        addToCart.setOnMouseClicked(e->{

        });

    }
    public void load(ProductSeller productSeller){

    }
    private void handleAdd(){

    }
}
