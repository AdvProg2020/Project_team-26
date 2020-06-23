package view.gui.seller;

import controller.interfaces.order.IOrderController;
import controller.interfaces.product.IProductController;
import exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import model.*;
import view.gui.Constants;
import view.gui.InitializableController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SingleProductForSellerPageController implements InitializableController {
    private int productId;
    private Product product;
    private ProductSeller productSeller;
    IProductController productController;
    IOrderController orderController;
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
    TableView<Buyers> buyersTableView;
    @FXML
    TableColumn<String, Buyers> buyersTableViewColumns;//todo


    @Override

    public void initialize(int id) throws IOException {
        productId = id;

    }

    public void load(Product product, ProductSeller productSeller) {
        this.productSeller = productSeller;
        this.product = product;
        // productImage //todo
        categoryText.setText(product.getCategory().getName());
        productRateSlider.setValue(product.getAverageRate());
        productRateSlider.setValueChanging(false);
        setBuyersTableView();
    }

    private void initPrimitiveFields() {
        productInfoEditButton.setText("Edit product");
        productSellerInfoEditButton.setText("Edit seller");
        nameTextField.setText(product.getName());
        brandTextField.setText(product.getBrand());
        amountTextField.setText("" + productSeller.getRemainingItems());
        descriptionTextArea.setText(product.getDescription());
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

    private void setBuyersTableView() {
        List<Customer> users = null;
        try {
            users = orderController.getProductBuyerByProductId(product.getId(), Constants.manager.getToken());
            List<Buyers> buyersList = new ArrayList<>();
            users.forEach(i -> buyersList.add(new Buyers(i.getId(), i.getUsername(), i.getFullName() == null ? "" : i.getFullName())));
            ObservableList<Buyers> observableList = FXCollections.observableList(buyersList);
            TableColumn buyers = new TableColumn("Buyers");
            buyers.setCellValueFactory(new PropertyValueFactory<>("userName"));
            buyersTableView.getColumns().add(buyers);
            buyersTableView.setItems(observableList);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void productSellerInfoEditButtonClicked() {
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
            } catch (InvalidIdException e) {
                e.printStackTrace();
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            } catch (NotSellerException e) {
                e.printStackTrace();
            } catch (NoAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void productInfoEditButtonClicked() {
        if (productInfoEditButton.getText().equals("Edit product")) {
            setEditableForProduct(true);
            productInfoEditButton.setText("Update product");
        } else {
            Product product = productSeller.getProduct().clone();
            product.setDescription(descriptionTextArea.getText());
            product.setBrand(brandTextField.getText());
            product.setName(nameTextField.getText());
            setEditableForProduct(false);
            productInfoEditButton.setText("Edit product");
            try {
                productController.editProduct(product.getId(), product, Constants.manager.getToken());
            } catch (InvalidIdException e) {
                e.printStackTrace();
            } catch (NotSellerException e) {
                e.printStackTrace();
            } catch (NoAccessException e) {
                e.printStackTrace();
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            } finally {
                //todo reload changes
            }
        }
    }

    @FXML
    public void cancleButtonClicked() {
        initPrimitiveFields();
    }


    @FXML
    public void uploadButtonClicked() {
        //todo set image
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
}
