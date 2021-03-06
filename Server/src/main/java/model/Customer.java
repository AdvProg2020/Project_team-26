package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.enums.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_promo",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "promo_id"))
    private List<Promo> availablePromos;

    public Customer() {
        orders = new ArrayList<>();
        availablePromos = new ArrayList<>();
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
