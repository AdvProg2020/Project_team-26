package view.gui.seller;

import controller.interfaces.category.ICategoryController;
import controller.interfaces.product.IProductController;
import exception.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import model.Product;
import model.ProductSeller;
import model.enums.FeatureType;
import view.cli.ControllerContainer;
import view.gui.*;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.Reloadable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;

public class CreateSingleProductForSellerController implements InitializableController, Reloadable {
    private int userId;
    private Category category;
    private IProductController productController;
    private ICategoryController categoryController;
    private ArrayList<FeatureBox> featureBoxList;
    private CategoryListController categoryListController;
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
    @FXML
    private Button imageChooserButton;
    private File imageFile;
    private PersonalInfoController personalInfoController;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CategoryList.fxml"));
        Node node = loader.load();
        this.categoryListController = (CategoryListController) loader.getController();
        categoryListController.initialize(1);
        categoryListController.setReloadable(this::reload);
        categoryBox.getChildren().removeAll(categoryBox.getChildren());
        categoryBox.getChildren().addAll(node);
        imageChooserButton.setOnMouseClicked(e -> {
            try {
                setFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void setPersonalInfoController(PersonalInfoController personalInfoController) {
        this.personalInfoController = personalInfoController;
    }

    private void setFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        this.imageFile = fileChooser.showOpenDialog(stage);
        if (imageFile != null)
            productImage.setImage(new Image(new ByteArrayInputStream(Files.readAllBytes(imageFile.toPath()))));

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CategoryList.fxml"));
        Node node = loader.load();
        this.categoryListController = (CategoryListController) loader.getController();
        categoryListController.initialize(category.getId());
        categoryListController.setReloadable(this::reload);
        categoryBox.getChildren().removeAll(categoryBox.getChildren());
        categoryBox.getChildren().addAll(node);//todo check here
        featuresBox.getChildren().removeAll(featuresBox.getChildren());
        category.getFeatures().forEach(i -> featureBoxList.add(new FeatureBox(i, i.getFeatureName(), i.getFeatureType())));
        featureBoxList.forEach(i -> featuresBox.getChildren().add(i.getContainer()));
    }

    @FXML
    public void saveButtonClicked() throws IOException {
        category = categoryListController.getCategory();
        if (isEveryThingOk()) {
            Product newProduct = new Product(nameTextField.getText(), brandTextField.getText(), descriptionField.getText());
            newProduct.setCategory(this.category);
            newProduct.setImage(Files.readAllBytes(imageFile.toPath()));
            ProductSeller newProductSeller = new ProductSeller();
            featureBoxList.forEach(i -> newProduct.getCategoryFeatures().put(i.getCategoryFeature(), i.getValue()));
            newProductSeller.setPrice(Long.parseLong(priceTextField.getText()));
            newProductSeller.setRemainingItems(Integer.parseInt(amountTextField.getText()));
            newProduct.getSellerList().add(newProductSeller);
            try {
                productController.createProduct(newProduct, Constants.manager.getToken());
                this.personalInfoController.clearBox();
            } catch (ObjectAlreadyExistException e) {
                try {
                    productController.addSeller(((Product) e.getObject()).getId(), newProductSeller, Constants.manager.getToken());
                    this.personalInfoController.clearBox();
                } catch (NotSellerException | NoAccessException ex) {
                    Constants.manager.showErrorPopUp(e.getMessage());
                } catch (InvalidTokenException ex) {
                    Constants.manager.showErrorPopUp(e.getMessage());
                    Constants.manager.setTokenFromController();
                }
            } catch (NotSellerException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
            } catch (InvalidTokenException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
                Constants.manager.setTokenFromController();
            }
        }


    }

    private boolean isEveryThingOk() {
        if (nameTextField.getText().isEmpty()|| nameTextField.getText().isBlank()){

        }
        return !nameTextField.getText().isEmpty() && !priceTextField.getText().isEmpty() && !amountTextField.getText().isEmpty() &&
                !descriptionField.getText().isEmpty() && !brandTextField.getText().isEmpty() &&
                !nameTextField.getText().isBlank() && !priceTextField.getText().isBlank() && !amountTextField.getText().isBlank() &&
                !descriptionField.getText().isEmpty() && !brandTextField.getText().isEmpty() &&
                !nameTextField.getText().isBlank() && Constants.manager.checkIsLong(priceTextField.getText()) &&
                Constants.manager.checkInputIsInt(amountTextField.getText()) && (category != null) && (imageFile != null);
    }

    @Override
    public void reload() throws IOException {
        loadCategoryBoxes(categoryListController.getCategory());
    }

    public class FeatureBox {
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
