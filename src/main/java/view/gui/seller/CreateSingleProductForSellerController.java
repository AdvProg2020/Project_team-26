package view.gui.seller;

import controller.interfaces.product.IProductController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotSellerException;
import exception.ObjectAlreadyExistException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Category;
import model.Product;
import model.ProductSeller;
import view.gui.Constants;
import view.gui.InitializableController;

import java.io.IOException;

public class CreateSingleProductForSellerController implements InitializableController {
    private int userId;
    private IProductController productController;
    @FXML
    private ImageView productImage;
    @FXML
    private Button saveButton;
    @FXML
    private Button clearButton;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField brandTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private ScrollPane categoryScrollPane;

    @Override
    public void initialize(int id) throws IOException {
        this.userId = id;
        productImage.setImage(null);
        saveButton.setText("save");
        clearButton.setText("clear");
        descriptionField.setPromptText("description");
        brandTextField.setPromptText("brand");
        nameTextField.setPromptText("name");
        priceTextField.setPromptText("price");
        amountTextField.setPromptText("amount");
        descriptionField.setText("");
        brandTextField.setText("");
        nameTextField.setText("");
        priceTextField.setText("");
        amountTextField.setText("");

    }

    @FXML
    public void clearAllClicked() throws IOException {
        initialize(this.userId);
    }

    @FXML
    public void saveButtonClicked() {
        if (isEveryThingOk()) {
            Product newProduct = new Product(nameTextField.getText(), brandTextField.getText(), descriptionField.getText());
            //  newProduct.setCategory(); todo
            ProductSeller newProductSeller = new ProductSeller();
            newProductSeller.setPrice(Long.parseLong(priceTextField.getText()));
            newProductSeller.setRemainingItems(Integer.parseInt(amountTextField.getText()));
            newProduct.getSellerList().add(newProductSeller);
            try {
                productController.createProduct(newProduct, Constants.manager.getToken());
            } catch (ObjectAlreadyExistException e) {
                try {
                    productController.addSeller(((Product) e.getObject()).getId(), newProductSeller, Constants.manager.getToken());
                } catch (NotSellerException ex) {
                    ex.printStackTrace();
                } catch (NoAccessException ex) {
                    ex.printStackTrace();
                } catch (InvalidTokenException ex) {
                    ex.printStackTrace();
                }
            } catch (NotSellerException e) {
                e.printStackTrace();
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            }

        }//todo message please fill all


    }

    private boolean isEveryThingOk() {
        return !nameTextField.getText().isEmpty() && !priceTextField.getText().isEmpty() && !amountTextField.getText().isEmpty() &&
                !descriptionField.getText().isEmpty() && !brandTextField.getText().isEmpty() &&
                !nameTextField.getText().isBlank() && !priceTextField.getText().isBlank() && !amountTextField.getText().isBlank() &&
                !descriptionField.getText().isEmpty() && !brandTextField.getText().isEmpty() &&
                !nameTextField.getText().isBlank() && Constants.manager.checkIsLong(priceTextField.getText()) &&
                Constants.manager.checkInputIsInt(amountTextField.getText());
        //check image and category todo
    }

    public void setcategory(){

    }

}
