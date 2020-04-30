package controller.interfaces.cart;

import model.Cart;

public interface ICartController {

    void addProduct(int productId, String token);

    void changeProductAmount(int productId, int amount, String token);

    Cart showCart(String token);

    void setAddress(String address, String token);

    boolean checkout(String token);

    long usePromoCode(String promoCode, String token);
}
