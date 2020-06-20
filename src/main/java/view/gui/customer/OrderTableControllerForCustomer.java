package view.gui.customer;

import controller.interfaces.order.IOrderController;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Order;
import view.gui.InitializableController;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class OrderTableControllerForCustomer implements InitializableController {
    private IOrderController orderController;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<String, Orders> buyerColumn;
    @FXML
    private TableColumn<Date,Orders> dateColumn;
    @FXML
    private TableColumn<String,Orders> addressColumn;
    @FXML
    private TableColumn<Long,Orders> priceColumn;
    @Override
    public void initialize(int id) throws IOException {

    }
    public void load(String token){
     //   List<Order> orders = orderController.get

    }
    private class Orders{
        private int id;
        private String address;
        private String name;
        private Date date;
        private Long price;
        protected Orders(int id,String address,String buyersName,Date date,Long price){
            this.id = id;
            this.address = address;
            this.name = buyersName;
            this.date = date;
            this.price = price;
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
    }
}
