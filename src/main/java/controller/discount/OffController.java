package controller.discount;

import controller.interfaces.discount.IOffController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectWithIdException;
import exception.ObjectAlreadyExistException;
import model.*;
import model.repository.OffRepository;
import model.repository.ProductRepository;

import java.util.List;

public class OffController implements IOffController {
    private OffRepository offRepository;
    ProductRepository productRepository;


    private void checkAccessOfUser(String token, String message) throws NoAccessException, InvalidTokenException {
        Session session = Session.getSession(token);
        if (!(session.getLoggedInUser().getRole() == Role.SELLER))
            throw new NoAccessException(message);
    }

    private Off getOffByIdWithCheck(int id) throws NoObjectWithIdException {
        Off off = offRepository.getById(id);
        if (off == null)
            throw new NoObjectWithIdException("the off with " + id + " doesn't exist");
        return off;
    }

    @Override
    public Off createNewOff(Off newOff, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidTokenException {
        checkAccessOfUser(token, "seller can create a off");
        /**
         if(newOff.getEndDate())
         check date
         */
        offRepository.addRequest(newOff);
        return newOff;
    }

    @Override
    public void addProductToOff(Off off, int productId, long priceInOff, int percent, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException, InvalidTokenException {
        checkAccessOfUser(token, "only seller can add product");
        Product product = productRepository.getById(productId);
        if (product == null)
            throw new NoObjectWithIdException("no product exist");
        List<OffItem> offItems = off.getItems();
        OffItem offItem = getOffItem(offItems, productId);
        if (offItem != null)
            throw new ObjectAlreadyExistException("the product exist in your off list", product);
        ProductSeller productSeller = getProductSeller(Session.getSession(token).getLoggedInUser().getId(), product.getSellerList());
        if (productSeller == null)
            throw new NoAccessException("the product you have choose you are not its seller");
        if (priceInOff < 0)
            offItem = new OffItem(product, productSeller.getPrice() * percent / 100);
        else
            offItem = new OffItem(product, priceInOff);
        offItems.add(offItem);
        offRepository.addRequest(off);
    }

    private OffItem getOffItem(List<OffItem> offItems, int productId) {
        for (OffItem offItem : offItems) {
            if (offItem.getProduct().getId() == productId)
                return offItem;
        }
        return null;
    }

    private ProductSeller getProductSeller(int seller, List<ProductSeller> productSellers) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.getSeller().getId() == seller)
                return productSeller;
        }
        return null;
    }

    @Override
    public void removeProductFromOff(Off off, int productId, String token) throws NoAccessException, ObjectAlreadyExistException, NoObjectWithIdException, InvalidTokenException {
        checkAccessOfUser(token, "only seller can add product");
        Product product = productRepository.getById(productId);
        if (product == null)
            throw new NoObjectWithIdException("no product exist");
        List<OffItem> offItems = off.getItems();
        OffItem offItem = getOffItem(offItems, productId);
        if (offItem == null)
            throw new ObjectAlreadyExistException("the product does not exist in list", product);
        ProductSeller productSeller = getProductSeller(Session.getSession(token).getLoggedInUser().getId(), product.getSellerList());
        if (productSeller == null)
            throw new NoAccessException("the product you have choose is not in your list");
        offRepository.deleteRequest(off.getId());
    }

    @Override
    public void removeAOff(int id, String token) throws NoAccessException, NoObjectWithIdException, InvalidTokenException {
        checkAccessOfUser(token, "only seller can remove off");
        getOffByIdWithCheck(id);
        offRepository.deleteRequest(id);
    }

    @Override
    public List<Off> getAllOffs(String token) {
        return offRepository.getAll();
    }

    @Override
    public List<Off> getAllOfForSeller(String token) throws NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "only seller");
        return ((Seller) Session.getSession(token).getLoggedInUser()).getAllOffs();
    }

    @Override
    public Off getOff(int id, String token) throws NoObjectWithIdException {
        return getOffByIdWithCheck(id);
    }

    @Override
    public void edit(Off newOff, int id, String token) throws NoAccessException, InvalidTokenException, NoObjectWithIdException {
        checkAccessOfUser(token, "only seller");
        Off off = getOffByIdWithCheck(id);
        Seller seller = (Seller) Session.getSession(token).getLoggedInUser();
        if (!seller.getAllOffs().contains(off))
            throw new NoAccessException("you can only change your off");
        offRepository.addRequest(off);
    }
}
