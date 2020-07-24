package client.gui.seller;

import client.ControllerContainer;
import client.connectionController.interfaces.auction.IAuctionController;
import client.connectionController.interfaces.order.IOrderController;
import client.connectionController.interfaces.product.IProductController;
import client.exception.*;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.Reloadable;
import client.model.Product;
import client.model.ProductSeller;
import client.model.User;
import client.model.enums.FeatureType;
import client.model.enums.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SingleProductForSellerPageController implements InitializableController, Reloadable {
    private int productId;
    private Product product;
    private ProductSeller productSeller;
    private IProductController productController;
    private IOrderController orderController;
    private IAuctionController auctionController;
    @FXML
    private Text stateText;
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
    private Button uploadPhoto;
    @FXML
    private TableColumn<String, Feature> featureDescription;
    @FXML
    private Button deleteButton;
    @FXML
    private VBox totalBox;
    @FXML
    private Button deleteSeller;

    private File imageFile;

    @Override
    public void initialize(int id) throws IOException {
        productController = (IProductController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ProductController);
        orderController = (IOrderController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OrderController);
        auctionController = (IAuctionController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.AuctionController);
        productId = id;
        productSellerInfoEditButton.setText("Edit seller");
        productInfoEditButton.setText("Edit product");
        uploadPhoto.setVisible(false);
        deleteButton.setOnAction(e -> {
            try {
                deleteProduct();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        deleteSeller.setOnAction(e -> {
            try {
                deleteSeller();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void deleteSeller() throws IOException {
        try {
            productController.removeSeller(this.product.getId(), this.productSeller.getId(), Constants.manager.getToken());
        } catch (InvalidIdException | NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.showLoginMenu();
        }
    }

    private void deleteProduct() throws IOException {
        try {
            productController.removeProduct(productId, Constants.manager.getToken());
            totalBox.getChildren().removeAll(totalBox.getChildren());
        } catch (InvalidIdException | NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.showLoginMenu();
        }

    }

    public void load(Product product, ProductSeller productSeller) throws IOException {
        this.productSeller = productSeller;
        this.product = product;
        nameTextField.setText(product.getName());
        brandTextField.setText(product.getBrand());
        descriptionTextArea.setText(product.getDescription());
        productImage.setImage(new Image(new ByteArrayInputStream(product.getImage())));
        categoryText.setText(product.getCategory().getName());
        productRateSlider.setValue(product.getAverageRate() == null ? 0.0 : product.getAverageRate());
        productRateSlider.setValueChanging(false);
        productRateSlider.setDisable(false);
        amountTextField.setText("" + productSeller.getRemainingItems());
        priceTextField.setText("" + productSeller.getPrice());
        stateText.setText(product.getStatus() == Status.DEACTIVE ? "DEACTIVE" : "ACTIVE");
        setEditableForProductSeller(false);
        setEditableForProduct(false);
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
        amountTextField.setText("" + productSeller.getRemainingItems());
        priceTextField.setText("" + productSeller.getPrice());
        setEditableForProductSeller(false);
        setEditableForProduct(false);

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
        List<User> users = null;
        try {
            users = orderController.getProductBuyerByProductId(product.getId(), Constants.manager.getToken());
            List<Buyers> buyersList = new ArrayList<>();
            users.forEach(i -> buyersList.add(new Buyers(i.getId(), i.getUsername(), i.getFullName() == null ? "" : i.getFullName())));
            ObservableList<Buyers> observableList = FXCollections.observableList(buyersList);
            buyersTableViewColumns.setCellValueFactory(new PropertyValueFactory<>("userName"));
            buyersTableView.setItems(observableList);
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (NotLoggedINException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.showLoginMenu();
        } catch (InvalidIdException | NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        }
    }

    @FXML
    public void setProductOnAuction() {
        Stage stage = new Stage();
        DatePicker datePicker = new DatePicker();
        datePicker.setOnAction(e -> {
            try {
                try {
                    auctionController.createNewAuction(this.productSeller.getId(),
                            Constants.manager.getDateFromDatePicker(datePicker), Constants.manager.getToken());
                    stage.close();
                } catch (ObjectAlreadyExistException | NotSellerException ex) {
                    Constants.manager.showErrorPopUp(ex.getMessage());
                } catch (NotLoggedINException ex) {
                    Constants.manager.showLoginMenu();
                } catch (InvalidTokenException ex) {
                    Constants.manager.showErrorPopUp(ex.getMessage());
                    Constants.manager.setTokenFromController();
                }finally {
                    stage.close();
                }
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        });
        Label command = new Label("pickDateForEnd");
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(command, datePicker);
        Scene scene = new Scene(vBox, 300, 240);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void productSellerInfoEditButtonClicked() throws IOException {
        if (productSellerInfoEditButton.getText().equals("Edit seller")) {
            setEditableForProductSeller(true);
            productSellerInfoEditButton.setText("Update seller");
        } else if (!priceTextField.getText().equals("") && !amountTextField.getText().equals("")
                && Constants.manager.checkIsLong(priceTextField.getText()) && Constants.manager.checkInputIsInt(amountTextField.getText())) {
            ProductSeller newProductSeller = this.productSeller.clone();
            newProductSeller.setId(this.productSeller.getId());
            newProductSeller.setPrice(Long.parseLong(priceTextField.getText()));
            newProductSeller.setRemainingItems(Integer.parseInt(amountTextField.getText()));
            try {
                productController.editProductSeller(productSeller.getId(), newProductSeller, Constants.manager.getToken());
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
            Product product = productController.getProductByProductSellerId(productSeller.getId()).clone();
            product.setName(nameTextField.getText());
            product.setDescription(descriptionTextArea.getText());
            product.setBrand(brandTextField.getText());
            if (imageFile != null)
                product.setImage(Files.readAllBytes(imageFile.toPath()));
            try {
                productController.editProduct(product.getId(), product, Constants.manager.getToken());
                setEditableForProduct(false);
                productInfoEditButton.setText("Edit product");
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
        } else
            productImage.setImage(new Image(new ByteArrayInputStream(Files.readAllBytes(imageFile.toPath()))));
    }

    @Override
    public void reload() throws IOException {

    }

    public class Buyers {
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

    public class Feature {
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
