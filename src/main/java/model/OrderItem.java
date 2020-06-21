package model;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_details_id", unique = true)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "amount", nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    private Seller seller;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "paid_price", nullable = false)
    private long paidPrice;

    @Enumerated(EnumType.STRING)
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

    public ShipmentState getState() {
        return state;
    }
}
