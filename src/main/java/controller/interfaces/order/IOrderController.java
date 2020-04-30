package controller.interfaces.order;

import model.Order;

public interface IOrderController {

    Order[] getOrders(int startIndex, int endIndex, String token);

    Order getASingleOrder(int id, String token);
}
