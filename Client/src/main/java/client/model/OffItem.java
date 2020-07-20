package client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OffItem {
    private int id;
    private ProductSeller productSeller;
    private long priceInOff;

    @JsonCreator
    public OffItem(@JsonProperty("id") int id,
                   @JsonProperty("productSeller") ProductSeller productSeller,
                   @JsonProperty("priceInOff") long priceInOff) {
        this.id = id;
        this.productSeller = productSeller;
        this.priceInOff = priceInOff;
    }

    public int getId() {
        return id;
    }

    public ProductSeller getProductSeller() {
        return productSeller;
    }

    public long getPriceInOff() {
        return priceInOff;
    }
}
