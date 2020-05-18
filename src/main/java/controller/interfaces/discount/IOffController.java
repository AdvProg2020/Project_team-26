package controller.interfaces.discount;

import exception.*;
import model.*;

import java.util.*;

public interface IOffController {
    void createNewOff(Off newOff, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException;

    void addProductToOff(Off off, int productId, long priceInOff, int percent, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException, NotLoggedINException;

    void removeProductFromOff(Off off, int productId, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException, NotLoggedINException;

    void removeAOff(int id, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException;

    List<Off> getAllOffs(String token);

    List<Product> getAllProductWithOff(Map<String, String> filter, String sortField, boolean isAscending, String token);

    List<Off> getAllOfForSeller(String token) throws NoAccessException, InvalidTokenException, NotLoggedINException;

    List<Off> getAllOfForSellerWithFilter(String sortField, boolean isAcsending, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException;

    Off getOff(int id, String token) throws InvalidIdException;

    void edit(Off newOff, int id, String token) throws NoAccessException, InvalidTokenException, InvalidIdException, NotLoggedINException;
}
