package controller.interfaces.cart;

import exception.*;
import model.Cart;

public interface ICartController {

    void addOrChangeProduct(int productSellerId, int amount, String token) throws InvalidIdException, NotEnoughProductsException, InvalidTokenException;

    Cart showCart(String token) throws InvalidTokenException;
     long getToTalPrice(Cart cart, String token);

    void setAddress(String address, String token) throws InvalidTokenException;

    void checkout(String token) throws InvalidTokenException, NotLoggedINException, NoAccessException, NotEnoughProductsException, NotEnoughCreditException;

    void usePromoCode(String promoCode, String token) throws InvalidTokenException, InvalidPromoCodeException, PromoNotAvailableException, NotLoggedINException, NoAccessException;
}
