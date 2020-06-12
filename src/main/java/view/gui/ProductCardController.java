package view.gui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import model.Product;

public class ProductCardController {

    @FXML
    private Text rateText;
    @FXML
    private Text nameText;
    @FXML
    private Text brandText;
    @FXML
    private Text descriptionText;

    private Product product;

    public void load(Product product) {
        this.product = product;
        if(product != null) {
            rateText.setText("Rate: " + product.getAverageRate());
            nameText.setText(product.getName());
            brandText.setText(product.getBrand());
            descriptionText.setText(product.getDescription());
        }
    }

    public void open() {
        //Constants.manager.openPage();
    }
}
