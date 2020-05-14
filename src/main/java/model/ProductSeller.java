package model;

import exception.NotEnoughProductsException;

public class ProductSeller {

    private int id;
    private Product product;
    private Seller seller;
    private long price;
    private long priceInOff;
    private int remainingItems;

    public ProductSeller(Seller seller,,Product product, long price , int remainingItems) {

    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
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
        return priceInOff;
    }
}
