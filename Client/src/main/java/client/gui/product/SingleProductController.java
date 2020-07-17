package client.gui.product;

import client.gui.Constants;
import client.gui.comment.CommentController;
import client.gui.interfaces.InitializableController;
import controller.interfaces.product.IProductController;
import controller.interfaces.review.IRatingController;
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
import model.Category;
import model.CategoryFeature;
import model.Product;
import model.ProductSeller;
import view.cli.ControllerContainer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingleProductController implements InitializableController {
    private IProductController productController;
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
    private Text categoryText;
    @FXML
    private TableView featuresTable;
    @FXML
    private TableColumn<String, Features> nameColumn;
    @FXML
    private TableColumn<String, Features> featureColumn;

    public SingleProductController() {
        productController = (IProductController) Constants.manager.getControllerContainer().
                        getController(ControllerContainer.Controller.ProductController);
        ratingController = (IRatingController) Constants.manager.getControllerContainer().
                getController(ControllerContainer.Controller.RatingController);
    }

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException {
        try {
            product = productController.getProductById(id, Constants.manager.getToken());
            try {
                if (product.getImage() != null) {
                    Image image = new Image(new ByteArrayInputStream(product.getImage()));
                    productImage.setImage(image);
                }
            } catch (Exception e) {
                Constants.manager.showErrorPopUp("There was no image for this product.\nDefault image is shown.");
            }
            nameText.setText(product.getName());
            brandText.setText(product.getBrand());
            if (product.getAverageRate() != null)
                rateText.setText(product.getAverageRate() + " / 5");
            else
                rateText.setText("Not Rated");
            descriptionText.setText(product.getDescription());
            categoryText.setText(getCategoryAddress(product.getCategory()));
            loadCategoryFeatures(product.getCategoryFeatures());
            loadSellers(product);
            loadComments(product);
        } catch (InvalidIdException invalidIdException) {
            invalidIdException.printStackTrace();
            Constants.manager.showErrorPopUp("There is no product with this id.");
            Constants.manager.back();
        } catch (NoAccessException e) {
            e.printStackTrace();
        }
    }

    private void loadCategoryFeatures(Map<CategoryFeature, String> map) {
        List<Features> features = new ArrayList<>();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        featureColumn.setCellValueFactory(new PropertyValueFactory<>("feature"));
        map.forEach((key, value) -> features.add(new Features(key.getFeatureName(), value)));
        featuresTable.setItems(FXCollections.observableList(features));
    }

    private void loadSellers(Product product) throws IOException, NoAccessException, InvalidTokenException {
        for (ProductSeller seller : product.getSellerList()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ProductSeller.fxml"));
            Parent sellerElement = loader.load();
            ProductSellerController productSellerController = loader.getController();
            productSellerController.initialize(seller.getId());
            productSellerController.load(seller);
            mainBox.getChildren().add(sellerElement);
        }
    }

    private void loadComments(Product product) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/Comment.fxml"));
        Node comments = loader.load();
        CommentController commentController = loader.getController();
        commentController.load(product);
        mainBox.getChildren().add(comments);
    }

    public void rate() throws IOException {
        try {
            ratingController.rate(rateSlider.getValue(), product.getId(), Constants.manager.getToken());
            Constants.manager.reload();
        } catch (NotBoughtTheProductException | NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.setTokenFromController();
            Constants.manager.showErrorPopUp("Your token was invalid.");
        }
    }

    public void addToCompareList() {
        Constants.manager.addToCompareList(product.getId());
        Constants.manager.reloadTop();
    }

    public class Features {
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

    private String getCategoryAddress(Category category) {
        if(category.getParent().equals(category)) {
            return category.getName();
        }
        return getCategoryAddress(category.getParent()) + " -> " + category.getName();
    }
}
