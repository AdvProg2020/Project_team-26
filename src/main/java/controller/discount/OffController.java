package controller.discount;

import controller.interfaces.discount.IOffController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectWithIdException;
import exception.ObjectAlreadyExistException;
import model.*;
import model.repository.OffRepository;
import model.repository.ProductRepository;

import java.util.Date;
import java.util.List;

public class OffController implements IOffController {
    private OffRepository offRepository;
    ProductRepository productRepository;

    @Override
    public int createNewOff(String stringCode, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidTokenException {
        checkAccessOfUser(token, "Only seller can add Off");
        if (offRepository.getOffByStringCode(stringCode) != null)
            throw new ObjectAlreadyExistException("the off with code " + stringCode + " exist", null);
        Off off = new Off(stringCode);
        offRepository.save(off);
        return off.getId();
    }

    private void checkAccessOfUser(String token, String message) throws NoAccessException, InvalidTokenException {
        Session session = Session.getSession(token);
        if (!(session.getLoggedInUser().getRole() == Role.SELLER))
            throw new NoAccessException(message);
    }

    @Override
    public void addProductToOff(int id, int productId, long priceInOff, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException, InvalidTokenException {
        checkAccessOfUser(token, "only seller can add product to off");
        Off off = getOffByIdWithCheck(id);
        Product product = productRepository.getById(productId);
        if (product == null)
            throw new NoObjectWithIdException("no product exist with this id");

        if (product.getOff() != null)
            throw new ObjectAlreadyExistException("the product exist in list", product);
        OffItem offItem = new OffItem(product, priceInOff);
        off.getItems().add(offItem);
        offRepository.save(off);
        //ToDo
    }

    private Off getOffByIdWithCheck(int id) throws NoObjectWithIdException {
        Off off = offRepository.getById(id);
        if (off == null)
            throw new NoObjectWithIdException("the off with " + id + " doesn't exist");
        return off;
    }

    @Override
    public void removeProductFromOff(int id, int productId, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException, InvalidTokenException {
        checkAccessOfUser(token, "only seller can remove product to off");
        Off off = getOffByIdWithCheck(id);
        Product product = productRepository.getById(productId);
        if (product == null)
            throw new NoObjectWithIdException("no product exist with this id");
        OffItem offItem = off.getItemByProductId(productId);
        if (offItem == null)
            throw new NoObjectWithIdException("no product is in list");
        off.getItems().remove(offItem);
        offRepository.save(off);
    }

    @Override
    public void setTime(int id, Date date, String type, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidTokenException {
        checkAccessOfUser(token, "only seller can set date");
        //ToDO
    }


    @Override
    public void removeAOff(int id, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException, InvalidTokenException {
        checkAccessOfUser(token, "only seller can delete off");
        Off off = getOffByIdWithCheck(id);
        offRepository.delete(id);
    }

    @Override
    public List<Off> getAllOffs(String token) {
        //ToDO no need to check
        return offRepository.getAll();
    }

    @Override
    public Off getOff(int id, String token) throws NoObjectWithIdException {
        Off off = getOffByIdWithCheck(id);
        return off;
    }

}
