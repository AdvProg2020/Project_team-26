package view.gui.customer;

import controller.interfaces.order.IOrderController;
import exception.*;
import javafx.collections.*;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.OrderItem;
import model.Promo;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.InitializableController;
import view.gui.*;

import java.io.IOException;
import java.util.ArrayList;
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
            try {
                orderController.getASingleOrder(i.getId(), Constants.manager.getToken()).getItems().forEach(j -> {
                    try {
                        loadFxmlOfSingleOrder(j);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (NoAccessException | InvalidIdException | NoObjectIdException e) {
                try {
                    Constants.manager.showErrorPopUp(e.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (InvalidTokenException e) {
                try {
                    Constants.manager.showErrorPopUp(e.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Constants.manager.setTokenFromController();

            }
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
