package view.gui.product;

import controller.product.ProductController;
import exception.InvalidIdException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Product;
import view.cli.ControllerContainer;
import view.gui.comment.CommentController;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SingleProductController implements InitializableController {

    @FXML
    private VBox mainBox;
    @FXML
    private ImageView productImage;
    @FXML
    private Text nameText;
    @FXML
    private Text brandText;
    @FXML
    private Text rateText;
    @FXML
    private TextArea descriptionText;

    @Override
    public void initialize(int id) throws IOException {
        ProductController productController =
                (ProductController) Constants.manager.getControllerContainer().
                        getController(ControllerContainer.Controller.ProductController);
        try {
            Product product = productController.getProductById(id, Constants.manager.getToken());
            Image image = new Image(new ByteArrayInputStream(product.getImage()));

            productImage.setImage(image);
            nameText.setText(product.getName());
            brandText.setText(product.getBrand());
            rateText.setText(product.getAverageRate() + " / 5");
            descriptionText.setText(product.getDescription());

            // TODO: load product seller
            loadComments(product);
        } catch (InvalidIdException invalidIdException) {
            invalidIdException.printStackTrace();
            Constants.manager.back();
            // TODO: show proper error
        }
    }

    private void loadComments(Product product) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/Comment.fxml"));
        CommentController commentController = loader.getController();
        commentController.load(product);
        Node comments = loader.load();
        mainBox.getChildren().add(comments);
    }
}
