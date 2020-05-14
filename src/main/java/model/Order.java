package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private int id;
    private Date date;
    private long totalPrice;
    private long paidAmount;
    private Customer customer;
    private String address;
    private Promo usedPromo;
    private List<OrderItem> items;

    public Order(int id) {
        this.id = id;
        items = new ArrayList<OrderItem>();
    }

    public Order(Customer customer, Promo usedPromo, String address) {
        this.customer = customer;
        this.usedPromo = usedPromo;
        this.address = address;
        this.items = new ArrayList<>();
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
        this.paidAmount = 0;
        this.usedPromo = null;
        for (OrderItem item : order.items) {
            if(item.getSeller().getUsername().equals(seller.getUsername())) {
                items.add(item);
                totalPrice += item.getPrice();
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

    public Date getDate() {
        return date;
    }
}
