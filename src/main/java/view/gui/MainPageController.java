package view.gui;

import controller.interfaces.category.ICategoryController;
import exception.InvalidIdException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Category;
import view.terminal.ViewManager;

public class MainPageController {
    private ICategoryController categoryController;
    private ViewManager manager;
    @FXML
    private TextField searchTextFieldForName;
    @FXML
    private ImageView searchIconImage;
    @FXML
    private CheckBox offCheckBox;
    @FXML
    private TreeView<String> categoriesTreeView;
    @FXML
    private ColorPicker chooseBackgroundColorPicker;
    @FXML
    private ComboBox<String> sortFieldComBowBox;
    @FXML
    private ComboBox<String> isAcendingComBowBox;
    @FXML
    private TextField brandTextField;
    @FXML
    private VBox priceVBoxForSlider;

    @FXML
    public void initialize() {
        initCheckBoxs();
        initComBowBoxs();
        buildTreeViewOfCategories();


    }


    private void initComBowBoxs() {
        sortFieldComBowBox.setPromptText("chooseSorting");
        sortFieldComBowBox.getItems().addAll("default", "average Rate", "most sold");
        isAcendingComBowBox.getItems().addAll("ascending", "depending");
        isAcendingComBowBox.setPromptText("ascending/descending");
    }

    private void initCheckBoxs() {
        offCheckBox.setSelected(false);
        offCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (offCheckBox.isSelected()) {
                    sortFieldComBowBox.getItems().addAll("start date", "end date");
                    //loadProducts();
                } else {
                    sortFieldComBowBox.getItems().removeAll("start date", "end date");
                    //loadProducts();
                }
            }
        });
    }

    @FXML
    public void searchIconClicked() {
        //todo
    }

    private void loadProducts() {
        //todo

    }

    private void buildTreeViewOfCategories() {

        TreeItem<String> root = new TreeItem<>("default");
        TreeItem<String> t1 = new TreeItem<>("hi");
        t1.setExpanded(false);

        TreeItem<String> t2 = new TreeItem<>("dfg");
        t2.setExpanded(false);

        TreeItem<String> t3 = new TreeItem<>("hidsf");
        TreeItem<String> t4 = new TreeItem<>("hidsadsf");
        TreeItem<String> t5 = new TreeItem<>("dsfshi");
        t1.getChildren().addAll(t4, t5);
        t5.setExpanded(false);
        t4.setExpanded(false);
        root.getChildren().addAll(t1, t2);
        t3.setExpanded(false);
        t2.getChildren().addAll(t3);
        categoriesTreeView.setRoot(root);
        categoriesTreeView.setShowRoot(false);


       /* try {
            Category category = categoryController.getCategory(1, manager.getToken());
            category.getSubCategory().forEach(i -> loadCategoriesToTree(i, root));

        } catch (InvalidIdException e) {
            e.printStackTrace();//todo
        }*/
    }

    private void loadCategoriesToTree(Category category, TreeItem<String> root) {
        TreeItem<String> leave = new TreeItem<>(category.getName());
        root.getChildren().addAll(leave);
        if (category.getSubCategory() == null || category.getSubCategory().size() == 0) {
            leave.setExpanded(false);
            return;
        }
        leave.setExpanded(true);
        for (Category category1 : category.getSubCategory()) {
            loadCategoriesToTree(category1, leave);
        }
    }
}
