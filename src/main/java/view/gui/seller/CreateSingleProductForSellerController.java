package view.gui.seller;

import controller.interfaces.category.ICategoryController;
import controller.interfaces.product.IProductController;
import controller.product.CategoryController;
import controller.product.ProductController;
import exception.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;
import model.Product;
import model.ProductSeller;
import view.cli.ControllerContainer;
import view.gui.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CreateSingleProductForSellerController implements InitializableController, Reloadable {
    private int userId;
    private Category category;
    private IProductController productController;
    private ICategoryController categoryController;
    private ArrayList<FeatureBox> featureBoxList;
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
    @FXML
    private VBox categoryBox;
    @FXML
    private VBox featuresBox;

    @Override
    public void initialize(int id) throws IOException {
        productController = (IProductController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ProductController);
        categoryController = (ICategoryController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CategoryController);
        featureBoxList = new ArrayList<>();
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
        try {
            load(categoryController.getCategory(1, Constants.manager.getToken()));
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }
    }

    public void load(Category mainCategory) throws IOException {
        loadCategoryBoxes(mainCategory);
    }

    private void loadCategoryBoxes(Category category) throws IOException {
        categoryBox.getChildren().removeAll();
        featuresBox.getChildren().removeAll();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CategotyForAdding.fxml"));
        CreateCategoryForAddingController createCategoryForAddingController = (CreateCategoryForAddingController) loader.getController();
        createCategoryForAddingController.initialize(category.getId());
        createCategoryForAddingController.load(category, this);
        categoryBox.getChildren().addAll((Collection<? extends Node>) loader.load());//todo check here
        category.getFeatures().forEach(i -> featureBoxList.add(new FeatureBox(i, i.getFeatureName(), i.getFeatureType())));
        featureBoxList.forEach(i -> featuresBox.getChildren().add(i.getContainer()));
    }

    @FXML
    public void uploadPhotoClicked() {
        //todo ....
    }

    @FXML
    public void saveButtonClicked() {
        if (isEveryThingOk()) {
            Product newProduct = new Product(nameTextField.getText(), brandTextField.getText(), descriptionField.getText());
            newProduct.setCategory(this.category);
            ProductSeller newProductSeller = new ProductSeller();
            featureBoxList.forEach(i -> newProduct.getCategoryFeatures().put(i.getCategoryFeature(), i.getValue()));
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
                Constants.manager.checkInputIsInt(amountTextField.getText()) && (category != null);
    }

    @Override
    public void reloadItems() {

    }

    @Override
    public void reloadItems(Object object) throws IOException {
        if (object instanceof Category) {
            category = (Category) object;
            load(category);
        }

    }

    private class FeatureBox {
        private HBox container;
        private Label name;
        private TextField description;
        private FeatureType type;
        private CategoryFeature categoryFeature;

        public FeatureBox(CategoryFeature categoryFeature, String name, FeatureType featureType) {
            this.categoryFeature = categoryFeature;
            this.name = new Label(name);
            this.type = featureType;
            container = new HBox();
            description = new TextField("");
            description.setPromptText(FeatureType.getType(featureType));
            description.setEditable(true);
            container.getChildren().addAll(this.name, description);
        }

        public CategoryFeature getCategoryFeature() {
            return categoryFeature;
        }

        public String getValue() {
            return name.getText();
        }

        public HBox getContainer() {
            return container;
        }
    }
}
