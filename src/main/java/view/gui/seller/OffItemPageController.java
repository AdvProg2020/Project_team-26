package view.gui.seller;

import controller.interfaces.discount.IOffController;
import exception.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import model.Off;
import model.OffItem;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.InitializableController;

import java.io.IOException;

public class OffItemPageController implements InitializableController {
    private IOffController offController;
    private OffItem offItem;
    private Off off;
    private int offItemId;
    private boolean isForAdd;
    @FXML
    private Text name;
    @FXML
    private Text price;
    @FXML
    private TextField priceInOff;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @Override
    public void initialize(int id) throws IOException {
        offController = (IOffController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OffController);
        offItemId = id;
    }

    public void load(OffItem offItem, Off off, boolean isForAdd) {
        this.off = off;
        this.offItem = offItem;
        name.setText(offItem.getProductSeller().getProduct().getName());
        price.setText("" + offItem.getProductSeller().getPrice());
        priceInOff.setText("" + offItem.getPriceInOff());
        priceInOff.setEditable(false);
        editButton.setText("Edit");
        deleteButton.setText("Delete");
    }

    @FXML
    public void deleteButtonClicked() {
        try {
            offController.removeProductFromOff(off, offItem.getProductSeller().getProduct().getId(), isForAdd, Constants.manager.getToken());
            //todo reload the offPages
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (ObjectAlreadyExistException e) {
            e.printStackTrace();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }
    }
}
