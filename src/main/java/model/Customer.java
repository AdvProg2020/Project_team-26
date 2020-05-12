package model;

import exception.NotEnoughCreditException;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private List<Order> orders;
    private List<Promo> availablePromos;

    public Customer(String username, String password, String email, Role role) {
        super(username, password, email, role);
        orders = new ArrayList<>();
        availablePromos = new ArrayList<>();
    }


    public List<Order> getOrders() {
        return orders;
    }

    public List<Promo> getAvailablePromos() {
        return availablePromos;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
