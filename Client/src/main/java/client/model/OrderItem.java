package client.model;

import client.model.enums.ShipmentState;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItem {
    private int id;
    private Product product;
    private Order order;
    private int amount;
    private User seller;
    private long price;
    private long paidPrice;
    private ShipmentState state;

    @JsonCreator
    public OrderItem(@JsonProperty("id") int id,
                     @JsonProperty("product") Product product,
                     @JsonProperty("order") Order order,
                     @JsonProperty("amount") int amount,
                     @JsonProperty("seller") User seller,
                     @JsonProperty("price") long price,
                     @JsonProperty("paidPrice") long paidPrice,
                     @JsonProperty("state") ShipmentState state) {
        this.id = id;
        this.product = product;
        this.order = order;
        this.amount = amount;
        this.seller = seller;
        this.price = price;
        this.paidPrice = paidPrice;
        this.state = state;
    }

    @JsonIgnore
    public User getSeller() {
        return this.seller;
    }

    public long getPrice() {
        return this.price;
    }

    public int getId() {
        return this.id;
    }

    public long getPaidPrice() {
        return paidPrice;
    }

    public int getProductId() {
        return product.getId();
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public Order getOrder() {
        return order;
    }

    public ShipmentState getState() {
        return state;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
