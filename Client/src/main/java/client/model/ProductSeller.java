package client.model;

import client.exception.NotEnoughProductsException;
import client.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductSeller {
    private int id;
    private Seller seller;
    private long price;
    private Long priceInOff;
    private int remainingItems;
    private Status status;

    public ProductSeller() {

    }

    public ProductSeller(
            @JsonProperty("id") int id,
            @JsonProperty("seller") Seller seller,
            @JsonProperty("price") long price,
            @JsonProperty("priceInOff") Long priceInOff,
            @JsonProperty("remainingItems") int remainingItems,
            @JsonProperty("status") Status status) {
        this.id = id;
        this.seller = seller;
        this.price = price;
        this.priceInOff = priceInOff;
        this.remainingItems = remainingItems;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Seller getSeller() {
        return seller;
    }

    public long getPrice() {
        return price;
    }

    public int getRemainingItems() {
        return remainingItems;
    }

    public void sell(int amount) throws NotEnoughProductsException {
        if (remainingItems < amount) {
            throw new NotEnoughProductsException("Not enough products", this);
        }
        remainingItems -= amount;
    }

    public long getPriceInOff() {
        if (priceInOff == null) {
            return price;
        }
        return priceInOff;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setRemainingItems(int remainingItems) {
        this.remainingItems = remainingItems;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "Seller: " + seller.getFullName() + "\n" +
                "With Username: " + seller.getUsername() + "\n" +
                "Price: " + price + "\n" +
                "Remaining Items: " + remainingItems;
    }
}
