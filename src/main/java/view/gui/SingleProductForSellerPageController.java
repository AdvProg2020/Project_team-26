package view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import model.*;

import java.io.IOException;

public class SingleProductForSellerPageController implements InitializableController {
    private int productId;
    private Product product;
    private ProductSeller productSeller;
    @FXML
    private ImageView productImage;
    @FXML
    private Text categoryText;
    @FXML
    private Slider productRateSlider;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField brandTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button editButton;
    @FXML
    private Button uploadPhoto;
    @FXML
    private Label nameLabel;

    @Override
    public void initialize(int id) throws IOException {
        productId = id;

    }

    public void load(Product product, ProductSeller productSeller) {
        this.productSeller = productSeller;
        this.product = product;
        // productImage //todo
        categoryText.setText(product.getCategory().getName());
        productRateSlider.setValue(product.getAverageRate());
        productRateSlider.setValueChanging(false);
        nameTextField.setText(product.getName());
        brandTextField.setText(product.getBrand());
        amountTextField.setText("" + productSeller.getRemainingItems());
        descriptionTextArea.setText(product.getDescription());
        setEditable(false);
    }

    private void setEditable(boolean type) {
        nameTextField.setEditable(type);
        brandTextField.setEditable(type);
        amountTextField.setEditable(type);
        descriptionTextArea.setEditable(type);
    }

    @FXML
    public void editButtonClicked() {
        if (editButton.getText().equals("edit")) {
            setEditable(true);
            editButton.setText("update");
        } else {//todo change product

        }
    }
    @FXML
    public void uploadButtonClicked(){
        //todo
    }
}
