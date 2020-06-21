package view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.OrderItem;
import model.ShipmentState;
import model.Status;

import java.io.IOException;

import static model.Status.DEACTIVE;

public class OrderItemController implements InitializableController {
    private int offItemId;
    private int productId;
    @FXML
    private Button productPageButton;
    @FXML
    private Text sellerName;
    @FXML
    private Text productName;
    @FXML
    private Text price;
    @FXML
    private Text paidPrice;
    @FXML
    private Text number;
    @FXML
    private Text state;

    @Override
    public void initialize(int id) throws IOException {
        this.offItemId = id;

    }

    public void load(OrderItem orderItem) {
        this.productId = orderItem.getProductId();
        sellerName.setText(orderItem.getSeller().getFullName());
        productName.setText(orderItem.getProduct().getName());
        price.setText("" + orderItem.getPrice());
        paidPrice.setText("" + orderItem.getPaidPrice());
        number.setText("" + orderItem.getAmount());
        state.setText("" + ShipmentState.getState(orderItem.getState()));
        if (orderItem.getProduct().getStatus() == Status.DEACTIVE) {
            productPageButton.setVisible(false);
            return;
        }


        //todo
    }

    @FXML
    public void productButtonClicked() {
        if (productPageButton.isVisible() == false)
            return;
        //todo load page
    }
}
