package client.gui;

import client.gui.interfaces.InitializableController;
import client.model.OrderItem;
import client.model.enums.ShipmentState;
import client.model.enums.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class OrderItemController implements InitializableController {
    private int orderItemId;
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
        this.orderItemId = id;
    }

    public void load(OrderItem orderItem) {
        this.productId = orderItem.getProductId();
        productPageButton.setText("product");
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
    }

    @FXML
    public void productButtonClicked() throws IOException {
        if (productPageButton.isVisible() == false)
            return;
        Constants.manager.openPage("SingleProduct", productId);
    }
}
