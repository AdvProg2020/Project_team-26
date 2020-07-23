package client.gui.seller;

import client.ControllerContainer;
import client.connectionController.interfaces.category.ICategoryController;
import client.connectionController.interfaces.product.IProductController;
import client.exception.*;
import client.gui.CategoryListController;
import client.gui.Constants;
import client.gui.PersonalInfoController;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.Reloadable;
import client.model.Category;
import client.model.*;
import client.model.enums.FeatureType;
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

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
    @FXML
    private Label errorLabel;
    private File imageFile;
    private PersonalInfoController personalInfoController;
    private Product newProductForAddingToSellers;

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
        saveButton.setText("Check");
        nameTextField.setPromptText("name");
        setEditable(false);
        errorLabel.setText("");
        imageChooserButton.setOnMouseClicked(e -> {
            try {
                setFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


    }

    public void setEditable(boolean type) {
        brandTextField.setEditable(type);
        descriptionField.setEditable(type);
        priceTextField.setEditable(type);
        amountTextField.setEditable(type);
        imageChooserButton.setVisible(type);
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
        categoryBox.getChildren().removeAll(categoryBox.getChildren());
        featuresBox.getChildren().removeAll(featuresBox.getChildren());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CategoryList.fxml"));
        Node node = loader.load();
        this.categoryListController = (CategoryListController) loader.getController();
        categoryListController.initialize(category.getId());
        categoryListController.setReloadable(this::reload);
        categoryBox.getChildren().removeAll(categoryBox.getChildren());
        categoryBox.getChildren().addAll(node);
        featuresBox.getChildren().removeAll(featuresBox.getChildren());
        featureBoxList = new ArrayList<>();
        category.getFeatures().forEach(i -> featureBoxList.add(new FeatureBox(i, i.getFeatureName(), i.getFeatureType(), null)));
        featureBoxList.forEach(i -> featuresBox.getChildren().add(i.getContainer()));
    }

    private void loadProduct(Product product) throws IOException {
        productImage.setImage(new Image(new ByteArrayInputStream(product.getImage())));
        nameTextField.setText(product.getName());
        brandTextField.setText(product.getBrand());
        descriptionField.setText(descriptionField.getText());
    }


    @FXML
    public void saveButtonClicked() throws IOException {
        if (saveButton.getText().equals("Check")) {
            try {
                this.newProductForAddingToSellers = productController.getProductByName(nameTextField.getText(), Constants.manager.getToken());
                Constants.manager.showErrorPopUp("product exist if you want to add click add or cancel to cancel");
                loadProduct(newProductForAddingToSellers);
                setEditable(false);
                nameTextField.setEditable(false);
                priceTextField.setEditable(true);
                amountTextField.setEditable(true);
                saveButton.setText("Add");
                categoryBox.getChildren().removeAll(categoryBox.getChildren());
                featuresBox.getChildren().removeAll(featuresBox.getChildren());
            } catch (NoObjectIdException e) {
                setEditable(true);
                nameTextField.setEditable(false);
                saveButton.setText("Save");
            }
        } else if (saveButton.getText().equals("Save")) {
            category = categoryListController.getCategory();
            if (isEveryThingOk()) {
                Product newProduct = new Product(nameTextField.getText(), brandTextField.getText(), descriptionField.getText());
                newProduct.setCategory(this.category);
                //newProduct.setImage(Files.readAllBytes(imageFile.toPath()));
                ProductSeller newProductSeller = new ProductSeller();
                featureBoxList.forEach(i -> newProduct.getCategoryFeatures().put(i.getCategoryFeature(), i.getValue()));
                newProductSeller.setPrice(Long.parseLong(priceTextField.getText()));
                newProductSeller.setRemainingItems(Integer.parseInt(amountTextField.getText()));
                newProduct.addSeller(newProductSeller);
                try {
                    productController.createProduct(newProduct, Constants.manager.getToken(), Files.readAllBytes(imageFile.toPath()));
                    this.personalInfoController.clearBox();
                    Constants.manager.showSuccessPopUp("Your Product Created");
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
        } else if (saveButton.getText().equals("Add")) {
            if (!Constants.manager.checkInputIsInt(amountTextField.getText())) {
                errorLabel.setText("invalid amount");
                return;
            }
            if (!Constants.manager.checkInputIsInt(priceTextField.getText())) {
                errorLabel.setText("invalid price");
                return;
            }
            ProductSeller newProductSeller = new ProductSeller();
            newProductSeller.setPrice(Long.parseLong(priceTextField.getText()));
            newProductSeller.setRemainingItems(Integer.parseInt(amountTextField.getText()));
            try {
                productController.addSeller(this.newProductForAddingToSellers.getId(), newProductSeller, Constants.manager.getToken());
                this.personalInfoController.clearBox();
                Constants.manager.showSuccessPopUp("you are added to product");
            } catch (NotSellerException | NoAccessException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
            }  catch (InvalidTokenException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
                Constants.manager.setTokenFromController();
            }
        }
    }

    private boolean isEveryThingOk() throws IOException {
        if (nameTextField.getText().isEmpty() || nameTextField.getText().isBlank()) {
            errorLabel.setText("empty name");
            return false;
        }
        if (priceTextField.getText().isEmpty() || priceTextField.getText().isBlank()) {
            errorLabel.setText("empty price");
            return false;
        }
        if (amountTextField.getText().isEmpty() || amountTextField.getText().isBlank()) {
            errorLabel.setText("empty amount");
            return false;
        }
        if (descriptionField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
            errorLabel.setText("empty description");
            return false;
        }
        if (brandTextField.getText().isEmpty() || brandTextField.getText().isEmpty()) {
            errorLabel.setText("empty brand");
            return false;
        }
        if (category == null) {
            errorLabel.setText("empty category");
            return false;
        }
        if (imageFile == null) {
            errorLabel.setText("empty image");
            return false;
        }
        if (!Constants.manager.checkInputIsInt(amountTextField.getText())) {
            errorLabel.setText("invalid amount");
            return false;
        }
        if (!Constants.manager.checkInputIsInt(priceTextField.getText())) {
            errorLabel.setText("invalid price");
            return false;
        }
        return true;
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

        public FeatureBox(CategoryFeature categoryFeature, String name, FeatureType featureType, String value) {
            this.categoryFeature = categoryFeature;
            this.name = new Label(name);
            this.type = featureType;
            description = new TextField("");
            description.setEditable(true);
            if (value != null) {
                description = new TextField(value);
                description.setEditable(false);
            }
            container = new HBox();
            description.setPromptText(FeatureType.getType(featureType));
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
