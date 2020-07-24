package client.gui;

import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.interfaces.InitializableController;
import client.model.OrderItem;
import client.model.enums.ShipmentState;
import client.model.enums.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OrderItemFileController implements InitializableController {

    private int orderItemId;
    private int productId;
    private byte[] file;
    private OrderItem orderItem;
    @FXML
    private Button downloadButton;
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

    public void load(OrderItem orderItem, byte[] file) {
        this.orderItem = orderItem;
        this.file = file;
        this.productId = orderItem.getProductId();
        downloadButton.setText("download");
        sellerName.setText(orderItem.getSeller().getFullName());
        productName.setText(orderItem.getProduct().getName());
        price.setText("" + orderItem.getPrice());
        paidPrice.setText("" + orderItem.getPaidPrice());
        number.setText("" + orderItem.getAmount());
        state.setText("" + ShipmentState.getState(orderItem.getState()));
        if (orderItem.getProduct().getStatus() == Status.DEACTIVE) {
            downloadButton.setVisible(false);
            return;
        }
    }

    @FXML
    public void downloadButtonClicked() throws IOException {
        if (downloadButton.isVisible() == false)
            return;
        Files.write(Paths.get("downloads\\" + orderItem.getProduct().getName()), file);
    }
}
