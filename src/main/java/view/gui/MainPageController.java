package view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MainPageController {
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
        sortFieldComBowBox.setPromptText("chooseSorting");
        sortFieldComBowBox.getItems().addAll("default", "average Rate", "most sold");
        isAcendingComBowBox.getItems().addAll("default", "ascending", "depending");
        isAcendingComBowBox.setPromptText("ascending/depending");
    }
}
