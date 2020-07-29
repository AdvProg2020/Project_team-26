package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import model.serializer.CartMapSerialization;

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

    @JsonCreator
    public Cart(@JsonProperty("products") Map<ProductSeller, Integer> products,
                @JsonProperty("usedPromo") Promo usedPromo,
                @JsonProperty("address") String address) {
        this.products = products;
        this.usedPromo = usedPromo;
        this.address = address;
    }

    public boolean addItems(ProductSeller productSeller, int amount) {
        if (productSellerContain(productSeller)) {
            if (amount <= productSeller.getRemainingItems() && amount >= 0) {
                if (amount == 0) {
                    products.remove(productSeller);
                    return true;
                }
                products.remove(productSeller);
                products.put(productSeller, amount);
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

    private boolean productSellerContain(ProductSeller productSeller) {
        for (ProductSeller seller : products.keySet()) {
            if(seller.getId() == productSeller.getId())
                return true;
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

    @JsonIgnore
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
