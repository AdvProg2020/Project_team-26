package controller.interfaces.discount;

import exception.NoAccessException;
import exception.NoObjectWithIdException;
import exception.ObjectAlreadyExistException;
import model.Off;
import model.repository.OffRepository;

import java.util.*;

public interface IOffController {


    int createNewOff(String stringCode, String token) throws NoAccessException, ObjectAlreadyExistException;

    void addProductToOff(int id, int productId, long priceInOff, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException;

    void removeProductFromOff(int id, int productId, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException;

    void setTime(int id, Date date, String type, String token) throws NoAccessException, ObjectAlreadyExistException;


    void removeAOff(int id, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException;

    List<Off> getAllOffs(String token);

    Off getOff(int id, String token) throws NoObjectWithIdException;
}
