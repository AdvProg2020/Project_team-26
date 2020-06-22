package view.gui.seller;

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
import view.gui.Constants;
import view.gui.InitializableController;

import java.io.IOException;

public class CreateOfForSellerController implements InitializableController, OffControllerForFxml {
    private IOffController offController;
    private IProductController productController;
    private boolean isOk;
    private Off off;
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
    private VBox itemsVBox;

    @Override
    public void initialize(int id) throws IOException {
        startDate.setPromptText("start date");
        endDate.setPromptText("end date");
        updateButton.setText("Submit off");
        addProductButton.setText("Add Product");
        productName.setText("enter name");
        priceInOff.setPromptText("enter [--%] or [d]");
        itemsVBox.getChildren().removeAll();
        this.off = new Off();
    }

    @FXML
    public void addProductButtonclicked() {
        try {
            Product product = productController.getProductByName(productName.getText(), Constants.manager.getToken());
            if (Constants.manager.checkIsPercent(priceInOff.getText())) {
                offController.addProductToOff(off, product.getId(), -1, Integer.parseInt(priceInOff.getText().split("%")[0]), true, Constants.manager.getToken());
            } else if (Constants.manager.checkIsLong(priceInOff.getText())) {
                offController.addProductToOff(off, product.getId(), Long.parseLong(priceInOff.getText()), 0, true, Constants.manager.getToken());
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

    private void updateVBox(OffItem item) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/OffItemPage.fxml"));
            OffItemPageController offItemPageController = (OffItemPageController) loader.getController();
            offItemPageController.load(item, off, true,this);
            offItemPageController.initialize(item.getId());
            itemsVBox.getChildren().add(loader.load());
    }

    @FXML
    public void updateButtonClicked() {
        try {
            this.off.setStartDate(Constants.manager.getDateFromDatePicker(startDate));
            this.off.setEndDate(Constants.manager.getDateFromDatePicker(endDate));
            offController.createNewOff(off, Constants.manager.getToken());
            // todo call the main to vanish this
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void reloadItems() {


    }
}
