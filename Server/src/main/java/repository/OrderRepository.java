package repository;

import model.Order;
import model.OrderItem;

import java.util.List;

public interface OrderRepository extends Repository<Order> {
    List<Order> getAllCustomerOrders(int customerId, Pageable pageable);

    List<Order> getAllSellerOrders(int sellerId, Pageable pageable);

    List<OrderItem> getSellerOrderItems(int id, int orderId);
}
