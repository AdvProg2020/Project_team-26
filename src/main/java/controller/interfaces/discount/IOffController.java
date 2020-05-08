package controller.interfaces.discount;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectWithIdException;
import exception.ObjectAlreadyExistException;
import model.*;
import model.repository.OffRepository;
import model.repository.ProductRepository;

import java.util.*;

public interface IOffController {
    Off createNewOff(Off newOff, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidTokenException;

    void addProductToOff(Off off, int productId, long priceInOff, int percent, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException, InvalidTokenException;

    void removeProductFromOff(Off off, int productId, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException, InvalidTokenException;

    void removeAOff(int id, String token) throws NoAccessException, NoObjectWithIdException, InvalidTokenException;

    List<Off> getAllOffs(String token);

    List<Off> getAllOfForSeller(String token) throws NoAccessException, InvalidTokenException;

    Off getOff(int id, String token) throws NoObjectWithIdException;

    void edit(Off newOff, int id, String token) throws NoAccessException, InvalidTokenException, NoObjectWithIdException;
}
