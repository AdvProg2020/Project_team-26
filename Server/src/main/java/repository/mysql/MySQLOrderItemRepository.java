package repository.mysql;

import model.OrderItem;
import repository.OrderItemRepository;
import repository.Repository;

public class MySQLOrderItemRepository
        extends MySQLRepository<OrderItem> implements OrderItemRepository {

    public MySQLOrderItemRepository() {
        super(OrderItem.class);
    }
}
