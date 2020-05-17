package repository.mysql;

import model.Order;
import model.Seller;
import repository.OrderRepository;

import java.util.List;

public class MySQLOrderRepository
    extends MySQLRepository<Order> implements OrderRepository {
    public MySQLOrderRepository() {
        super(Order.class);
    }

    @Override
    public List<Order> getAllCustomerOrders(int customerId) {
        return null;
    }

    @Override
    public List<Order> getAllSellerOrders(int sellerId) {
        return null;
    }
}
