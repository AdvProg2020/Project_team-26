package client.gui;

import client.ControllerContainer;
import client.connectionController.interfaces.product.IProductController;
import client.exception.*;
import client.gui.interfaces.InitializableController;
import client.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
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
        this.productId = id;
        productController = (IProductController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ProductController);
        Product product = null;
        product = productController.getProductById(id, Constants.manager.getToken());
        if (product.getImage() != null)
            productImage.setImage(new Image(new ByteArrayInputStream(product.getImage())));
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
        product.getCategoryFeatures().forEach((k, v) -> {
            HBox hBox = new HBox();
            Label label = new Label(k.getFeatureName());
            TextField text = new TextField(v);
            text.setEditable(false);
            hBox.getChildren().addAll(label, text);
            box.getChildren().add(hBox);
        });
    }
}
