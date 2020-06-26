package view.gui.product;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Product;
import view.gui.Constants;
import view.gui.interfaces.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ProductCardController {

    @FXML
    private Text rateText;
    @FXML
    private Text nameText;
    @FXML
    private Text brandText;
    @FXML
    private Text descriptionText;
    @FXML
    private Text priceText;
    @FXML
    private ImageView productImage;

    private Product product;

    public void load(Product product) {
        this.product = product;
        if(product != null) {
            rateText.setText("Rate: " + product.getAverageRate());
            nameText.setText(product.getName());
            brandText.setText(product.getBrand());
            descriptionText.setText(product.getDescription());
            try {
                if(product.getImage() != null) {
                    Image image = new Image(new ByteArrayInputStream(product.getImage()));
                    productImage.setImage(image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(product.getPrice() != null) {
                priceText.setText(product.getPrice() + " T");
            } else {
                priceText.setText("- T");
            }
        }
    }

    public void open() throws IOException {
        Constants.manager.openPage("SingleProduct", product.getId());
    }
}
