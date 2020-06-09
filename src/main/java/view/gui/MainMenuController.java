package view.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private HBox pageHBox;
    @FXML
    private VBox productsVBox;
    @FXML
    private VBox optionsVBox;
    @FXML
    private ScrollPane productsScrollPain;
    @FXML
    private GridPane productsGridPain;
    @FXML
    private VBox treeViewVBox;
    @FXML
    private Pane treeViewPane;
    @FXML
    private TreeView<String> categoryTreeView;
    @FXML
    private Pane filterAndOffPane;
    @FXML
    private ComboBox<String> productSortComboBox;
    @FXML
    private TextArea nameFilterTextArea;
    @FXML
    private TextField brandSearchTextField;
    @FXML
    private CheckBox offsCheckBox;
    @FXML
    private ComboBox<String> offSortCombowBox;
    @FXML
    private Label offSortLabel;
    @FXML
    private Label startOffsDateLabel;
    @FXML
    private Label endOffsDateLabel;
    @FXML
    private DatePicker startOffsDateDatePicker;
    @FXML
    private DatePicker endOffsDateDatePicker;
    @FXML
    private Label productLabel;
    @FXML
    private Label productSortLabel;
    @FXML
    private ColorPicker backgroundColorPicker;
    @FXML
    private Label chooseBackgroundLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("second");

    }
}
