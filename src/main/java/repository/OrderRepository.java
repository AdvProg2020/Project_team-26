package repository;

import model.Order;
import model.Seller;

import java.util.List;

public interface OrderRepository extends Repository<Order> {
    public List<Order> getAllCustomerOrders(int customerId);

    public List<Order> getAllSellerOrders(int sellerId);
}
