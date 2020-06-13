package view.gui;

import controller.interfaces.product.IProductController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotSellerException;
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
    IProductController productController;
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
    private Button productSellerInfoEditButton;
    @FXML
    private Button productInfoEditButton;
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
    }

    private void initPrimitiveFields() {
        productInfoEditButton.setText("Edit product");
        productSellerInfoEditButton.setText("Edit seller");
        nameTextField.setText(product.getName());
        brandTextField.setText(product.getBrand());
        amountTextField.setText("" + productSeller.getRemainingItems());
        descriptionTextArea.setText(product.getDescription());
        setEditableForProduct(false);
        setEditableForProductSeller(false);

    }

    private void setEditableForProductSeller(boolean type) {
        amountTextField.setEditable(type);
        priceTextField.setEditable(type);
    }

    private void setEditableForProduct(boolean type) {
        nameTextField.setEditable(type);
        brandTextField.setEditable(type);
        descriptionTextArea.setEditable(type);
    }


    @FXML
    public void productSellerInfoEditButtonClicked() {
        if (editButton.getText().equals("Edit seller")) {
            setEditableForProductSeller(true);
            editButton.setText("update");
        } else {//todo change product

        }
    }

    @FXML
    public void productInfoEditButtonClicked() {
        if (editButton.getText().equals("Edit product")) {
            setEditableForProduct(true);
            editButton.setText("Update product");
        } else {
            Product product = productSeller.getProduct().clone();
            product.setDescription(descriptionTextArea.getText());
            product.setBrand(brandTextField.getText());
            product.setName(nameTextField.getText());
            setEditableForProduct(false);
            productInfoEditButton.setText("Edit product");
            try {
                productController.editProduct(product.getId(), product, Constants.manager.getToken());
            } catch (InvalidIdException e) {
                e.printStackTrace();
            } catch (NotSellerException e) {
                e.printStackTrace();
            } catch (NoAccessException e) {
                e.printStackTrace();
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            } finally {
                //todo reload changes
            }
        }
    }

    @FXML
    public void cancleButtonClicked() {
        initPrimitiveFields();
    }


    @FXML
    public void uploadButtonClicked() {
        //todo set image
    }
}
