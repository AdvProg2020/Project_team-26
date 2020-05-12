package model;

import exception.NotEnoughCreditException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    // TODO: define many to many correctly
    @ManyToMany
    private List<Promo> availablePromos;

    public Customer() {
    }

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
