package controller.interfaces.order;

import exception.NoAccessException;
import exception.NoObjectWithIdException;
import model.Order;

import java.util.ArrayList;

public interface IOrderController {

    ArrayList<Order> getOrders(String token) throws NoAccessException;

    Order getASingleOrder(int id, String token) throws NoAccessException, NoObjectWithIdException;
}
