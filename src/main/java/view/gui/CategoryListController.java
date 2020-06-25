package view.gui;

import controller.interfaces.category.ICategoryController;
import controller.product.CategoryController;
import exception.InvalidIdException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Category;
import view.cli.ControllerContainer;
import view.gui.admin.CategoryOptionController;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.Reloadable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryListController implements InitializableController {
    ICategoryController categoryController;
    Reloadable reloadable;
    CategoryOptionController categoryOptionController;
    private Category category;
    @FXML
    private Label currentCategory;
    @FXML
    private ListView categoryList;
    @FXML
    private Button goToParentButton;


    @Override
    public void initialize(int id) throws IOException {
        categoryController = (CategoryController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CategoryController);
        try {
            category = categoryController.getCategory(id, Constants.manager.getToken());
            load(category);
            categoryList.setOnMouseClicked(e -> click());
        } catch (InvalidIdException invalidIdException) {
            invalidIdException.printStackTrace();
        }
    }

    public void load(Category category) throws InvalidIdException {
        if(category.getId() == 1)
            goToParentButton.setDisable(true);
        else
            goToParentButton.setDisable(false);
        List<Category> categories = new ArrayList<>();
        categories.addAll(categoryController.getSubCategories(category.getId(), Constants.manager.getToken()));
        categories.removeIf(c -> c.getId() == category.getId());
        ObservableList<Category> categoryObservableList = FXCollections.observableList(categories);
        categoryList.setItems(categoryObservableList);
        currentCategory.setText(category.getName());
    }

    public void goToParent() {
        try {
            load(category.getParent());
            category = category.getParent();
            reloadable.reload();
        } catch (InvalidIdException | IOException invalidIdException) {
            invalidIdException.printStackTrace();
        }
    }

    private void click() {
        if (categoryList.getItems() != null) {
            Category selectedCategory = (Category) categoryList.getSelectionModel().getSelectedItem();
            categoryOptionController.setTable(selectedCategory);
            try {
                if(selectedCategory == null)
                    return;
                load(selectedCategory);
                category = selectedCategory;
                reloadable.reload();
            } catch (InvalidIdException | IOException invalidIdException) {
                invalidIdException.printStackTrace();
            }

        }
    }

    public void setCategoryOptionController(CategoryOptionController categoryOptionController) {
        this.categoryOptionController = categoryOptionController;
    }

    public Category getCategory() {
        return category;
    }

    public void setReloadable(Reloadable reloadable) {
        this.reloadable = reloadable;
    }
}
