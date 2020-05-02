package controller.interfaces.cart;

import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NotEnoughProductsException;
import model.Cart;

public interface ICartController {

    void addOrChangeProduct(int productSellerId, int amount, String token) throws InvalidIdException, NotEnoughProductsException, InvalidTokenException;

    Cart showCart(String token) throws InvalidTokenException;

    void setAddress(String address, String token) throws InvalidTokenException;

    boolean checkout(String token);

    long usePromoCode(String promoCode, String token);
}
