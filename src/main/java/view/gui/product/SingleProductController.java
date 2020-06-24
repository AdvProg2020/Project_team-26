package view.gui.product;

import controller.interfaces.review.IRatingController;
import controller.product.ProductController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotBoughtTheProductException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.CategoryFeature;
import model.Product;
import model.ProductSeller;
import view.cli.ControllerContainer;
import view.gui.comment.CommentController;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.*;

import javax.xml.catalog.CatalogFeatures;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SingleProductController implements InitializableController {
    private IRatingController ratingController;
    private Product product;

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
    @FXML
    private Slider rateSlider;
    @FXML
    private TableView featuresTable;
    @FXML
    private TableColumn<String, Features> nameColumn;
    @FXML
    private TableColumn<String, Features> featureColumn;

    public SingleProductController() {
        ratingController = (IRatingController) Constants.manager.getControllerContainer().
                getController(ControllerContainer.Controller.RatingController);
    }

    @Override
    public void initialize(int id) throws IOException {
        ProductController productController =
                (ProductController) Constants.manager.getControllerContainer().
                        getController(ControllerContainer.Controller.ProductController);
        try {
            product = productController.getProductById(id, Constants.manager.getToken());
            Image image = new Image(new ByteArrayInputStream(product.getImage()));
            productImage.setImage(image);
            nameText.setText(product.getName());
            brandText.setText(product.getBrand());
            rateText.setText(product.getAverageRate() + " / 5");
            descriptionText.setText(product.getDescription());
            fillTable(product.getCategoryFeatures());
            loadSellers(product);
            loadComments(product);
        } catch (InvalidIdException invalidIdException) {
            invalidIdException.printStackTrace();
            Constants.manager.showErrorPopUp("There is no product with this id.");
            Constants.manager.back();
        }
    }

    private void fillTable(Map<CategoryFeature, String> map) {
        ArrayList<Features> features = new ArrayList<>();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        featureColumn.setCellValueFactory(new PropertyValueFactory<>("feature"));
        map.entrySet().forEach(i -> features.add(new Features(i.getKey().getFeatureName(), i.getValue())));
        featuresTable.setItems(FXCollections.observableList(features));
    }

    private void loadSellers(Product product) throws IOException {
        for (ProductSeller seller : product.getSellerList()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ProductSeller.fxml"));
            ProductSellerController productSellerController = loader.getController();
            productSellerController.load(seller);
            Parent sellerElement = loader.load();
            mainBox.getChildren().add(sellerElement);
        }
    }

    private void loadComments(Product product) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/Comment.fxml"));
        CommentController commentController = loader.getController();
        commentController.load(product);
        Node comments = loader.load();
        mainBox.getChildren().add(comments);
    }

    public void rate() throws IOException {
        try {
            ratingController.rate(rateSlider.getValue(), product.getId(), Constants.manager.getToken());
        } catch (NotBoughtTheProductException | NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.setTokenFromController();
            Constants.manager.showErrorPopUp("Your token was invalid.");
        }
    }

    public void addToCompareList() {
        Constants.manager.addToCompareList(product.getId());
    }

    private class Features {
        private String name;
        private String feature;

        public Features(String name, String feature) {
            this.name = name;
            this.feature = feature;
        }

        public String getName() {
            return name;
        }

        public String getFeature() {
            return feature;
        }
    }
}
