package client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    @JsonDeserialize(using = CartProductDeserialization.class)
    @JsonSerialize(using = CartProductSerializer.class)
    private Map<ProductSeller, Integer> products;
    private Promo usedPromo;
    private String address;

    @JsonCreator
    public Cart(@JsonProperty("products") Map<ProductSeller, Integer> products,
                @JsonProperty("usedPromo") Promo usedPromo,
                @JsonProperty("address") String address) {
        this.products = products;
        this.usedPromo = usedPromo;
        this.address = address;
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


    public void setUsedPromo(Promo usedPromo) {
        this.usedPromo = usedPromo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
