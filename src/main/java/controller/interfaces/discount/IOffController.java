package controller.interfaces.discount;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectWithIdException;
import exception.ObjectAlreadyExistException;
import model.Off;
import model.Product;
import model.repository.OffRepository;

import java.util.*;

public interface IOffController {


    Off createNewOff(Off off, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidTokenException;

    void addProductToOff(Off off, int productId, long priceInOff, int percent, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException, InvalidTokenException;

    void removeProductFromOff(Off off, int productId, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException, InvalidTokenException;

    void removeAOff(int id, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException, InvalidTokenException;

    List<Off> getAllOffs(String token);

    List<Off> getAllOfForSeller(int sellerId, String token);

    Off getOff(int id, String token) throws NoObjectWithIdException;

    void edit(Off off, int previousId, String token);
}
