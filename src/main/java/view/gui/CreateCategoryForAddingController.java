package view.gui;

import controller.interfaces.cart.ICartController;
import controller.interfaces.category.ICategoryController;
import controller.product.CategoryController;
import exception.InvalidIdException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Category;
import view.cli.ControllerContainer;

import java.io.IOException;
import java.util.ArrayList;

public class CreateCategoryForAddingController implements InitializableController {
    ICategoryController categoryController;
    ParentPageController parentPageController;
    private int categoryId;
    @FXML
    private ListView categoryList;


    @Override
    public void initialize(int id) throws IOException {
        categoryController = (CategoryController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CategoryController);
        categoryId = id;
        categoryList.setOnMouseClicked(e -> {
            if (categoryList.getItems() != null) {
                Category selectedCategory = (Category) categoryList.getSelectionModel().getSelectedItem();
                try {
                    Category newCategory = categoryController.getCategory(selectedCategory.getId(), Constants.manager.getToken());
                    parentPageController.reloadItems(newCategory);
                } catch (InvalidIdException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void load(Category category, ParentPageController parentPageController) {
        ArrayList<Category> categories = new ArrayList<>();
        this.parentPageController = parentPageController;
        categories.add(category);
        this.categoryId = category.getId();
        categories.addAll(category.getSubCategory());
        ObservableList<Category> categoryObservableList = FXCollections.observableList(category.getSubCategory());
        categoryList.setItems(categoryObservableList);
    }

    public Category getCategory() {
        return (Category) categoryList.getSelectionModel().getSelectedItem();
    }
}
