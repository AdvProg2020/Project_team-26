package controller.discount;

import controller.interfaces.discount.IOffController;
import controller.interfaces.product.IProductController;
import exception.NoAccessException;
import exception.ObjectAlreadyExistException;
import model.Role;
import model.Session;
import model.repository.OffRepository;

import java.util.Date;

public class OffController implements IOffController {
    private OffRepository offRepository;
    IProductController productController;

    @Override
    public int createNewOff(String stringCode, String token) throws NoAccessException, ObjectAlreadyExistException {
        checkAccessOfUser(token,"Only seller can add Off");


        return 0;
    }

    private void checkAccessOfUser(String token, String message) throws NoAccessException {
        Session session = Session.getSession(token);
        if (!(session.getLoggedInUser().getRole() == Role.SELLER))
            throw new NoAccessException(message);

    }

    @Override
    public void addProductToOff(int id, int productId, long priceInOff, String token) throws NoAccessException, ObjectAlreadyExistException {

    }

    @Override
    public void removeProductFromOff(int id, int productId, String token) throws NoAccessException, ObjectAlreadyExistException {

    }

    @Override
    public void setStartDate(int id, Date date, String token) throws NoAccessException, ObjectAlreadyExistException {

    }

    @Override
    public void setEndDate(int id, Date date, String token) throws NoAccessException, ObjectAlreadyExistException {

    }

    @Override
    public void removeAOff(int id, String token) throws NoAccessException, ObjectAlreadyExistException {

    }
}
