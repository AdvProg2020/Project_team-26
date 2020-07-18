package client.gui.customer;

import client.ControllerContainer;
import client.connectionController.interfaces.order.IOrderController;
import client.gui.Constants;
import client.gui.OrderItemController;
import client.gui.PersonalInfoController;
import client.gui.interfaces.InitializableController;
import client.model.Order;
import client.model.OrderItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class OrderTableController implements InitializableController {
    private IOrderController orderController;
    private PersonalInfoController personalInfoController;
    private int id;
    @FXML
    private TableView tableView;

    @Override
    public void initialize(int id) throws IOException {
        orderController = (IOrderController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OrderController);
        this.id = id;
    }

    public void load(List<Order> orders, PersonalInfoController parentController) {
        personalInfoController = parentController;
        tableView.setItems(FXCollections.observableList(orders));
        ObservableList<TableColumn> cols = tableView.getColumns();
        cols.get(0).setCellValueFactory(new PropertyValueFactory<Order, Date>("date"));
        cols.get(1).setCellValueFactory(new PropertyValueFactory<Order, Long>("totalPrice"));
        cols.get(2).setCellValueFactory(new PropertyValueFactory<Order, Long>("paidAmount"));
        cols.get(3).setCellValueFactory(new PropertyValueFactory<Order, String>("address"));
        tableView.setOnMouseClicked((e) -> {
            ObservableList<Order> listOfSelected = tableView.getSelectionModel().getSelectedItems();
            loadDetailOfOrder(listOfSelected);
        });
    }


    private void loadDetailOfOrder(ObservableList<Order> observableList) {
        personalInfoController.clearBox();
        observableList.forEach(i -> {
            i.getItems().forEach(j -> {
                try {
                    loadFxmlOfSingleOrder(j);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private void loadFxmlOfSingleOrder(OrderItem item) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/OrderItem.fxml"));
        Node node = loader.load();
        OrderItemController orderItemController = (OrderItemController) loader.getController();
        orderItemController.initialize(item.getId());
        orderItemController.load(item);
        personalInfoController.addSingleItemToBox(node);
    }
}
