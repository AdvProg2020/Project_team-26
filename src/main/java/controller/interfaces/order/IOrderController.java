package controller.interfaces.order;

import exception.*;
import model.Order;
import model.User;

import java.util.ArrayList;
import java.util.List;

public interface IOrderController {

    List<Order> getOrders(String token) throws NoAccessException, InvalidTokenException;

    List<Order> getOrdersWithFilter(String sortField, boolean isAcsending, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException;

    List<User> getProductBuyerByProductId(int productId, String token);//TODO

    Order getASingleOrder(int id, String token) throws NoAccessException, InvalidIdException, NoObjectIdException, InvalidTokenException;
}
