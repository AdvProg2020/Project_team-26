package view.gui;

import controller.interfaces.category.ICategoryController;
import controller.product.CategoryController;
import exception.InvalidIdException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Category;
import view.cli.ControllerContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryListController implements InitializableController {
    ICategoryController categoryController;
    Reloadable reloadable;
    private Category category;
    @FXML
    private Label currentCategory;
    @FXML
    private ListView categoryList;


    @Override
    public void initialize(int id) throws IOException {
        categoryController = (CategoryController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CategoryController);
        try {
            category = categoryController.getCategory(id, Constants.manager.getToken());
            load(category);
            categoryList.setOnMouseClicked(e -> {
                try {
                    click();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (InvalidIdException invalidIdException) {
            invalidIdException.printStackTrace();
        }
    }

    public void load(Category category) throws InvalidIdException {
        currentCategory.setText(category.getName());
        List<Category> categories = new ArrayList<>();
        categories.addAll(categoryController.getSubCategories(category.getId(), Constants.manager.getToken()));
        ObservableList<Category> categoryObservableList = FXCollections.observableList(category.getSubCategory());
        categoryList.setItems(categoryObservableList);
    }

    public void goToParent() throws IOException {
        category = category.getParent();
        try {
            load(category);
            reloadable.reload();
        } catch (InvalidIdException invalidIdException) {
            invalidIdException.printStackTrace();
        }
    }

    private void click() throws IOException {
        if (categoryList.getItems() != null) {
            Category selectedCategory = (Category) categoryList.getSelectionModel().getSelectedItem();
            try {
                load(selectedCategory);
                category = selectedCategory;
            } catch (InvalidIdException invalidIdException) {
                invalidIdException.printStackTrace();
            }
            reloadable.reload();
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setReloadable(Reloadable reloadable) {
        this.reloadable = reloadable;
    }
}
