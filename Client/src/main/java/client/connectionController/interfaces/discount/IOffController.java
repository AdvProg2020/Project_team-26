package client.connectionController.interfaces.discount;

import exception.*;
import model.Off;
import model.Product;

import java.util.List;
import java.util.Map;

public interface IOffController {
    void createNewOff(Off newOff, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException;

    void addProductToOff(Off off, int productId, long priceInOff, double percent, boolean isForFirstTime, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException, NotLoggedINException;

    void removeProductFromOff(Off off, int productId, boolean isForAdd, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException, NotLoggedINException;

    void removeAOff(int id, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException;

    List<Product> getAllProductWithOff(Map<String, String> filter, String sortField, boolean isAscending, int startIndex, int endIndex, String token);

    List<Off> getAllOfForSellerWithFilter(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException;

    Off getOff(int id, String token) throws InvalidIdException;

    void edit(Off newOff, int id, String token) throws NoAccessException, InvalidTokenException, InvalidIdException, NotLoggedINException;
}
