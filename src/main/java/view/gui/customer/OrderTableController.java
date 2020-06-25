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

    public void load(List<Order> orders, PersonalInfoController parentController) {
        personalInfoController = parentController;
        ArrayList<Orders> ordersArrayList = new ArrayList<>();
        orders.forEach(i -> ordersArrayList.add(new Orders(i.getId(), i.getAddress(), i.getCustomer().getFullName(), i.getDate(), i.getTotalPrice(), i.getPaidAmount())));
        ObservableList<OrderTableController.Orders> observableList = FXCollections.observableList(ordersArrayList);
        loadColumn(observableList);
    }

    private void loadColumn(ObservableList<OrderTableController.Orders> observableList) {
        ObservableList<TableColumn> cols = tableView.getColumns();
        cols.get(0).setCellValueFactory(new PropertyValueFactory<Promo, Date>("startDate"));
        cols.get(1).setCellValueFactory(new PropertyValueFactory<Promo,Date>("endDate"));
        cols.get(2).setCellValueFactory(new PropertyValueFactory<Promo,String>("promoCode"));
        cols.get(3).setCellValueFactory(new PropertyValueFactory<Promo,Double>("percent"));
        cols.get(4).setCellValueFactory(new PropertyValueFactory<Promo,Long>("maxDiscount"));
       /* priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        paidAmount.setCellValueFactory(new PropertyValueFactory<>("paidPrice"));*/
        tableView.setItems(observableList);
        tableView.setEditable(false);
        tableView.setOnMouseClicked((e) -> {
            ObservableList<OrderTableController.Orders> listOfSelected = tableView.getSelectionModel().getSelectedItems();
            loadDetailOfOrder(listOfSelected);
        });
    }

    private void loadDetailOfOrder(ObservableList<OrderTableController.Orders> observableList) {
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
        OrderItemController orderItemController = (OrderItemController) loader.getController();
        orderItemController.initialize(item.getId());
        orderItemController.load(item);
        personalInfoController.addSingleItemToBox(loader.load());
    }

    private class Orders {
        public int id;
        public String address;
        public String name;
        public Date date;
        public Long price;
        public Long paidPrice;

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
