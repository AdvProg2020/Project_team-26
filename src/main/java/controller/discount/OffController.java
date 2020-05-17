package controller.discount;

import interfaces.discount.IOffController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.InvalidIdException;
import exception.ObjectAlreadyExistException;
import model.*;
import repository.OffRepository;
import repository.ProductRepository;
import repository.RepositoryContainer;

import java.util.List;
import java.util.Map;

public class OffController implements IOffController {
    private OffRepository offRepository;
    ProductRepository productRepository;

    public OffController(RepositoryContainer repositoryContainer) {

    }


    private void checkAccessOfUser(String token, String message) throws NoAccessException, InvalidTokenException {
        Session session = Session.getSession(token);
        if (!(session.getLoggedInUser().getRole() == Role.SELLER))
            throw new NoAccessException(message);
    }

    private Off getOffByIdWithCheck(int id) throws InvalidIdException {
        Off off = offRepository.getById(id);
        if (off == null)
            throw new InvalidIdException("the off with " + id + " doesn't exist");
        return off;
    }

    @Override
    public void createNewOff(Off newOff, String token) throws NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "seller can create a off");
        offRepository.addRequest(newOff);
    }

    @Override
    public void addProductToOff(Off off, int productId, long priceInOff, int percent, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException {
        checkAccessOfUser(token, "only seller can add product");
        Product product = productRepository.getById(productId);
        if (product == null)
            throw new InvalidIdException("no product exist");
        List<OffItem> offItems = off.getItems();
        OffItem offItem = getOffItem(offItems, productId);
        if (offItem != null)
            throw new ObjectAlreadyExistException("the product exist in your off list", product);
        ProductSeller productSeller = getProductSeller(Session.getSession(token).getLoggedInUser().getId(), product.getSellerList());
        if (productSeller == null)
            throw new NoAccessException("the product you have choose you are not its seller");
        if (priceInOff < 0)
            offItem = new OffItem(product, (long) productSeller.getPrice() * (100 - percent) / 100);
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
    public void removeProductFromOff(Off off, int productId, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException {
        checkAccessOfUser(token, "only seller can add product");
        Product product = productRepository.getById(productId);
        if (product == null)
            throw new InvalidIdException("no product exist");
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
    public void removeAOff(int id, String token) throws NoAccessException, InvalidIdException, InvalidTokenException {
        checkAccessOfUser(token, "only seller can remove off");
        getOffByIdWithCheck(id);
        offRepository.deleteRequest(id);
    }

    @Override
    public List<Off> getAllOffs(String token) {
        return offRepository.getAll();
    }

    @Override
    public List<Product> getAllProductWithOff(Map<String, String> filter, String sortFiled, boolean isAscending, String token) {
        return null;
    }

    @Override
    public List<Off> getAllOfForSeller(String token) throws NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "only seller");
        return ((Seller) Session.getSession(token).getLoggedInUser()).getAllOffs();
    }

    @Override
    public List<Off> getAllOfForSellerWithFilter(Map<String, String> filter, String sortField, boolean isAcsending, String token) throws NoAccessException, InvalidTokenException {
        return null;
    }

    @Override
    public Off getOff(int id, String token) throws InvalidIdException {
        return getOffByIdWithCheck(id);
    }

    @Override
    public void edit(Off newOff, int id, String token) throws NoAccessException, InvalidTokenException, InvalidIdException {
        checkAccessOfUser(token, "only seller");
        Off off = getOffByIdWithCheck(id);
        Seller seller = (Seller) Session.getSession(token).getLoggedInUser();
        if (!seller.getAllOffs().contains(off))
            throw new NoAccessException("you can only change your off");
        offRepository.addRequest(off);
    }
}
