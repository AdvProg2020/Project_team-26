package model;

import exception.NotEnoughProductsException;

import javax.persistence.*;

@Entity
@Table(name = "product_seller")
public class ProductSeller {

    @Id
    @Column(name="product_seller_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    private long price;
    private long priceInOff;
    private int remainingItems;

    public ProductSeller() {

    }

    public ProductSeller(int id, Product product) {
        this.id = id;
        this.product = product;
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
