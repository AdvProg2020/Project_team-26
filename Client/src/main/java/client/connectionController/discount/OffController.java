package client.connectionController.discount;


import client.connectionController.interfaces.discount.IOffController;
import client.exception.*;
import client.model.*;
import client.model.enums.Role;

import java.util.List;
import java.util.Map;

public class OffController implements IOffController {


    public void createNewOff(Off newOff, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        checkAccessOfUser(token, "seller can create a off");
        newOff.setSeller((Seller) Session.getSession(token).getLoggedInUser());
        offRepository.addRequest(newOff);
    }

    public void addProductToOff(Off off, int productId, long priceInOff, double percent, boolean isFirstTime, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        checkAccessOfUser(token, "Only Seller Can Add Product");
        Seller seller = (Seller) Session.getSession(token).getLoggedInUser();
        ProductSeller productSeller = productSellerRepository.getProductSellerByIdAndSellerId(productId, seller.getId());
        Product product = productRepository.getById(productId);
        List<OffItem> offItems = off.getItems();
        OffItem offItem = getOffItem(offItems, productId);
        if (product == null) {
            throw new InvalidIdException("No Such product Exists");
        } else if (offItem != null) {
            throw new ObjectAlreadyExistException("the product exist in your off list", product);
        } else if (productSeller == null) {
            throw new NoAccessException("you are not the seller of the product you have chosen.");
        } else if (priceInOff < 0) {
            offItem = new OffItem(productSeller, (long) ((long) productSeller.getPrice() * (100 - percent) / 100));
        } else {
            offItem = new OffItem(productSeller, priceInOff);
        }
        offItems.add(offItem);
        if (!isFirstTime) {
            off.setSeller((Seller) Session.getSession(token).getLoggedInUser());
            offRepository.addRequest(off);
        }

    }

    public void removeProductFromOff(Off off, int productId, boolean isForAdd, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException, NotLoggedINException {
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
        if (!isForAdd)
            offRepository.deleteRequest(off.getId());
    }

    public void removeAOff(int id, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        checkAccessOfUser(token, "only seller can remove off");
        getOffByIdWithCheck(id);
        offRepository.deleteRequest(id);
    }

    public List<Product> getAllProductWithOff(Map<String, String> filter, String sortField, boolean isAscending, int startIndex, int endIndex, String token) {
        Pageable page = createAPage(sortField, isAscending, startIndex, endIndex);
        return productRepository.getAllSortedAndFiltered(filter, page);
    }

    public List<Off> getAllOfForSellerWithFilter(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        checkAccessOfUser(token, "only seller");
        Pageable page = createAPage(sortField, isAscending, startIndex, endIndex);
        return ((Seller) Session.getSession(token).getLoggedInUser()).getAllOffs(page);
    }

    public Off getOff(int id, String token) throws InvalidIdException {
        return getOffByIdWithCheck(id);
    }

    public void edit(Off newOff, int id, String token) throws NoAccessException, InvalidTokenException, InvalidIdException, NotLoggedINException {
        checkAccessOfUser(token, "only seller");
        Off off = getOffByIdWithCheck(id);
        Seller seller = (Seller) Session.getSession(token).getLoggedInUser();
       // if (!seller.getAllOffs(new Pageable()).contains(off)) // TODO
         //   throw new NoAccessException("you can only change your off");
        off.setSeller((Seller) Session.getSession(token).getLoggedInUser());
        offRepository.addRequest(off);
    }
}
