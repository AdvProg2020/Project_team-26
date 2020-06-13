package view.gui;

import controller.interfaces.discount.IOffController;
import controller.interfaces.product.IProductController;
import exception.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.*;
import view.Main;
import view.cli.ControllerContainer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.time.LocalDate;

public class OffControllerPage implements InitializableController {
    private Off off;
    private int offId;
    private IOffController offController;
    private IProductController productController;

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Button updateButton;
    @FXML
    private Button addProductButton;
    @FXML
    private TextField productName;
    @FXML
    private TextField priceInOff;
    @FXML
    private VBox offVBox;

    @Override
    public void initialize(int id) throws IOException {
        offController = (IOffController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OffController);
        this.offId = offId;
        updateButton.setText("Edit");
        addProductButton.setText("Add Product");
    }

    public void loadOffPage(Off off) throws IOException {
        startDate.setValue(LocalDate.from(off.getStartDate().toInstant()));
        endDate.setValue(LocalDate.from(off.getEndDate().toInstant()));
        setEditable(false);
        loadOffItems(off);
    }

    private void setEditable(boolean type) {
        startDate.setEditable(type);
        endDate.setEditable(type);
    }

    public void loadOffItems(Off off) throws IOException {
        offVBox.getChildren().removeAll();
        for (OffItem item : off.getItems()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/OffItemPage.fxml"));
            OffItemPageController offItemPageController = (OffItemPageController) loader.getController();
            offItemPageController.load(item, off);
            offItemPageController.initialize(item.getId());
            offVBox.getChildren().add(loader.load());
        }
    }

    @FXML
    public void updateButtonClicked() {
        if (updateButton.getText().equals("Edit")) {
            setEditable(true);
            updateButton.setText("Update");
        } else {
            try {
                Off newOff = off.clone();
                newOff.setStartDate(Constants.manager.getDateFromDatePicker(startDate));
                newOff.setEndDate(Constants.manager.getDateFromDatePicker(endDate));
                offController.edit(newOff, offId, Constants.manager.getToken());
                setEditable(false);
                updateButton.setText("Edit");
                //todo reload offs;
            } catch (NoAccessException e) {
                e.printStackTrace();
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            } catch (InvalidIdException e) {
                e.printStackTrace();
            } catch (NotLoggedINException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void addProductButtonclicked() {
        if (productName.getText().equals("") || priceInOff.getText().equals("")) {
            //todo red the field
            return;
        }
        try {
            Product product = productController.getProductByName(productName.getText(), Constants.manager.getToken());
            if (Constants.manager.checkIsPercent(priceInOff.getText())) {
                offController.addProductToOff(off, product.getId(), -1, Integer.parseInt(priceInOff.getText().split("%")[0]), Constants.manager.getToken());
            } else if (Constants.manager.checkIsLong(priceInOff.getText())) {
                offController.addProductToOff(off, product.getId(), Long.parseLong(priceInOff.getText()), 0, Constants.manager.getToken());
            } else {
                //todo red the box
                return;
            }
        } catch (NoObjectIdException e) {
            e.printStackTrace();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (ObjectAlreadyExistException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }
        //todo reload page


    }
}
