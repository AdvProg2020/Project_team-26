package view.gui.product;

import controller.interfaces.category.ICategoryController;
import exception.InvalidIdException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import model.Product;
import org.controlsfx.control.RangeSlider;
import view.cli.ControllerContainer;
import view.gui.CategoryListController;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.Reloadable;

import java.io.IOException;
import java.util.ArrayList;
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
    @FXML
    private Slider rate;
    @FXML
    private VBox price;
    @FXML
    private Slider minRate;
    @FXML
    private Slider maxRate;
    @FXML
    private Slider maxPrice;
    @FXML
    private Slider minPrice;


    public void initialize(int id) throws IOException {
        categoryController = (ICategoryController) Constants.manager.getControllerContainer().
                getController(ControllerContainer.Controller.CategoryController);
        sortFieldComboBox.setPromptText("Choose Sorting");
        sortFieldComboBox.getItems().addAll("Name", "Price", "Average Rate", "Most Sold");
        sortDirectionComboBox.getItems().addAll("Ascending", "Descending");
        sortDirectionComboBox.setValue("Ascending");
        categoryListController.setReloadable(this);
        categoryListController.initialize(1);
        reload();
    }

    @Override
    public void reload() {
        Task<List<Product>> task = new Task<>() {
            @Override
            protected List<Product> call() throws Exception {
                boolean isAscending = true;
                if (sortDirectionComboBox.getValue().equals("Descending")) {
                    isAscending = false;
                }
                int categoryId = categoryListController.getCategory().getId();
                try {
                    if (offCheckBox.isSelected()) {
                        return categoryController.getAllProductsInOff(extractFilter(),
                                extractSortField(), isAscending, 0, 0, categoryId, Constants.manager.getToken());
                    } else {
                        return categoryController.getAllProducts(extractFilter(),
                                extractSortField(), isAscending, 0, 0, categoryId, Constants.manager.getToken());
                    }
                } catch (InvalidIdException invalidIdException) {
                    invalidIdException.printStackTrace();
                    return new ArrayList<>();
                }
            }
        };
        task.setOnSucceeded(workerStateEvent -> {
            try {
                updateGrid(task.getValue());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        new Thread(task).start();
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
        filter.put("rate", makeRate());
        filter.put("price", makePrice());
        return filter;
    }

    private String makeRate() {
        if (minRate.getValue() > maxRate.getValue()) {
            return maxRate.getValue() + "-" + minRate.getValue();
        }
        return minRate.getValue() + "-" + maxRate.getValue();
    }

    private String makePrice() {
        if (minPrice.getValue() > maxPrice.getValue()) {
            return maxPrice.getValue() + "-" + minPrice.getValue();
        }
        return minPrice.getValue() + "-" + maxPrice.getValue();
    }

    private String extractSortField() {
        if (sortFieldComboBox.getValue() == null)
            return null;
        switch (sortFieldComboBox.getValue()) {
            case "Name":
                return "name";
            case "Price":
                return "price";
            case "Average Rate":
                return "averageRate";
            case "Most Sold":
                return "amountBought";
        }
        return null;
    }
}
