package client.gui.admin;

import client.ControllerContainer;
import client.connectionController.interfaces.order.IOrderController;
import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.Order;
import client.model.OrderItem;
import client.model.Product;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class ManagerOrderController implements InitializableController {

    private IOrderController controller;

    private Order order;

    @FXML
    private TableView<OrderItem> orderItemTable;

    @FXML
    private TableColumn idCol;

    @FXML
    private TableColumn productNameCol;

    @FXML
    private TableColumn sellerCol;

    @FXML
    private TableColumn priceCol;

    @FXML
    private TableColumn paidPriceCol;

    @FXML
    private Button acceptButton;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn orderIdCol;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        controller = (IOrderController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OrderController);

        acceptButton.setOnMouseClicked(e -> {
            try {
                changeShipmentStatus();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            }
        });

        orderTable.setOnMouseClicked(e -> {
            try {
                orderTableClicked();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            }
        });

        reload();
    }

    private void orderTableClicked() throws NoAccessException, InvalidTokenException {
        this.order = orderTable.getSelectionModel().getSelectedItem();
        reloadOrderItemTable(order);
    }

    private void changeShipmentStatus() throws NoAccessException, InvalidTokenException {
        OrderItem orderItem = orderItemTable.getSelectionModel().getSelectedItem();
        if (orderItem == null)
            return;
        else {
            controller.changeShipmentStatus(orderItem.getId());
            reloadOrderItemTable(order);
            reload();
        }
    }

    private void reloadOrderItemTable(Order order) throws NoAccessException, InvalidTokenException {
        List<OrderItem> allOrderItems = controller.getOrderItems(order.getId(), Constants.manager.getToken());
        orderItemTable.setItems(FXCollections.observableList(allOrderItems));
        idCol.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("product"));
        sellerCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("seller"));
        priceCol.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("price"));
        paidPriceCol.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("paidPrice"));
    }


    private void reload() throws NoAccessException, InvalidTokenException {
        List<Order> allOrders = controller.getOrders(Constants.manager.getToken());
        orderTable.setItems(FXCollections.observableList(allOrders));
        orderIdCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
    }
}
