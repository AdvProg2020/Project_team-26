package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private int id;
    private boolean isLoaded;
    private Date date;
    private long totalPrice;
    private long paidAmount;
    private Customer customer;
    private Promo usedPromo;
    private List<OrderItem> items;


    public Customer getCustomer() {
        return this.customer;
    }

    public int getId() {
        return this.id;
    }

    public Order(int id) {
        this.id = id;
        items = new ArrayList<OrderItem>();
    }

    public Order(Order order, Seller seller) {
        this.isLoaded = order.isLoaded;
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
}
