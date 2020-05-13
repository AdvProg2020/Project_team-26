package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @Column(name = "order_id", unique = true)
    private int id;

    @Column(name = "date")
    private Date date;

// TODO: define price for order
//    private long totalPrice;
//    private long paidAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id")
    private Customer customer;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "promo_id")
    private Promo usedPromo;

    @OneToMany
    private List<OrderItem> items;

    public Order() {
    }

    public Order(int id) {
        this.id = id;
        items = new ArrayList<OrderItem>();
    }

    public Order(Customer customer, Promo usedPromo, String address) {
        this.customer = customer;
        this.usedPromo = usedPromo;
        this.address = address;
        // TODO: set date for order
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public int getId() {
        return this.id;
    }

    public Order(Order order, Seller seller) {
        this.date = order.date;
//        this.paidAmount = 0;
        this.usedPromo = null;
        for (OrderItem item : order.items) {
            if(item.getSeller().getUsername().equals(seller.getUsername())) {
                items.add(item);
//                totalPrice += item.getPrice();
            }
        }
    }

    public Promo getUsedPromo() {
        return usedPromo;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem orderItem) {
        items.add(orderItem);
    }

    public long getTotalPrice() {
        return items.stream().
                map(item -> item.getPrice()).
                reduce((price, totalPrice) -> price + totalPrice).
                orElse((long) 0);
    }

    public long getPaidAmount() {
        return items.stream().
                map(item -> item.getPaidPrice()).
                reduce((price, paidAmount) -> price + paidAmount).
                orElse((long) 0);
    }
}
