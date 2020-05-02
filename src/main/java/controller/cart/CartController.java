package controller.cart;

import controller.interfaces.cart.ICartController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NotEnoughProductsException;
import model.Cart;
import model.ProductSeller;
import model.Session;
import model.repository.ProductSellerRepository;
import model.repository.RepositoryContainer;

public class CartController implements ICartController {

    ProductSellerRepository productSellerRepository;

    public CartController(RepositoryContainer repositoryContainer) {
        productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
    }

    @Override
    public void addOrChangeProduct(int productSellerId, int amount, String token)
            throws InvalidIdException, NotEnoughProductsException, InvalidTokenException {
        Session session = Session.getSession(token);
        ProductSeller productSeller = productSellerRepository.getById(productSellerId);
        if(productSeller == null) {
            throw new InvalidIdException("There is no product seller with this id");
        }

        if(!session.getCart().addItems(productSeller, amount)) {
            throw new NotEnoughProductsException("There isn't enough products available");
        }
    }

    @Override
    public Cart showCart(String token) throws InvalidTokenException {
        Session session = Session.getSession(token);
        return session.getCart();
    }

    @Override
    public void setAddress(String address, String token) throws InvalidTokenException {
        Session session = Session.getSession(token);
        session.getCart().setAddress(address);
    }

    @Override
    public long usePromoCode(String promoCode, String token) {
        return 0;
    }

    @Override
    public boolean checkout(String token) {
        return false;
    }
}