package view.gui.seller;

import controller.interfaces.discount.IOffController;
import exception.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import model.Off;
import model.OffItem;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.interfaces.Reloadable;

import java.io.IOException;

public class OffItemPageController implements InitializableController {
    private IOffController offController;
    private OffItem offItem;
    private Off off;
    private int offItemId;
    private boolean isForAdd;
    private Reloadable offControllerPage;
    @FXML
    private Text name;
    @FXML
    private Text price;
    @FXML
    private TextField priceInOff;
    @FXML
    private Button deleteButton;

    @Override
    public void initialize(int id) throws IOException {
        offController = (IOffController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OffController);
        offItemId = id;
    }

    public void load(OffItem offItem, Off off, boolean isForAdd, Reloadable offControllerPage) {
        this.offControllerPage = offControllerPage;
        this.off = off;
        this.offItem = offItem;
        this.isForAdd = isForAdd;
        name.setText(offItem.getProductSeller().getProduct().getName());
        price.setText("" + offItem.getProductSeller().getPrice());
        priceInOff.setText("" + offItem.getPriceInOff());
        priceInOff.setEditable(false);
        deleteButton.setText("Delete");
    }

    @FXML
    public void deleteButtonClicked() throws IOException {
        try {
            offController.removeProductFromOff(off, offItem.getProductSeller().getProduct().getId(), isForAdd, Constants.manager.getToken());
            offControllerPage.reload();
        } catch (NoAccessException | ObjectAlreadyExistException | InvalidIdException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        }
    }
}
