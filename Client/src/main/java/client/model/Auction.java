package client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Auction {
    private int id;
    private Date endDate;
    private ProductSeller productSeller;
    @JsonIgnore
    private User maxSuggestedCustomer;
    @JsonIgnore
    private long price;

    public Auction() {
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

    public long getPrice() {
        return price;
    }
}
