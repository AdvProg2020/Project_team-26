package controller.interfaces.discount;

import exception.NoAccessException;
import exception.NoObjectWithIdException;
import exception.ObjectAlreadyExistException;
import model.repository.OffRepository;

import java.util.Date;

public interface IOffController {


    int createNewOff(String stringCode, String token) throws NoAccessException, ObjectAlreadyExistException;

    void addProductToOff(int id, int productId, long priceInOff, String token)throws NoAccessException, ObjectAlreadyExistException;

    void removeProductFromOff(int id, int productId, String token)throws NoAccessException, ObjectAlreadyExistException ;

    void setStartDate(int id, Date date, String token)throws NoAccessException, ObjectAlreadyExistException;

    void setEndDate(int id, Date date, String token)throws NoAccessException, ObjectAlreadyExistException;

    void removeAOff(int id, String token)throws NoAccessException, ObjectAlreadyExistException;
}
