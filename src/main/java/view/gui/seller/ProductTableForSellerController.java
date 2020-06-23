package view.gui.seller;

import controller.interfaces.product.*;
import exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.*;
import view.gui.*;

import java.io.IOException;
import java.util.*;

public class ProductTableForSellerController implements InitializableController {
    private PersonalInfoController personalInfoController;
    private IProductController productController;
    private ArrayList<ProductForTable> productForTables;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<String, ProductForTable> productName;
    @FXML
    private TableColumn<String, ProductForTable> categoryName;
    @FXML
    private TableColumn<String, ProductForTable> brand;
    @FXML
    private TableColumn<Long, ProductForTable> price;
    @FXML
    private ComboBox<String> sortComBowBox;
    @FXML
    private TextField searchTextField;
    @FXML
    private ImageView searchIconImage;


    @Override
    public void initialize(int id) throws IOException {
        sortComBowBox.getItems().addAll("mostSold", "price");
        searchTextField.setPromptText("Name");
    }

    private void loadSingleProduct(ProductForTable productForTable) throws IOException {
        Product product = null;
        try {
            product = productController.getProductById(productForTable.getId(), Constants.manager.getToken());
            ProductSeller productSeller = productController.getProductSellerByIdAndSellerId(product.getId(), Constants.manager.getToken());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/SingleProductForSellerPage.fxml"));
            SingleProductForSellerPageController singleProductForSellerPageController = (SingleProductForSellerPageController) loader.getController();
            singleProductForSellerPageController.initialize(productSeller.getId());
            singleProductForSellerPageController.load(product, productSeller);
            personalInfoController.updateAllBox(loader.load());
        } catch (InvalidIdException e) {
            e.printStackTrace();
        } catch (NoObjectIdException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }
    }

    public void load(List<Product> productList, PersonalInfoController personalInfoController) {
        this.personalInfoController = personalInfoController;
        productList.forEach(i -> productForTables.add(new ProductForTable(i.getId(), i.getCategory().getId(), i.getName()
                , i.getCategory().getName(), i.getMinimumPrice(), i.getBrand())));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.setItems(FXCollections.observableList(productForTables));
        tableView.setEditable(false);
        tableView.setOnMouseClicked(e -> {
            ObservableList<ProductForTable> productForTableObservableList = tableView.getSelectionModel().getSelectedItems();
            try {
                loadSingleProduct(productForTableObservableList.get(productForTableObservableList.size() - 1));
            } catch (IOException ex) {
                //todo
            }
        });
    }

    private class ProductForTable {
        private int id;
        private int categoryId;
        private String name;
        private String categoryName;
        private Long price;
        private String brand;

        public ProductForTable(int id, int categoryId, String name, String categoryName, Long price, String brand) {
            this.id = id;
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.name = name;
            this.price = price;
            this.brand = brand;
        }

        public int getId() {
            return id;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public String getName() {
            return name;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public Long getPrice() {
            return price;
        }

        public String getBrand() {
            return brand;
        }
    }
}
