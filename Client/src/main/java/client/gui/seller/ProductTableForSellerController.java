package client.gui.seller;

import client.ControllerContainer;
import client.connectionController.interfaces.product.IProductController;
import client.exception.*;
import client.gui.Constants;
import client.gui.PersonalInfoController;
import client.gui.interfaces.InitializableController;
import client.model.*;
import client.model.ProductSeller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        productController = (IProductController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ProductController);
        sortComBowBox.getItems().addAll("mostSold", "price");
        productForTables = new ArrayList<>();
        searchTextField.setPromptText("Name");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadSingleProduct(ProductForTable productForTable) throws IOException {
        Product product = null;
        try {
            product = productController.getProductById(productForTable.getId(), Constants.manager.getToken());
            ProductSeller productSeller = productController.getProductSellerByIdAndSellerId(product.getId(), Constants.manager.getToken());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/SingleProductForSellerPage.fxml"));
            Node node = loader.load();
            SingleProductForSellerPageController singleProductForSellerPageController = (SingleProductForSellerPageController) loader.getController();
            singleProductForSellerPageController.initialize(productSeller.getId());
            singleProductForSellerPageController.load(product, productSeller);
            personalInfoController.updateAllBoxWithSingleNode(node);
        } catch (InvalidIdException | NoObjectIdException | NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        }
    }

    public void load(List<Product> productList, PersonalInfoController personalInfoController) {
        this.personalInfoController = personalInfoController;
        productList.forEach(i -> productForTables.add(new ProductForTable(i.getId(), i.getCategory().getId(), i.getName()
                , i.getCategory().getName(), i.getMinimumPrice(), i.getBrand())));
        tableView.getItems().removeAll(tableView.getItems());
        tableView.setItems(FXCollections.observableList(productForTables));
        tableView.setEditable(false);
        tableView.setOnMouseClicked(e -> {
            ObservableList<ProductForTable> productForTableObservableList = tableView.getSelectionModel().getSelectedItems();
            try {
                if (tableView.getSelectionModel().getSelectedItems() != null)
                    loadSingleProduct(productForTableObservableList.get(productForTableObservableList.size() - 1));
            } catch (IOException ex) {
                //TODO end program
            }
        });
    }

    @FXML
    public void searchIconClicked() throws IOException {
        HashMap<String, String> filter = new HashMap<>();
        filter.put("name", searchTextField.getText());
        try {
            List<Product> productList = productController.getAllProductWithFilterForSellerId(filter, "price", true, 0, 50, Constants
                    .manager.getToken());
            load(productList, this.personalInfoController);
        } catch (NotLoggedINException | NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        }
    }

    public class ProductForTable {
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
