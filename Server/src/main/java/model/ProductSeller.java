package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import exception.NotEnoughProductsException;
import model.enums.RequestType;
import model.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "product_seller")
@SecondaryTable(name = "product_seller_off", pkJoinColumns = @PrimaryKeyJoinColumn(name = "product_seller_id"))
public class ProductSeller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_seller_id")
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "price_in_off", table = "product_seller_off", insertable = false, updatable = false)
    private Long priceInOff;

    @Column(name = "remaining_items")
    private int remainingItems;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public ProductSeller() {

    }

    public ProductSeller(int id, Product product, int remainingItems) {
        this.id = id;
        this.product = product;
        this.remainingItems = remainingItems;
    }

    public ProductSeller(int id, Product product, int remainingItems, long price, long priceInOff) {
        this.id = id;
        this.product = product;
        this.remainingItems = remainingItems;
        this.price = price;
        this.priceInOff = priceInOff;
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

    public void setProduct(Product product) {
        this.product = product;
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

    public ProductSellerRequest createProductSellerRequest(RequestType requestType) {
        return new ProductSellerRequest(product, seller, price, remainingItems, requestType);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public ProductSeller clone() {
        return new ProductSeller(this.id, this.product, this.remainingItems, this.price, priceInOff == null ? price : priceInOff);
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
