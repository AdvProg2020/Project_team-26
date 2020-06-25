package view.gui.seller;

import controller.interfaces.order.IOrderController;
import controller.interfaces.product.IProductController;
import exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import model.enums.FeatureType;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingleProductForSellerPageController implements InitializableController {
    private int productId;
    private Product product;
    private ProductSeller productSeller;
    private IProductController productController;
    private IOrderController orderController;
    @FXML
    private ImageView productImage;
    @FXML
    private Text categoryText;
    @FXML
    private Slider productRateSlider;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField brandTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button productInfoEditButton;
    @FXML
    private Button productSellerInfoEditButton;
    @FXML
    private Label nameLabel;
    @FXML
    private TableView<Buyers> buyersTableView;
    @FXML
    private TableView<Feature> categoryFeatures;
    @FXML
    private TableColumn<String, Buyers> buyersTableViewColumns;
    @FXML
    private TableColumn<String, Feature> featureName;
    @FXML
    private TableColumn<String, Feature> featureType;
    @FXML
    private TableColumn<String, Feature> featureDescription;
    private File imageFile;

    @Override
    public void initialize(int id) throws IOException {
        productController = (IProductController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ProductController);
        orderController = (IOrderController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OrderController);
        productId = id;

    }

    public void load(Product product, ProductSeller productSeller) throws IOException {
        this.productSeller = productSeller;
        this.product = product;
        productImage.setImage(new Image(new ByteArrayInputStream(product.getImage())));
        categoryText.setText(product.getCategory().getName());
        productRateSlider.setValue(product.getAverageRate());
        productRateSlider.setValueChanging(false);
        setBuyersTableView();
        setFeaturesTable(product);
    }

    private void setFeaturesTable(Product product) {
        featureName.setCellValueFactory(new PropertyValueFactory<>("name"));
        featureType.setCellValueFactory(new PropertyValueFactory<>("featureType"));
        featureDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryFeatures.setEditable(false);
        ArrayList<Feature> features = new ArrayList<>();
        product.getCategoryFeatures().entrySet().forEach(i -> features.add(new Feature(i.getKey().getFeatureName(), i.getKey().getFeatureType(), i.getValue())));
        categoryFeatures.setItems(FXCollections.observableList(features));
    }

    private void initPrimitiveFields() throws IOException {
        productInfoEditButton.setText("Edit product");
        productSellerInfoEditButton.setText("Edit seller");
        nameTextField.setText(product.getName());
        brandTextField.setText(product.getBrand());
        amountTextField.setText("" + productSeller.getRemainingItems());
        descriptionTextArea.setText(product.getDescription());
        productImage.setImage(new Image(new ByteArrayInputStream(product.getImage())));
        setEditableForProduct(false);
        setEditableForProductSeller(false);

    }

    private void setEditableForProductSeller(boolean type) {
        amountTextField.setEditable(type);
        priceTextField.setEditable(type);
    }

    private void setEditableForProduct(boolean type) {
        nameTextField.setEditable(type);
        brandTextField.setEditable(type);
        descriptionTextArea.setEditable(type);
    }

    private void setBuyersTableView() throws IOException {
        List<Customer> users = null;
        try {
            users = orderController.getProductBuyerByProductId(product.getId(), Constants.manager.getToken());
            List<Buyers> buyersList = new ArrayList<>();
            users.forEach(i -> buyersList.add(new Buyers(i.getId(), i.getUsername(), i.getFullName() == null ? "" : i.getFullName())));
            ObservableList<Buyers> observableList = FXCollections.observableList(buyersList);
           /* ObservableList<TableColumn> cols = buyersTableView.getColumns();
            cols.get(0).setCellValueFactory(new PropertyValueFactory<User, String>("userName"));*/
            buyersTableViewColumns.setCellValueFactory(new PropertyValueFactory<>("userName"));
            buyersTableView.setItems(observableList);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        } catch (InvalidIdException | NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        }
    }


    @FXML
    public void productSellerInfoEditButtonClicked() throws IOException {
        if (productSellerInfoEditButton.getText().equals("Edit seller")) {
            setEditableForProductSeller(true);
            productSellerInfoEditButton.setText("Update seller");
        } else if (!priceTextField.getText().equals("") && !amountTextField.getText().equals("")
                && Constants.manager.checkIsLong(priceTextField.getText()) && Constants.manager.checkInputIsInt(amountTextField.getText())) {
            ProductSeller productSeller = this.productSeller.clone();
            productSeller.setPrice(Long.parseLong(priceTextField.getText()));
            productSeller.setRemainingItems(Integer.parseInt(amountTextField.getText()));
            try {
                productController.editProductSeller(product.getId(), productSeller, Constants.manager.getToken());
                productSellerInfoEditButton.setText("Edit seller");
                setEditableForProductSeller(false);
            } catch (InvalidIdException | NotSellerException | NoAccessException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
            } catch (InvalidTokenException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
                Constants.manager.setTokenFromController();
            }
        }
    }

    @FXML
    public void productInfoEditButtonClicked() throws IOException {
        if (productInfoEditButton.getText().equals("Edit product")) {
            setEditableForProduct(true);
            productInfoEditButton.setText("Update product");
        } else {
            Product product = productSeller.getProduct().clone();
            product.setDescription(descriptionTextArea.getText());
            product.setBrand(brandTextField.getText());
            product.setName(nameTextField.getText());
            product.setImage(Files.readAllBytes(imageFile.toPath()));
            setEditableForProduct(false);
            productInfoEditButton.setText("Edit product");
            try {
                productController.editProduct(product.getId(), product, Constants.manager.getToken());
            } catch (InvalidIdException | NoAccessException | NotSellerException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
            } catch (InvalidTokenException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
                Constants.manager.setTokenFromController();
            } finally {
                //todo reload changes
            }
        }
    }

    @FXML
    public void cancleButtonClicked() throws IOException {
        initPrimitiveFields();
    }


    @FXML
    public void uploadButtonClicked() throws IOException {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        this.imageFile = fileChooser.showOpenDialog(stage);
        if (imageFile == null) {
            Constants.manager.showErrorPopUp("you should choose photo");
        }
        productImage.setImage(new Image(new ByteArrayInputStream(Files.readAllBytes(imageFile.toPath()))));
    }

    private class Buyers {
        int id;
        String userName;
        String name;

        public Buyers(int id, String userName, String name) {
            this.id = id;
            this.userName = userName;
            this.name = name;
        }

        public String getUserName() {
            return userName;
        }
    }

    private class Feature {
        private String name;
        private String featureType;
        private String description;

        public Feature(String name, FeatureType featureType, String description) {
            this.name = name;
            this.featureType = FeatureType.getType(featureType);
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getFeatureType() {
            return featureType;
        }

        public String getDescription() {
            return description;
        }
    }

}
