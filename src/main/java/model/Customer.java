package model;

import exception.NotEnoughCreditException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "user_promo",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "promo_id"))
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

    public void addPromo(Promo promo) {
        availablePromos.add(promo);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
