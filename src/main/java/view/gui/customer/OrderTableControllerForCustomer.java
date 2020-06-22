package view.gui.customer;

import controller.interfaces.order.IOrderController;
import exception.*;
import javafx.collections.*;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.OrderItem;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.InitializableController;
import view.gui.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderTableControllerForCustomer implements InitializableController {
    private IOrderController orderController;
    private PersonalInfoController personalInfoController;
    private int id;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<String, Orders> paidAmount;
    @FXML
    private TableColumn<Date, Orders> dateColumn;
    @FXML
    private TableColumn<String, Orders> addressColumn;
    @FXML
    private TableColumn<Long, Orders> priceColumn;

    @Override
    public void initialize(int id) throws IOException {
        orderController = (IOrderController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OrderController);
        this.id = id;
    }

    public void load(PersonalInfoController parentController, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        personalInfoController = parentController;
        ArrayList<Orders> ordersArrayList = new ArrayList<>();
        List<Order> orders = orderController.getOrdersWithFilter("date", true, 0, 50, Constants.manager.getToken());
        orders.forEach(i -> ordersArrayList.add(new Orders(i.getId(), i.getAddress(), i.getCustomer().getFullName(), i.getDate(), i.getTotalPrice(), i.getPaidAmount())));
        ObservableList<OrderTableControllerForCustomer.Orders> observableList = FXCollections.observableList(ordersArrayList);
        loadColumn(observableList);
    }

    private void loadColumn(ObservableList<OrderTableControllerForCustomer.Orders> observableList) {
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        paidAmount.setCellValueFactory(new PropertyValueFactory<>("paidPrice"));
        tableView.setItems(observableList);
        tableView.setEditable(false);
        tableView.setOnMouseClicked((e) -> {
            ObservableList<OrderTableControllerForCustomer.Orders> listOfSelected = tableView.getSelectionModel().getSelectedItems();
            loadDetailOfOrder(listOfSelected);
        });
    }

    private void loadDetailOfOrder(ObservableList<OrderTableControllerForCustomer.Orders> observableList) {
        personalInfoController.clearBox();
        observableList.forEach(i -> {
            try {
                orderController.getASingleOrder(i.getId(), Constants.manager.getToken()).getItems().forEach(j -> loadFxmlOfSingleOrder(j));
            } catch (NoAccessException e) {//todo
                e.printStackTrace();
            } catch (InvalidIdException e) {
                e.printStackTrace();
            } catch (NoObjectIdException e) {
                e.printStackTrace();
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            }
        });

    }

    private void loadFxmlOfSingleOrder(OrderItem item) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/OrderItem.fxml"));
        OrderItemController orderItemController = (OrderItemController) loader.getController();
        try {
            orderItemController.initialize(item.getId());
            orderItemController.load(item);
            personalInfoController.addSingleItemToBox(loader.load());
        } catch (IOException e) {
            return;
        }
    }

    private class Orders {
        private int id;
        private String address;
        private String name;
        private Date date;
        private Long price;
        private Long paidPrice;

        protected Orders(int id, String address, String buyersName, Date date, Long price, Long paidPrice) {
            this.id = id;
            this.address = address;
            this.name = buyersName;
            this.date = date;
            this.price = price;
            this.paidPrice = paidPrice;
        }

        public int getId() {
            return id;
        }

        public String getAddress() {
            return address;
        }

        public String getName() {
            return name;
        }

        public Date getDate() {
            return date;
        }

        public Long getPrice() {
            return price;
        }

        public Long getPaidPrice() {
            return paidPrice;
        }
    }
}