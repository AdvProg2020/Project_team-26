package view.gui;

import controller.interfaces.product.IProductController;
import exception.InvalidIdException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Product;
import view.gui.interfaces.InitializableController;

import java.io.IOException;

public class CompareController implements InitializableController {
    @FXML
    private ImageView productImage;
    @FXML
    private TextField name;
    @FXML
    private TextField brand;
    @FXML
    private TextArea description;
    @FXML
    private TextField rate;
    @FXML
    private TextField price;
    @FXML
    private VBox box;

    IProductController productController;
    int productId;

    @Override
    public void initialize(int id) throws IOException, InvalidIdException {
        Product product = null;
        product = productController.getProductById(id, Constants.manager.getToken());
        productImage.setImage(productImage.getImage());
        name.setText(product.getName());
        brand.setText(product.getBrand());
        description.setText(product.getDescription());
        rate.setText("" + product.getAverageRate() + "/5");
        price.setText("" + product.getMinimumPrice());
        name.setEditable(false);
        brand.setEditable(false);
        description.setEditable(false);
        rate.setEditable(false);
        price.setEditable(false);
        product.getCategoryFeatures().entrySet().forEach(f -> {
            HBox hBox = new HBox();
            Label label = new Label(f.getKey().getFeatureName());
            TextField text = new TextField(f.getValue());
            text.setEditable(false);
            hBox.getChildren().addAll(label, text);
            box.getChildren().add(hBox);
        });
    }
}
