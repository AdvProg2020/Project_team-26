package view.gui.admin;

import controller.interfaces.category.ICategoryController;
import controller.product.CategoryController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.ObjectAlreadyExistException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Category;
import model.CategoryFeature;
import model.enums.FeatureType;
import view.cli.ControllerContainer;
import view.gui.CategoryListController;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.Reloadable;
import view.gui.seller.SingleProductForSellerPageController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryOptionController implements InitializableController, Reloadable {

    @FXML
    private TableView categoryTable;
    @FXML
    private TableColumn featureCol;
    @FXML
    private TableColumn typeCol;
    @FXML
    private TextField categoryFeatureName;
    @FXML
    private ChoiceBox typeChoice;
    @FXML
    private Button addButton;
    @FXML
    private TextField categoryName;
    @FXML
    private HBox hbox;
    @FXML
    private Button addCategory;
    @FXML
    private Label errorLabel;

    private CategoryListController controller;
    private ICategoryController categoryController;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        categoryController = (ICategoryController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CategoryController);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CategoryList.fxml"));
        hbox.getChildren().add(loader.load());
        controller = loader.getController();
        controller.initialize(1);
        controller.load(categoryController.getCategory(1, Constants.manager.getToken()));
        controller.setCategoryOptionController(this);
        addCategory.setOnMouseClicked(e -> addCategory());
        addButton.setOnMouseClicked(e -> addCategoryFeature());
        categoryTable.setOnMouseClicked(e -> fillDetails());
        List<FeatureType> types = new ArrayList<>();
        types.add(FeatureType.DOUBLE);
        types.add(FeatureType.STRING);
        types.add(FeatureType.INTEGER);
        typeChoice.setItems(FXCollections.observableList(types));
        controller.setReloadable(this::reload);
    }

    private void fillDetails() {
        CategoryFeature categoryFeature = (CategoryFeature) categoryTable.getSelectionModel().getSelectedItem();
        categoryFeatureName.setText(categoryFeature.getFeatureName());
        typeChoice.setValue(categoryFeature.getFeatureType());
    }

    private void addCategoryFeature() {
        String categoryFeatureName = this.categoryFeatureName.getText();
        FeatureType type = (FeatureType) typeChoice.getSelectionModel().getSelectedItem();
        if (categoryFeatureName.isBlank()) {
            errorLabel.setText("Please enter a name.");
        } else if (type == null) {
            errorLabel.setText("Please select a type.");
        } else {
            try {
                categoryController.addAttribute(controller.getCategory().getId(), categoryFeatureName, type, Constants.manager.getToken());
            } catch (InvalidIdException e) {
                errorLabel.setText(e.getMessage());
            } catch (NoAccessException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidTokenException e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    public void load() {

    }

    private void addCategory() {
        String name = categoryName.getText();
        if (name.isBlank()) {
            errorLabel.setText("Please enter a name.");
        } else {
            Category category = new Category();
            category.setName(name);
            try {
                categoryController.addCategory(controller.getCategory().getId(), category, Constants.manager.getToken());
                controller.load(categoryController.getCategory(1, Constants.manager.getToken()));
            } catch (InvalidIdException e) {
                errorLabel.setText(e.getMessage());
            } catch (ObjectAlreadyExistException e) {
                errorLabel.setText(e.getMessage());
            } catch (NoAccessException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidTokenException e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    public void setTable(Category category) {
        if (category == null)
            return;
        System.out.println(category + " " + "nigga");
        List<CategoryFeature> list = category.getFeatures();
        categoryTable.setItems(FXCollections.observableList(list));
        featureCol.setCellValueFactory(new PropertyValueFactory<CategoryFeature, String>("featureName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<CategoryFeature, String>("featureType"));
    }

    @Override
    public void reload() throws IOException {
      /*  if (controller.getCategory() == null)
            return;
        setTable(controller.getCategory());*/

    }
}
