package interfaces.discount;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.InvalidIdException;
import exception.ObjectAlreadyExistException;
import model.*;

import java.util.*;

public interface IOffController {
    void createNewOff(Off newOff, String token) throws NoAccessException, InvalidTokenException;

    void addProductToOff(Off off, int productId, long priceInOff, int percent, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException;

    void removeProductFromOff(Off off, int productId, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException;

    void removeAOff(int id, String token) throws NoAccessException, InvalidIdException, InvalidTokenException;

    List<Off> getAllOffs(String token);

    List<Product> getAllProductWithOff(Map<String, String> filter, String sortField, boolean isAscending, String token);

    List<Off> getAllOfForSeller(String token) throws NoAccessException, InvalidTokenException;

    List<Off> getAllOfForSellerWithFilter(Map<String, String> filter, String sortField, boolean isAcsending, String token) throws NoAccessException, InvalidTokenException;

    Off getOff(int id, String token) throws InvalidIdException;

    void edit(Off newOff, int id, String token) throws NoAccessException, InvalidTokenException, InvalidIdException;
}
