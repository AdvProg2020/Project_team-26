package client.gui.seller;

import client.ControllerContainer;
import client.connectionController.interfaces.discount.IOffController;
import client.connectionController.interfaces.product.IProductController;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.Reloadable;
import client.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class OffItemPageController implements InitializableController {
    private IOffController offController;
    private IProductController productController;
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
        productController = (IProductController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ProductController);
        offItemId = id;
    }

    public void load(Product product, long actualPrice, long offPrice, boolean isForAdd, Reloadable offControllerPage) {
        this.offControllerPage = offControllerPage;
        this.isForAdd = isForAdd;
        name.setText(product.getName());
        price.setText("" + actualPrice);
        priceInOff.setText("" + offPrice);
        priceInOff.setEditable(false);
        deleteButton.setText("Delete");
        deleteButton.setVisible(false);
    }

    public void load(OffItem offItem, Off off, boolean isForAdd, Reloadable offControllerPage) {
        this.offControllerPage = offControllerPage;
        this.isForAdd = isForAdd;
        name.setText(productController.getProductNameByProductSellerId(offItem.getProductSeller().getId()));
        price.setText("" + offItem.getProductSeller().getPrice());
        priceInOff.setText("" + offItem.getPriceInOff());
        priceInOff.setEditable(false);
        deleteButton.setText("Delete");
        deleteButton.setVisible(false);
    }

    @FXML
    public void deleteButtonClicked() throws IOException {
      /*  try {
            offController.removeProductFromOff(off, offItem.getProductSeller().getProduct().getId(), isForAdd, Constants.manager.getToken());
            offControllerPage.reload();
        } catch (NoAccessException | ObjectAlreadyExistException | InvalidIdException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        }*/
    }
}
