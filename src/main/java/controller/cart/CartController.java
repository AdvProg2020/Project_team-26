package controller.cart;

import controller.interfaces.cart.ICartController;
import model.Cart;

public class CartController implements ICartController {

    public void addProduct(int productId, String token) {

    }

    public void changeProductAmount(int productId, int amount, String token) {

    }

    public Cart showCart(String token) {
        return null;
    }

    public void setAddress(String address, String token) {

    }

    public boolean checkout(String token) {
        return false;
    }

    public long usePromoCode(String promoCode, String token) {
        return 0;
    }
}
