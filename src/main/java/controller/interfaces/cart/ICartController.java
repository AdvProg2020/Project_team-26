package controller.interfaces.cart;


import exception.*;
import model.Cart;

public interface ICartController {

    void addOrChangeProduct(int productSellerId, int amount, String token) throws InvalidIdException, NotEnoughProductsException, InvalidTokenException;

    Cart getCart(String token) throws InvalidTokenException;

    long getTotalPrice(Cart cart, String token) throws InvalidTokenException;

    void setAddress(String address, String token) throws InvalidTokenException;

    void checkout(String token) throws InvalidTokenException, NotLoggedINException, NoAccessException, NotEnoughProductsException, NotEnoughCreditException;

    void usePromoCode(String promoCode, String token) throws InvalidTokenException, InvalidPromoCodeException, PromoNotAvailableException, NotLoggedINException, NoAccessException;

    int getAmountInCartBySellerId(int productSelleId, String token) throws InvalidTokenException, NoSuchObjectException;
}
