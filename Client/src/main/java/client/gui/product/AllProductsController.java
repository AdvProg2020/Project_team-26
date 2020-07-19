package client.gui.product;

import client.ControllerContainer;
import client.connectionController.interfaces.category.ICategoryController;
import client.exception.*;
import client.gui.CategoryListController;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.Reloadable;
import client.model.Product;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private CategoryListController categoryListController;
    @FXML
    private Slider rateSlider;
    @FXML
    private VBox price;
    @FXML
    private Slider maxPrice;
    @FXML
    private Slider minPrice;

    private GridPane mainGrid;

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

        rateSlider.valueProperty().addListener((observable) -> reload());
        minPrice.valueProperty().addListener((observable) -> reload());
        maxPrice.valueProperty().addListener((observable) -> reload());
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
        if(rateSlider.getValue() > 0) {
            filter.put("rate", rateSlider.getValue() + "-10");
        }
        if(minPrice.getValue() != minPrice.getMin() && maxPrice.getValue() != maxPrice.getMax()) {
            filter.put("price", makePrice());
        }
        return filter;
    }

    private String makePrice() {
        return (long) minPrice.getValue() + "-" + (long) maxPrice.getValue();
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
