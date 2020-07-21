package model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    @JsonSerialize(using = CartMapSerialization.class)
    private Map<ProductSeller, Integer> products;
    private Promo usedPromo;
    private String address;

    public Cart() {
        products = new HashMap<>();
    }

    public boolean addItems(ProductSeller productSeller, int amount) {
        if (products.containsKey(productSeller)) {
            if (amount <= productSeller.getRemainingItems() && amount >= 0) {
                if (amount == 0) {
                    products.remove(productSeller);
                }
                products.replace(productSeller, amount);
                return true;
            }
        } else {
            if (amount <= productSeller.getRemainingItems() && amount > 0) {
                products.put(productSeller, amount);
                return true;
            }
        }
        return false;
    }

    public Promo getUsedPromo() {
        return usedPromo;
    }

    public String getAddress() {
        return address;
    }

    public Map<ProductSeller, Integer> getProducts() {
        return products;
    }

    public Map<ProductSeller, Integer> getProduct() {
        //todo
        return products;
    }

    public long getTotalPrice() {
        //todo
        return 0;
    }

    public void setUsedPromo(Promo usedPromo) {
        this.usedPromo = usedPromo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
