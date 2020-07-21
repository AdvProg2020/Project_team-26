package client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private Date date;
    private Long totalPrice;
    private Long paidAmount;
    private User customer;
    private String address;
    private Promo usedPromo;
    private long discount;

    @JsonCreator
    public Order(@JsonProperty("id") int id,
                 @JsonProperty("date") Date date,
                 @JsonProperty("totalPrice") Long totalPrice,
                 @JsonProperty("paidAmount") Long paidAmount,
                 @JsonProperty("customer") User customer,
                 @JsonProperty("address") String address,
                 @JsonProperty("usedPromo") Promo usedPromo,
                 @JsonProperty("discount") long discount) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.paidAmount = paidAmount;
        this.customer = customer;
        this.address = address;
        this.usedPromo = usedPromo;
        this.discount = discount;
    }

    public User getCustomer() {
        return this.customer;
    }

    public int getId() {
        return this.id;
    }

    public Promo getUsedPromo() {
        return usedPromo;
    }

    public long getDiscount() {
        return discount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public Long getPaidAmount() {
        return paidAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }
}
