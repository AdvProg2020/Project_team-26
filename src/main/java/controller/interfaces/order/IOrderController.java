package controller.interfaces.order;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.InvalidIdException;
import exception.NoObjectIdException;
import model.Order;
import model.User;

import java.util.ArrayList;

public interface IOrderController {

    ArrayList<Order> getOrders(String token) throws NoAccessException, InvalidTokenException;

    ArrayList<Order> getOrdersWithFilter(String sortField, boolean isAcsending, String token) throws NoAccessException, InvalidTokenException;

    ArrayList<User> getProductBuyerByProductId(int productId, String token);//TODO

    Order getASingleOrder(int id, String token) throws NoAccessException, InvalidIdException, NoObjectIdException, InvalidTokenException;
}
