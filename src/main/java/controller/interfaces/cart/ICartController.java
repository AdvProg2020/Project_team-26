package controller.interfaces.cart;

import controller.Exceptions;
import model.Cart;

public interface ICartController {

    void addProduct(String productId, String sellerId, String token) throws Exceptions.TheParameterDoesNOtExist, Exceptions.InvalidFiledException;

    void changeProductAmount(int productId, int amount, String token);

    Cart showCart(String token);

    void setAddress(String address, String token);

    boolean checkout(String token);

    long usePromoCode(String promoCode, String token);
}
