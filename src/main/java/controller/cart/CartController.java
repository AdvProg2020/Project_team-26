package controller.cart;

import controller.Exceptions;
import controller.interfaces.cart.ICartController;
import model.Cart;

public class CartController implements ICartController {


    @Override
    public void addProduct(String productId, String sellerId, String token) throws Exceptions.TheParameterDoesNOtExist, Exceptions.InvalidFiledException {

    }

    @Override
    public void changeProductAmount(int productId, int amount, String token) {

    }

    @Override
    public Cart showCart(String token) {
        return null;
    }

    @Override
    public void setAddress(String address, String token) {

    }

    @Override
    public boolean checkout(String token) {
        return false;
    }

    @Override
    public long usePromoCode(String promoCode, String token) {
        return 0;
    }
}
