package view.gui.product;

import controller.interfaces.category.ICategoryController;
import exception.InvalidIdException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Product;
import view.cli.ControllerContainer;
import view.gui.CategoryListController;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.Reloadable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllProductsController implements InitializableController, Reloadable {
    private ICategoryController categoryController;

    @FXML
    private TextField nameField;
    @FXML
    private TextField brandField;
    @FXML
    private TextField descriptionField;
    @FXML
    private CheckBox offCheckBox;
    @FXML
    private ComboBox<String> sortFieldComboBox;
    @FXML
    private ComboBox<String> sortDirectionComboBox;
    private GridPane mainGrid;
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private CategoryListController categoryListController;

    public void initialize(int id) throws IOException {
        categoryController = (ICategoryController) Constants.manager.getControllerContainer().
                getController(ControllerContainer.Controller.CategoryController);
        sortFieldComboBox.setPromptText("Choose Sorting");
        sortFieldComboBox.getItems().addAll("Name", "Average Rate", "Most Sold");
        sortDirectionComboBox.getItems().addAll("Ascending", "Descending");
        sortDirectionComboBox.setValue("Ascending");
        categoryListController.setReloadable(this);
        categoryListController.initialize(1);
        reload();
    }

    @Override
    public void reload() {
        boolean isAscending = true;
        if (sortDirectionComboBox.getValue().equals("Descending")) {
            isAscending = false;
        }
        int categoryId = categoryListController.getCategory().getId();
        try {
            List<Product> products;
            if (offCheckBox.isSelected()) {
                products = categoryController.getAllProductsInOff(extractFilter(),
                        extractSortField(), isAscending, 0, 0, categoryId, Constants.manager.getToken());
            } else {
                products = categoryController.getAllProducts(extractFilter(),
                        extractSortField(), isAscending, 0, 0, categoryId, Constants.manager.getToken());
            }
            updateGrid(products);
        } catch (InvalidIdException invalidIdException) {
            invalidIdException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void updateGrid(List<Product> products) throws IOException {
        mainGrid = new GridPane();
        mainScrollPane.setContent(mainGrid);
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int column = i % Constants.productGridColumnCount;
            int row = i / Constants.productGridColumnCount;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ProductCard.fxml"));
            Parent element = loader.load();

            ProductCardController productCardController = loader.getController();
            productCardController.load(product);

            mainGrid.setConstraints(element, column, row);
            mainGrid.getChildren().add(element);
        }
    }

    private Map<String, String> extractFilter() {
        Map<String, String> filter = new HashMap<>();
        if (nameField.getText() != null && !nameField.getText().equals("")) {
            filter.put("product name", nameField.getText());
        }
        if (brandField.getText() != null && !brandField.getText().equals("")) {
            filter.put("brand", brandField.getText());
        }
        if (descriptionField.getText() != null && !descriptionField.getText().equals("")) {
            filter.put("description", descriptionField.getText());
        }
        // TODO: add price to filter
        // TODO: add rate to filter
        return filter;
    }

    private String extractSortField() {
        if(sortFieldComboBox.getValue() == null)
            return null;
        switch (sortFieldComboBox.getValue()) {
            case "Name":
                return "name";
            case "Average Rate":
                return "averageRate";
            case "Most Sold":
                return null;
        }
        return null;
    }
}
