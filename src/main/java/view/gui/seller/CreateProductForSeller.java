package view.gui.seller;

import controller.interfaces.product.IProductController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotSellerException;
import exception.ObjectAlreadyExistException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Category;
import model.Product;
import model.ProductSeller;
import org.hibernate.query.criteria.internal.expression.ConcatExpression;
import view.gui.Constants;
import view.gui.InitializableController;

import java.io.IOException;

public class CreateProductForSeller implements InitializableController {
    IProductController productController;
    private Product product;
    private Category category;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField brandTextField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private VBox categoryVBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button clearButton;
    @FXML
    private ImageView productImage;
    @FXML
    private VBox featuresBox;

    @Override
    public void initialize(int id) throws IOException {

    }

    @FXML
    public void clearAllClicked() {
        saveButton.setText("save");
        clearButton.setText("Clear all");
        amountTextField.setText("");
        nameTextField.setText("");
        brandTextField.setText("");
        descriptionField.setText("");
        priceTextField.setText("");
        productImage.setImage(null);
        featuresBox.getChildren().removeAll();
    }

    @FXML
    public void saveButtomClicked() {
        try {
            if (saveButton.getText().equals("AddSeller") && checkInputsForProductSeller()) {
                ProductSeller productSeller = new ProductSeller();
                productSeller.setRemainingItems(Integer.parseInt(amountTextField.getText()));
                productSeller.setPrice(Long.parseLong(priceTextField.getText()));
                productController.addSeller(product.getId(), productSeller, Constants.manager.getToken());
                //TODO reload
            } else if (checkInputsForProduct()) {
                Product newProduct = new Product(nameTextField.getText(), brandTextField.getText(), descriptionField.getText());
                newProduct.setCategory(category);
                //TODO add seller
                productController.createProduct(newProduct, Constants.manager.getToken());
                //TODO reload
            }
        } catch (ObjectAlreadyExistException e) {
            //todo red field
            saveButton.setText("AddSeller");
            product = (Product) e.getObject();
        } catch (NotSellerException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        }

    }

    private boolean checkInputsForProductSeller() {
        boolean result = true;
        if (amountTextField.getText().equals("") || !Constants.manager.checkInputIsInt(amountTextField.getText())) {
            result = false;
            //todo red
        }

        if (priceTextField.getText().equals("") || !Constants.manager.checkInputIsInt(priceTextField.getText())) {
            result = false;
            //todo red
        }
        return result;
    }

    private boolean checkInputsForProduct() {
        boolean result = true;
        //TODO check category and feartus first
        if (nameTextField.getText().equals("")) {
            result = false;
            //todo red
        }
        if (brandTextField.getText().equals("")) {
            result = false;
            //todo red
        }
        if (descriptionField.getText().equals("")) {
            result = false;
            //todo red
        }
        return result;
    }

    private void setEditable(boolean type) {

    }
}
