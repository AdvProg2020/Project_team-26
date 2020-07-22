package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.enums.AuctionState;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;

@Entity
@Table(name = "auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "product_seller_id")
    private ProductSeller productSeller;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer maxSuggestedCustomer;

    @JsonIgnore
    @Column(name = "price")
    private long price;

    @JsonIgnore

    public Auction() {
    }

    public Auction(Date endDate, ProductSeller productSeller) {
        this.endDate = endDate;
        this.productSeller = productSeller;
        this.price = productSeller.getPrice();
        this.maxSuggestedCustomer = null;
    }


    public int getId() {
        return id;
    }


    public Date getEndDate() {
        return endDate;
    }

    public ProductSeller getProductSeller() {
        return productSeller;
    }

    public User getMaxSuggestedCustomer() {
        return maxSuggestedCustomer;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setMaxSuggestedCustomer(Customer maxSuggestedCustomer) {
        this.maxSuggestedCustomer = maxSuggestedCustomer;
    }

    public long getPrice() {
        return price;
    }
}
