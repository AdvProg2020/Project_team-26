package view.gui.seller;

import controller.interfaces.discount.IOffController;
import controller.interfaces.product.IProductController;
import exception.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.*;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.Reloadable;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;

public class OffControllerPage implements InitializableController, Reloadable {
    private Off off;
    private int offId;
    private IOffController offController;
    private IProductController productController;
    private OffTableController offTableController;

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addProductButton;
    @FXML
    private TextField productName;
    @FXML
    private TextField priceInOff;
    @FXML
    private VBox offVBox;
    @FXML
    private VBox allBox;

    @Override
    public void initialize(int id) throws IOException {
        offController = (IOffController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OffController);
        productController = (IProductController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ProductController);
        this.offId = id;
        updateButton.setText("Edit");
        addProductButton.setText("Add Product");
    }

    public void loadOffPage(Off off, OffTableController offTableController) throws IOException {
        this.offTableController = offTableController;
       /* startDate.setValue(LocalDate.from(off.getStartDate().toInstant()));
        endDate.setValue(LocalDate.from(off.getEndDate().toInstant()));*/
        setEditable(false);
        loadOffItems(off);
        deleteButton.setOnMouseClicked(e -> {
            try {
                offTableController.deleteOffAndReloadBox(offId);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setEditable(boolean type) {
        startDate.setEditable(type);
        endDate.setEditable(type);
    }

    public void loadOffItems(Off off) throws IOException {
        offVBox.getChildren().removeAll();
        for (OffItem item : off.getItems()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/OffItemPage.fxml"));
            Node node = loader.load();
            OffItemPageController offItemPageController = (OffItemPageController) loader.getController();
            offItemPageController.load(item, off, false, this);
            offItemPageController.initialize(item.getId());
            offVBox.getChildren().add(node);
        }
    }

    @FXML
    public void updateButtonClicked() throws IOException {
        if (updateButton.getText().equals("Edit")) {
            setEditable(true);
            updateButton.setText("Update");
        } else {
            try {
                if (off == null)
                    return;
                Off newOff = off.clone();
                newOff.setStartDate(Constants.manager.getDateFromDatePicker(startDate));
                newOff.setEndDate(Constants.manager.getDateFromDatePicker(endDate));
                offController.edit(newOff, offId, Constants.manager.getToken());
                Constants.manager.showSuccessPopUp("off updated");
                setEditable(false);
                updateButton.setText("Edit");
                reloadPage();
            } catch (NoAccessException | InvalidIdException | DateTimeException | IllegalArgumentException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
            } catch (InvalidTokenException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
                Constants.manager.setTokenFromController();
            } catch (NotLoggedINException e) {
                Constants.manager.showLoginMenu();
            }
        }
    }


    public void reloadPage() throws IOException {
        try {
            off = offController.getOff(offId, Constants.manager.getToken());
            loadOffPage(off, this.offTableController);
        } catch (InvalidIdException e) {
            Constants.manager.showErrorPopUp("the off just deleted by manager");
        }


    }

    @FXML
    public void addProductButtonclicked() throws IOException {
        if (productName.getText().equals("") || priceInOff.getText().equals("")) {
            Constants.manager.showErrorPopUp("dont leave the fields empty");
            return;
        }
        try {
            Product product = productController.getProductByName(productName.getText(), Constants.manager.getToken());
            if (Constants.manager.checkIsPercent(priceInOff.getText())) {
                offController.addProductToOff(off, product.getId(), -1, Integer.parseInt(priceInOff.getText().split("%")[0]), false, Constants.manager.getToken());
                Constants.manager.showSuccessPopUp("product added");
                reload();
            } else if (Constants.manager.checkIsLong(priceInOff.getText())) {
                offController.addProductToOff(off, product.getId(), Long.parseLong(priceInOff.getText()), 0, false, Constants.manager.getToken());
                Constants.manager.showSuccessPopUp("product added");
                reload();
            } else {
                Constants.manager.showErrorPopUp("enter Integer");
                return;
            }
        } catch (NoObjectIdException | InvalidIdException | NoAccessException | ObjectAlreadyExistException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        } finally {
            priceInOff.setText("");
            productName.setText("");

        }
    }

    @Override
    public void reload() throws IOException {
        try {
            off = offController.getOff(offId, Constants.manager.getToken());
            reloadPage();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteThisOff() throws IOException {
        try {
            offController.removeAOff(this.offId, Constants.manager.getToken());
            allBox.getChildren().removeAll(allBox.getChildren());
        } catch (NoAccessException | InvalidIdException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        }

    }
}
