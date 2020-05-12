package model;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderItem {

    @Id
    @Column(name = "order_details_id", unique = true)
    private int id;

    @ManyToOne
    @Column(name = "product_id")
    private Product product;

    @Column(name = "amount", nullable = false)
    private int amount;

    @ManyToOne
    @Column(name = "seller_id", nullable = false)
    private Seller seller;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "paid_price", nullable = false)
    private long paidPrice;

    @Column(name = "shipment_state", nullable = false)
    private ShipmentState state;

    public OrderItem() {
    }

    public OrderItem(int id) {
        this.id = id;
    }

    public OrderItem(Product product, int amount, Seller seller, long price, long paidPrice, ShipmentState state) {
        this.product = product;
        this.amount = amount;
        this.seller = seller;
        this.price = price;
        this.paidPrice = paidPrice;
        this.state = state;
    }

    public Seller getSeller() {
        return this.seller;
    }

    public long getPrice() {
        return this.price;
    }

    public long getPaidPrice() {
        return paidPrice;
    }
}
