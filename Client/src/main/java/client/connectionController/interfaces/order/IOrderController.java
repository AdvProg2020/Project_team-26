package client.connectionController.interfaces.order;

import client.exception.*;
import client.model.*;

import java.util.List;

public interface IOrderController {

    List<Order> getOrders(String token) throws NoAccessException, InvalidTokenException;

    List<Order> getOrdersWithFilter(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException;

    List<Customer> getProductBuyerByProductId(int productId, String token) throws InvalidTokenException, NotLoggedINException, NoAccessException, InvalidIdException;//TODO

    Order getASingleOrder(int id, String token) throws NoAccessException, InvalidIdException, NoObjectIdException, InvalidTokenException;

    List<Order> getOrderHistoryForSeller(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException;

}
