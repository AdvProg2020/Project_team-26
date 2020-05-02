package model;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    // TODO: define hash for product seller
    private Map<ProductSeller, Integer> products;
    private Promo usedPromo;
    private String address;

    public Cart() {
        products = new HashMap<>();
    }

    public boolean addItems(ProductSeller productSeller, int amount) {
        if(products.containsKey(productSeller)) {
            int newAmount = products.get(productSeller) + amount;
            if(newAmount <= productSeller.getRemainingItems() && newAmount >= 0) {
                products.replace(productSeller, newAmount);
                return true;
            }
        } else {
            if(amount <= productSeller.getRemainingItems() && amount > 0) {
                products.put(productSeller, amount);
                return true;
            }
        }
        return false;
    }

    public void setUsedPromo(Promo usedPromo) {
        this.usedPromo = usedPromo;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
