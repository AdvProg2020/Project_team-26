package controller.interfaces.order;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.InvalidIdException;
import exception.NoObjectIdException;
import model.Order;

import java.util.ArrayList;

public interface IOrderController {

    ArrayList<Order> getOrders(String token) throws NoAccessException, InvalidTokenException;

    Order getASingleOrder(int id, String token) throws NoAccessException, InvalidIdException, NoObjectWithIdException, NoObjectIdException, InvalidTokenException;
}
